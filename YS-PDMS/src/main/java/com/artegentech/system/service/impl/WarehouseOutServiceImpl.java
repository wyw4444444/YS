package com.artegentech.system.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.artegentech.system.dao.IMemberDAO;
import com.artegentech.system.dao.IOutListDAO;
import com.artegentech.system.dao.IPartInfoDAO;
import com.artegentech.system.dao.ITypeDAO;
import com.artegentech.system.dao.IWarehouseOutDAO;
import com.artegentech.system.service.IOutListService;
import com.artegentech.system.service.IWarehouseInService;
import com.artegentech.system.service.IWarehouseOutService;
import com.artegentech.system.service.abs.AbstractService;
import com.artegentech.system.vo.WarehouseOut;

@Service
public class WarehouseOutServiceImpl extends AbstractService implements IWarehouseOutService {

	@Resource
	IWarehouseOutDAO warehouseOutDAO;

	@Resource
	IPartInfoDAO partInfoDAO;

	@Resource
	IMemberDAO memberDAO;

	@Resource
	ITypeDAO typeDAO;

	@Resource
	IOutListDAO outListDAO;
	
	@Autowired
	IOutListService outListService;
	
	@Autowired
	IWarehouseInService warehouseInService;

	@Transactional(rollbackFor=Exception.class)
	@Override
	public Map<String, Object> add(WarehouseOut warehouseOut) {
		
		// 需要先判断此part_code的剩余数量总数是否大于等于此次的出库数量
		// 如果大于等于，则允许新增
		// 如果小于，则不允许新增出库记录
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			Double inSurplusQuantity = this.warehouseInService.getSumSurplusQuantityByPartCode(warehouseOut.getPart_code());
			if(inSurplusQuantity == null) {
				inSurplusQuantity = (double) 0;
			}
			Double total_quantity = warehouseOut.getTotal_quantity();
			System.out.println("inSurplusQuantity : " + inSurplusQuantity);
			System.out.println("total_quantity : " + total_quantity);
			if(Double.doubleToLongBits(total_quantity)>0) {
				if(Double.doubleToLongBits(inSurplusQuantity) < Double.doubleToLongBits(total_quantity)) {
					result.put("msg", "剩余库存数量不足以支持此出库数量！");
					result.put("result", false);
				}else {
					boolean flag = false;
					boolean flag2 = false;
					if (this.warehouseOutDAO.findById(warehouseOut.getOut_id()) == null) {
						flag = this.warehouseOutDAO.doCreate(warehouseOut);
					}else {
						result.put("msg", "此out_id已经存在！");
						result.put("result", false);
					}
					
					if(flag==true) {
						// 新增出库记录后，需要新增出库清单，以及调整对应的入库记录剩余数量
						flag2 = this.outListService.addStocks(warehouseOut);
						if(flag2 == true) {
							result.put("msg", "出库记录新增成功！");
							result.put("result", true);
						}else {
							result.put("msg", "出库记录新增成功，但是出库清单新增失败，请修改一下数量或单价激活更新，再修改回来！");
							result.put("result", false);
						}
					}else {
						result.put("msg", "出库记录新增失败，请确认收有问题项！");
						result.put("result", false);
					}
				}
			}else {
				result.put("msg", "出库数量不允许为0！");
				result.put("result", false);
			}
		
		}catch(Exception e) {
			e.printStackTrace();     
	        //就是这一句了, 加上之后抛了异常就能回滚（有这句代码就不需要再手动抛出运行时异常了）
	        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();  
			result.put("msg", "修改出库记录失败，请确认！");
			result.put("result", false);
		}
		return result;
	}

	@Transactional(rollbackFor=Exception.class)
	@Override
	public Map<String, Object> edit(WarehouseOut warehouseOut, WarehouseOut warehouseOut2, Boolean flag1, Boolean flag2) {
		Map<String, Object> mapResult = new HashMap<String, Object>();
		String msg = "";
		try {
			if (this.warehouseOutDAO.findById(warehouseOut.getOut_id()) == null) {
				msg = msg + "未发现此出库记录!";
				mapResult.put("msg", msg);
				mapResult.put("result", false);
			} else {
				if(this.warehouseOutDAO.doUpdate(warehouseOut)) {
					if(flag1==true) {
						// 针对修改前的料号执行重新计算动作
						if(this.outListService.recalculateData(warehouseOut2.getPart_code())==false) {
							msg = msg + "原料号重新计算资料失败!";
						}
					}
					if(flag2==true) {
						if(this.outListService.recalculateData(warehouseOut.getPart_code())==false) {
							msg = msg + "新料号重新计算资料失败！";
						}
					}
					if("".equals(msg)) {
						msg = msg + "修改原出库记录成功！";
						mapResult.put("msg", msg);
						mapResult.put("result", true);
					}else {
						mapResult.put("msg", msg);
						mapResult.put("result", false);
					}
				}else {
					msg = msg + "修改出库记录失败，请确认！";
					mapResult.put("msg", msg);
					mapResult.put("result", false);
				}
			}
			
		}catch(Exception e) {
			e.printStackTrace();     
	        //就是这一句了, 加上之后抛了异常就能回滚（有这句代码就不需要再手动抛出运行时异常了）
	        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();  
	        msg = msg + "修改出库记录失败，请确认！";
			mapResult.put("msg", msg);
			mapResult.put("result", false);
		}
		return mapResult;
	}

	@Override
	public List<WarehouseOut> getAll() throws Exception {
		List<WarehouseOut> warehouseOutList = new ArrayList<WarehouseOut>();
		warehouseOutList = this.warehouseOutDAO.findAll();
		Iterator<WarehouseOut> iter = warehouseOutList.iterator();
		if (iter != null) {
			List<WarehouseOut> result = new ArrayList<WarehouseOut>();
			while (iter.hasNext()) {
				WarehouseOut warehouseOut = iter.next();
				warehouseOut.setPartInfo(this.partInfoDAO.findByCode(warehouseOut.getPart_code()));
				//warehouseOut.getPartInfo.setType_unit_type(this.typeDAO.findById(warehouseOut.getPartInfo.getUnit_type()));
				warehouseOut.setMember(this.memberDAO.findById(warehouseOut.getMember_id()));
				warehouseOut.setType_out_reason(this.typeDAO.findById(warehouseOut.getOut_reason()));
				warehouseOut.setOutLists(this.outListDAO.findAllByOutId(warehouseOut.getOut_id()));
				result.add(warehouseOut);
			}
			return result;
		}
		return null;
	}
	
	@Override
	public List<WarehouseOut> getAllSplit(String column, String keyword, Integer currentPage, Integer lineSize, Date start_date, Date end_date) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map = super.handleParams2(column, keyword, currentPage, lineSize);
		if("out_date".equals(column)) {
			column = null;
			keyword = null;
			map = super.handleParams2(column, keyword, currentPage, lineSize);
		}
		if("part_code".equals(column) || "out_reason".equals(column)) {
			map = super.handleParams2(column, keyword, currentPage, lineSize);
		}
		map.put("start_date", start_date);
		map.put("end_date", end_date);
		List<WarehouseOut> warehouseOutList = new ArrayList<WarehouseOut>();
		warehouseOutList = this.warehouseOutDAO.findAllSplit(map);
		Iterator<WarehouseOut> iter = warehouseOutList.iterator();
		if (iter != null) {
			List<WarehouseOut> result = new ArrayList<WarehouseOut>();
			while (iter.hasNext()) {
				WarehouseOut warehouseOut = iter.next();
				warehouseOut.setPartInfo(this.partInfoDAO.findByCode(warehouseOut.getPart_code()));
				//warehouseOut.getPartInfo.setType_unit_type(this.typeDAO.findById(warehouseOut.getPartInfo.getUnit_type()));
				warehouseOut.setMember(this.memberDAO.findById(warehouseOut.getMember_id()));
				warehouseOut.setType_out_reason(this.typeDAO.findById(warehouseOut.getOut_reason()));
				warehouseOut.setOutLists(this.outListDAO.findAllByOutId(warehouseOut.getOut_id()));
				result.add(warehouseOut);
			}
			return result;
		}
		return null;
	}

	@Override
	public WarehouseOut getById(String out_id) throws Exception {
		WarehouseOut warehouseOut = this.warehouseOutDAO.findById(out_id);
		if (warehouseOut != null) {
			warehouseOut.setPartInfo(this.partInfoDAO.findByCode(warehouseOut.getPart_code()));
			//warehouseOut.getPartInfo.setType_unit_type(this.typeDAO.findById(warehouseOut.getPartInfo.getUnit_type()));
			warehouseOut.setMember(this.memberDAO.findById(warehouseOut.getMember_id()));
			warehouseOut.setType_out_reason(this.typeDAO.findById(warehouseOut.getOut_reason()));
			warehouseOut.setOutLists(this.outListDAO.findAllByOutId(warehouseOut.getOut_id()));
		}
		return warehouseOut;
	}

	@Override
	public Long getAllCount(String column, String keyword, Date start_date, Date end_date) throws Exception {
		Map<String, Object> map = super.handleParams2(column, keyword);
		map.put("start_date", start_date);
		map.put("end_date", end_date);
		return this.warehouseOutDAO.getAllCount(map);
	}

	@Override
	public Long getDayIdNum(String pre_out_id) throws Exception {
		return this.warehouseOutDAO.getDayIdNum(pre_out_id + "%");
	}
	
	@Override
	public Double getSumQuantityByPartCode(String part_code) throws Exception {
		return this.warehouseOutDAO.getSumQuantityByPartCode(part_code);
	}
	
	@Override
	public List<WarehouseOut> getAllByPDRNoPartCode(String pdr_no, String part_code) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pdr_no", pdr_no);
		map.put("part_code", part_code);
		List<WarehouseOut> warehouseOutList = new ArrayList<WarehouseOut>();
		warehouseOutList = this.warehouseOutDAO.findAllByPDRNoPartCode(map);
		Iterator<WarehouseOut> iter = warehouseOutList.iterator();
		if (iter != null) {
			List<WarehouseOut> result = new ArrayList<WarehouseOut>();
			while (iter.hasNext()) {
				WarehouseOut warehouseOut = iter.next();
				warehouseOut.setPartInfo(this.partInfoDAO.findByCode(warehouseOut.getPart_code()));
				//warehouseOut.getPartInfo.setType_unit_type(this.typeDAO.findById(warehouseOut.getPartInfo.getUnit_type()));
				warehouseOut.setMember(this.memberDAO.findById(warehouseOut.getMember_id()));
				warehouseOut.setType_out_reason(this.typeDAO.findById(warehouseOut.getOut_reason()));
				warehouseOut.setOutLists(this.outListDAO.findAllByOutId(warehouseOut.getOut_id()));
				result.add(warehouseOut);
			}
			return result;
		}
		return null;
	}
	
	@Override
	public Double getSumQuantityByPDRNoPartCode(String pdr_no, String part_code) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pdr_no", pdr_no);
		map.put("part_code", part_code);
		return this.warehouseOutDAO.getSumQuantityByPDRNoPartCode(map);
	}
}

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
import com.artegentech.system.dao.IPartInfoDAO;
import com.artegentech.system.dao.ISafetyStorageDAO;
import com.artegentech.system.dao.ITypeDAO;
import com.artegentech.system.dao.IWarehouseInDAO;
import com.artegentech.system.service.IOutListService;
import com.artegentech.system.service.IWarehouseInService;
import com.artegentech.system.service.abs.AbstractService;
import com.artegentech.system.vo.WarehouseIn;

@Service
public class WarehouseInServiceImpl extends AbstractService implements IWarehouseInService {

	@Resource
	IWarehouseInDAO warehouseInDAO;

	@Resource
	IPartInfoDAO partInfoDAO;

	@Resource
	IMemberDAO memberDAO;

	@Resource
	ITypeDAO typeDAO;
	
	@Autowired
	IOutListService outListService;
	
	@Resource
	ISafetyStorageDAO safetyStorageDAO;

	@Transactional(rollbackFor=Exception.class)
	@Override
	public boolean addBatch(List<WarehouseIn> list) {
		try {
			return this.warehouseInDAO.doCreateBatch(list);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	@Override
	public boolean add(WarehouseIn warehouseIn) throws Exception {
		if (this.warehouseInDAO.findById(warehouseIn.getIn_id()) == null) {
			return this.warehouseInDAO.doCreate(warehouseIn);
		}
		return false;
	}

	@Transactional(rollbackFor=Exception.class)
	@Override
	public Map<String, Object> edit(WarehouseIn warehouseIn, WarehouseIn warehouseIn2, Boolean flag) {
		Map<String, Object> mapResult = new HashMap<String, Object>();
		String msg = "";
		try {
			if (this.warehouseInDAO.findById(warehouseIn.getIn_id()) == null) {
				msg = msg + "未发现此入库记录!";
			} else {
				if(this.warehouseInDAO.doUpdate(warehouseIn)) {
					if(warehouseIn2.getPrice()!=null&&Double.doubleToLongBits(warehouseIn.getPrice()) != Double.doubleToLongBits(warehouseIn2.getPrice())) {
						// 如果入库单价有变化，需要将出库清单中此pur_id对应的price_exc修改为最新的值
						if(this.outListService.editPurPriceByInId(warehouseIn.getIn_id(), warehouseIn.getPrice())==false) {
							msg = msg + "更新出库清单中的入库单价失败!";
							//System.out.println(msg);
						}
						// flag设置为true，代表需要重新计算
						flag = true;
					}
					if(flag==true) {
						// 执行重新计算动作
						if(this.outListService.recalculateData(warehouseIn2.getPart_code())==false) {
							msg = msg + "重新计算资料失败!";
						}
					}
					if("".equals(msg)) {
						msg = msg + "修改入库记录成功！";
						mapResult.put("msg", msg);
						mapResult.put("result", true);
					}else {
						mapResult.put("msg", msg);
						mapResult.put("result", false);
					}
				}else {
					msg = msg + "修改入库记录失败，请确认！";
					mapResult.put("msg", msg);
					mapResult.put("result", false);
				}
			}
		}catch(Exception e) {
			e.printStackTrace();     
	        //就是这一句了, 加上之后抛了异常就能回滚（有这句代码就不需要再手动抛出运行时异常了）
	        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();  
	        msg = msg + "修改入库记录失败，请确认！";
			mapResult.put("msg", msg);
			mapResult.put("result", false);
		}
		return mapResult;
	}

	@Override
	public List<WarehouseIn> getAll() throws Exception {
		List<WarehouseIn> warehouseInList = new ArrayList<WarehouseIn>();
		warehouseInList = this.warehouseInDAO.findAll();
		Iterator<WarehouseIn> iter = warehouseInList.iterator();
		if (iter != null) {
			List<WarehouseIn> result = new ArrayList<WarehouseIn>();
			while (iter.hasNext()) {
				WarehouseIn warehouseIn = iter.next();
				//warehouseIn.setPartInfo(this.partInfoDAO.findById(warehouseIn.getPart_code()));
				//warehouseIn.getPartInfo.setType_unit_type(this.typeDAO.findById(warehouseIn.getPartInfo.getUnit_type()));
				warehouseIn.setMember(this.memberDAO.findById(warehouseIn.getMember_id()));
				warehouseIn.setType_in_reason(this.typeDAO.findById(warehouseIn.getIn_reason()));
				result.add(warehouseIn);
			}
			return result;
		}
		return null;
	}
	
	@Override
	public List<WarehouseIn> getAllSplit(String column, String keyword, Integer currentPage, Integer lineSize, Date start_date, Date end_date) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		if("in_date".equals(column)) {
			column = null;
			keyword = null;
			map = super.handleParams2(column, keyword, currentPage, lineSize);
		}
		if("part_code".equals(column) || "in_reason".equals(column)) {
			map = super.handleParams2(column, keyword, currentPage, lineSize);
		}
		map.put("start_date", start_date);
		map.put("end_date", end_date);
		List<WarehouseIn> warehouseInList = new ArrayList<WarehouseIn>();
		warehouseInList = this.warehouseInDAO.findAllSplit(map);
		Iterator<WarehouseIn> iter = warehouseInList.iterator();
		if (iter != null) {
			List<WarehouseIn> result = new ArrayList<WarehouseIn>();
			while (iter.hasNext()) {
				WarehouseIn warehouseIn = iter.next();
				//warehouseIn.setPartInfo(this.partInfoDAO.findById(warehouseIn.getPart_code()));
				//warehouseIn.getPartInfo.setType_unit_type(this.typeDAO.findById(warehouseIn.getPartInfo.getUnit_type()));
				warehouseIn.setMember(this.memberDAO.findById(warehouseIn.getMember_id()));
				warehouseIn.setType_in_reason(this.typeDAO.findById(warehouseIn.getIn_reason()));
				result.add(warehouseIn);
			}
			return result;
		}
		return null;
	}
	
	@Override
	public List<WarehouseIn> getAllSplit2(String column, String keyword, Integer currentPage, Integer lineSize, Date start_date, Date end_date) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map = super.handleParams2(column, keyword, currentPage, lineSize);
		if("in_date".equals(column)) {
			column = null;
			keyword = null;
			map = super.handleParams2(column, keyword, currentPage, lineSize);
		}
		if("part_code".equals(column) || "in_reason".equals(column)) {
			map = super.handleParams2(column, keyword, currentPage, lineSize);
		}
		map.put("start_date", start_date);
		map.put("end_date", end_date);
		List<WarehouseIn> warehouseInList = new ArrayList<WarehouseIn>();
		warehouseInList = this.warehouseInDAO.findAllSplit2(map);
		Iterator<WarehouseIn> iter = warehouseInList.iterator();
		if (iter != null) {
			List<WarehouseIn> result = new ArrayList<WarehouseIn>();
			while (iter.hasNext()) {
				WarehouseIn warehouseIn = iter.next();
				warehouseIn.setPartInfo(this.partInfoDAO.findByCode(warehouseIn.getPart_code()));
				//warehouseIn.getPartInfo.setType_unit_type(this.typeDAO.findById(warehouseIn.getPartInfo.getUnit_type()));
				warehouseIn.setMember(this.memberDAO.findById(warehouseIn.getMember_id()));
				warehouseIn.setType_in_reason(this.typeDAO.findById(warehouseIn.getIn_reason()));
				result.add(warehouseIn);
			}
			return result;
		}
		return null;
	}
	
	@Override
	public List<WarehouseIn> getAllUnlockedSplit(String column, String keyword, Integer currentPage, Integer lineSize, Date start_date, Date end_date) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map = super.handleParams2(column, keyword, currentPage, lineSize);
		if("in_date".equals(column)) {
			column = null;
			keyword = null;
			map = super.handleParams2(column, keyword, currentPage, lineSize);
		}
		if("part_code".equals(column) || "in_reason".equals(column)) {
			map = super.handleParams2(column, keyword, currentPage, lineSize);
		}
		map.put("start_date", start_date);
		map.put("end_date", end_date);
		List<WarehouseIn> warehouseInList = new ArrayList<WarehouseIn>();
		warehouseInList = this.warehouseInDAO.findAllUnlockedSplit(map);
		Iterator<WarehouseIn> iter = warehouseInList.iterator();
		if (iter != null) {
			List<WarehouseIn> result = new ArrayList<WarehouseIn>();
			while (iter.hasNext()) {
				WarehouseIn warehouseIn = iter.next();
				warehouseIn.setPartInfo(this.partInfoDAO.findByCode(warehouseIn.getPart_code()));
				//warehouseIn.getPartInfo.setType_unit_type(this.typeDAO.findById(warehouseIn.getPartInfo.getUnit_type()));
				warehouseIn.setMember(this.memberDAO.findById(warehouseIn.getMember_id()));
				warehouseIn.setType_in_reason(this.typeDAO.findById(warehouseIn.getIn_reason()));
				result.add(warehouseIn);
			}
			return result;
		}
		return null;
	}

	@Override
	public WarehouseIn getById(String in_id) throws Exception {
		WarehouseIn warehouseIn = this.warehouseInDAO.findById(in_id);
		if (warehouseIn != null) {
			warehouseIn.setPartInfo(this.partInfoDAO.findByCode(warehouseIn.getPart_code()));
			//warehouseIn.getPartInfo.setType_unit_type(this.typeDAO.findById(warehouseIn.getPartInfo.getUnit_type()));
			warehouseIn.setMember(this.memberDAO.findById(warehouseIn.getMember_id()));
			warehouseIn.setType_in_reason(this.typeDAO.findById(warehouseIn.getIn_reason()));
		}
		return warehouseIn;
	}

	@Override
	public Long getAllCount(String column, String keyword, Date start_date, Date end_date) throws Exception {
		Map<String, Object> map = super.handleParams2(column, keyword);
		map.put("start_date", start_date);
		map.put("end_date", end_date);
		return this.warehouseInDAO.getAllCount(map);
	}
	
	@Override
	public Long getAllCount2(String column, String keyword, Date start_date, Date end_date) throws Exception {
		Map<String, Object> map = super.handleParams2(column, keyword);
		map.put("start_date", start_date);
		map.put("end_date", end_date);
		return this.warehouseInDAO.getAllCount2(map);
	}
	
	@Override
	public Long getAllUnlockedCount(String column, String keyword, Date start_date, Date end_date) throws Exception {
		Map<String, Object> map = super.handleParams2(column, keyword);
		map.put("start_date", start_date);
		map.put("end_date", end_date);
		return this.warehouseInDAO.getAllUnlockedCount(map);
	}

	@Override
	public Long getDayIdNum(String pre_in_id) throws Exception {
		return this.warehouseInDAO.getDayIdNum(pre_in_id + "%");
	}

	@Override
	public List<WarehouseIn> getAllStorage() throws Exception {
		List<WarehouseIn> warehouseInList = new ArrayList<WarehouseIn>();
		warehouseInList = this.warehouseInDAO.findAllStorage();
		Iterator<WarehouseIn> iter = warehouseInList.iterator();
		if (iter != null) {
			List<WarehouseIn> result = new ArrayList<WarehouseIn>();
			while (iter.hasNext()) {
				WarehouseIn warehouseIn = iter.next();
				//warehouseIn.setPartInfo(this.partInfoDAO.findById(warehouseIn.getPart_code()));
				//warehouseIn.getPartInfo.setType_unit_type(this.typeDAO.findById(warehouseIn.getPartInfo.getUnit_type()));
				warehouseIn.setMember(this.memberDAO.findById(warehouseIn.getMember_id()));
				warehouseIn.setType_in_reason(this.typeDAO.findById(warehouseIn.getIn_reason()));
				result.add(warehouseIn);
			}
			return result;
		}
		return null;
	}

	@Override
	public List<WarehouseIn> getAllStorageSplit(String column, String keyword, Integer currentPage, Integer lineSize) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map = super.handleParams2(column, keyword, currentPage, lineSize);
		List<WarehouseIn> warehouseInList = new ArrayList<WarehouseIn>();
		warehouseInList = this.warehouseInDAO.findAllStorageSplit(map);
		Iterator<WarehouseIn> iter = warehouseInList.iterator();
		if (iter != null) {
			List<WarehouseIn> result = new ArrayList<WarehouseIn>();
			while (iter.hasNext()) {
				WarehouseIn warehouseIn = iter.next();
				warehouseIn.setPartInfo(this.partInfoDAO.findByCode(warehouseIn.getPart_code()));
				//warehouseIn.getPartInfo.setType_unit_type(this.typeDAO.findById(warehouseIn.getPartInfo.getUnit_type()));
				warehouseIn.setMember(this.memberDAO.findById(warehouseIn.getMember_id()));
				warehouseIn.setType_in_reason(this.typeDAO.findById(warehouseIn.getIn_reason()));
				result.add(warehouseIn);
			}
			return result;
		}
		return null;
	}

	@Override
	public Long getAllStorageCount(String column, String keyword) throws Exception {
		Map<String, Object> map = super.handleParams2(column, keyword);
		return this.warehouseInDAO.getAllStorageCount(map);
	}
	
	@Override
	public List<WarehouseIn> getAllStorageByPartCodeSplit(Integer currentPage, Integer lineSize) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map = super.handleParams(null, null, currentPage, lineSize);
		List<WarehouseIn> warehouseInList = new ArrayList<WarehouseIn>();
		warehouseInList = this.warehouseInDAO.findAllStorageByPartCodeSplit(map);
		//System.out.println(warehouseInList.toString());
		Iterator<WarehouseIn> iter = warehouseInList.iterator();
		if (iter != null) {
			List<WarehouseIn> result = new ArrayList<WarehouseIn>();
			while (iter.hasNext()) {
				WarehouseIn warehouseIn = iter.next();
				warehouseIn.setPartInfo(this.partInfoDAO.findByCode(warehouseIn.getPart_code()));
				//warehouseIn.getPartInfo.setType_unit_type(this.typeDAO.findById(warehouseIn.getPartInfo.getUnit_type()));
				warehouseIn.setSafetyStorage(this.safetyStorageDAO.findByPartCode(warehouseIn.getPart_code()));
				result.add(warehouseIn);
			}
			return result;
		}
		return null;
	}

	@Override
	public Long getAllStorageByPartCodeCount() throws Exception {
		return this.warehouseInDAO.getAllStorageByPartCodeCount();
	}
	
	@Override
	public Double getSumQuantityByPartCode(String part_code) throws Exception {
		return this.warehouseInDAO.getSumQuantityByPartCode(part_code);
	}
	
	@Override
	public Double getSumSurplusQuantityByPartCode(String part_code) throws Exception {
		return this.warehouseInDAO.getSumSurplusQuantityByPartCode(part_code);
	}
	
	@Override
	public Double getSumStorageAmount(String column, String keyword) throws Exception {
		Map<String, Object> map = super.handleParams2(column, keyword);
		return this.warehouseInDAO.getSumStorageAmount(map);
	}
}

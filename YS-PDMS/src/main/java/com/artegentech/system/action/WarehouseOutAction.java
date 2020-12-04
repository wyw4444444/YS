package com.artegentech.system.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.artegentech.system.service.IOutListService;
import com.artegentech.system.service.ITypeService;
import com.artegentech.system.service.IWarehouseInService;
import com.artegentech.system.service.IWarehouseOutService;
import com.artegentech.system.vo.WarehouseOut;
import com.artegentech.util.JsonDateValueProcessor;
import com.artegentech.util.JsonNullConvert;
import com.artegentech.util.action.AbstractAction;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

@Controller
@ResponseBody
@RequestMapping("warehouseOut/*")
public class WarehouseOutAction extends AbstractAction {

	@Resource
	private IWarehouseInService warehouseInService;
	
	@Resource
	private IWarehouseOutService warehouseOutService;
	
	@Resource
	private IOutListService outListService;
	
	@Resource
	private ITypeService typeService;

	@RequiresUser
	@RequestMapping("addBatch")
	public JSONObject addBatch(HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		Long lastSuccessNum = (long) 0;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy/MM/dd");
			String receivedString = request.getParameter("data");
			JsonConfig jsonConfig = new JsonConfig();
			jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
			JSONArray jsonArray = JSONArray.fromObject(receivedString, jsonConfig);
			
			String pre_out_id = "OUT" + sdf.format(new Date());
			Long currentIdNum = this.warehouseOutService.getDayIdNum(pre_out_id);
			for (int i = 1; i < jsonArray.size(); i++) {
				JSONArray jsonArray2 = JSONArray.fromObject(jsonArray.get(i), jsonConfig);
				WarehouseOut warehouseOut = new WarehouseOut();
				String out_id = pre_out_id + String.format("%03d", currentIdNum + i);
				Date out_date = sdf2.parse((String) jsonArray2.get(0));
				String part_code = (String) jsonArray2.get(1);
				Double total_quantity = Double.parseDouble((String) jsonArray2.get(2));
				Integer out_reason = Integer.parseInt((String) jsonArray2.get(3));
				String pdr_no = (String) jsonArray2.get(4);
				String member_id = (String) jsonArray2.get(5);
				String note = (String) jsonArray2.get(6);
				Integer locked = 0;
				Date lock_date = null;
				
				String string_out_reason = this.typeService.getById(out_reason).getSub_type();
				if("正常订单出库".equals(string_out_reason)) {
					if(pdr_no==null || "".equals(pdr_no)) {
						result.put("result", false);
						result.put("num", lastSuccessNum);
						JSONObject json = JSONObject.fromObject(result);
						return json;
					}
				}
				
				warehouseOut.setOut_id(out_id);
				warehouseOut.setOut_date(out_date);
				warehouseOut.setPart_code(part_code);
				warehouseOut.setTotal_quantity(total_quantity);
				warehouseOut.setOut_reason(out_reason);
				warehouseOut.setPdr_no(pdr_no);
				warehouseOut.setMember_id(member_id);
				warehouseOut.setNote(note);
				warehouseOut.setLocked(locked);
				warehouseOut.setLock_date(lock_date);
				
				Map<String, Object> result1 = this.warehouseOutService.add(warehouseOut);
				if ((boolean) result1.get("result") == true) {
					lastSuccessNum++;
				} else {
					result.put("result", false);
					result.put("num", lastSuccessNum);
					JSONObject json = JSONObject.fromObject(result);
					return json;
				}
			}
			result.put("result", true);
			JSONObject json = JSONObject.fromObject(result);
			return json;
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", false);
			result.put("num", lastSuccessNum);
			JSONObject json = JSONObject.fromObject(result);
			return json;
		}
	}
	
	@RequiresUser
	@RequestMapping("add")
	public JSONObject add(HttpServletRequest request) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
		WarehouseOut warehouseOut = new WarehouseOut();
		
		// 先生成新的出库记录编号
		String pre_out_id = "OUT" + sdf.format(new Date());
		String out_id = pre_out_id + String.format("%03d", this.warehouseOutService.getDayIdNum(pre_out_id) + 1);
		System.out.println("add out_id : " + out_id);
		
		Date out_date = sdf2.parse(request.getParameter("out_date"));
		String part_code = request.getParameter("part_code");
		Double total_quantity = Double.parseDouble(request.getParameter("total_quantity"));
		Integer out_reason = Integer.parseInt(request.getParameter("out_reason"));
		String pdr_no = request.getParameter("pdr_no");
		String member_id = request.getParameter("member_id");
		String note = request.getParameter("note");
		Integer locked = 0;
		Date lock_date = null;
		
		warehouseOut.setOut_id(out_id);
		warehouseOut.setOut_date(out_date);
		warehouseOut.setPart_code(part_code);
		warehouseOut.setTotal_quantity(total_quantity);
		warehouseOut.setOut_reason(out_reason);
		warehouseOut.setPdr_no(pdr_no);
		warehouseOut.setMember_id(member_id);
		warehouseOut.setNote(note);
		warehouseOut.setLocked(locked);
		warehouseOut.setLock_date(lock_date);
		
		// 需要先判断此product_no的剩余数量总数是否大于等于此次的出库数量
		// 如果大于等于，则允许新增
		// 如果小于，则不允许新增出库记录
		Map<String, Object> result = this.warehouseOutService.add(warehouseOut);
		JSONObject json = JSONObject.fromObject(result);
		return json;
	}

	@RequiresUser
	@RequestMapping("update")
	public JSONObject update(HttpServletRequest request) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
		WarehouseOut warehouseOut = new WarehouseOut();
		String out_id = request.getParameter("out_id");
		Date out_date = sdf2.parse(request.getParameter("out_date"));
		String part_code = request.getParameter("part_code");
		Double total_quantity = Double.parseDouble(request.getParameter("total_quantity"));
		Integer out_reason = Integer.parseInt(request.getParameter("out_reason"));
		String pdr_no = request.getParameter("pdr_no");
		String member_id = request.getParameter("member_id");
		String note = request.getParameter("note");
		
		WarehouseOut warehouseOut2 = this.warehouseOutService.getById(out_id);
		
		// 锁定及锁定时间是不允许编辑的，只能维持系统中原来的数据，
		// 只有在执行“结账”动作的时候，才会对入库记录进行锁定的动作
		Integer locked = warehouseOut2.getLocked();
		Date lock_date = warehouseOut2.getLock_date();
		
		// flag1代表原料号，来记录是否需要重新计算系统数据，如果为true，则后面需要调用重新计算方法
		// flag2代表修改后的料号，是否需要新增记录
		boolean flag1 = false;
		boolean flag2 = false;
		
		if(locked==1) {
			result.put("msg", "此出库记录已经结账，不允许修改！");
			result.put("result", false);
		}
		String part_code2 = warehouseOut2.getPart_code();
		Double total_quantity2 = warehouseOut2.getTotal_quantity();
		// 如果修改后warehouseOut的料号有变更
		if(!part_code.equals(part_code2)) {
			if(result.isEmpty()) {
				// 原料号需要取消出库的数量，则肯定需要重新计算
				flag1 = true;
				// 新料号需要确认该料号在系统中的剩余数量是否大于此笔的出库数量
				Double newPartSusQuantity = this.warehouseInService.getSumSurplusQuantityByPartCode(part_code);
				if(Double.doubleToLongBits(total_quantity) <= Double.doubleToLongBits(newPartSusQuantity)) {
					// 如果剩余数量大于等于此笔出库数量的话，则允许修改
					flag2 = true;
				}else {
					// 如果剩余数量小于此笔出库数量的话，报错
					result.put("msg", "变更后的料号剩余数量不足以支持此次出库数量，请确认！");
					result.put("result", false);
				}
			}
		}else {
			// 如果料号没有修改，只是修改了 数量和单价的话，执行下面的判断程序
			// 判断数量有变化的 
			if(result.isEmpty()) {
				// result为null，则说明判断过程中没有错误，继续往下执行
				// 如果数量有变更
				if(Double.doubleToLongBits(total_quantity) != Double.doubleToLongBits(total_quantity2)) {
					// 先判断数量是增加还是减少
					if(Double.doubleToLongBits(total_quantity) > Double.doubleToLongBits(total_quantity2)) {
						// 如果是数量增加的话
						Double addQuantity = total_quantity - total_quantity2;
						Double partSusQuantity = this.warehouseInService.getSumSurplusQuantityByPartCode(part_code);
						if(Double.doubleToLongBits(addQuantity) <= Double.doubleToLongBits(partSusQuantity)) {
							flag1 = true;
						}else {
							result.put("msg", "此品号剩余数量不足以支持增加的数量，请确认！");
							result.put("result", false);
						}
					}else {
						flag1 = true;
					}
				}
			}
		}
		
		if(result.isEmpty()) {
			// result为null，则说明没有判断出错误，向下执行
			// flag1为true，则说明需要针对原料号重新计算，则先执行修改动作，再执行重新计算动作
			// flag1为false，则说明不需要针对原料号重新计算，直接执行修改动作即可
			// flag2为true，则说明需要针对修改后的品号新增出库记录
			// flag2为false，则说明没有修改品号，不需要新增出库记录
			// 所以直接先执行sale的修改动作，然后再根据flag的值执行重新计算动作
			warehouseOut.setOut_id(out_id);
			warehouseOut.setOut_date(out_date);
			warehouseOut.setPart_code(part_code);
			warehouseOut.setTotal_quantity(total_quantity);
			warehouseOut.setOut_reason(out_reason);
			warehouseOut.setPdr_no(pdr_no);
			warehouseOut.setMember_id(member_id);
			warehouseOut.setNote(note);
			locked = 0;
			lock_date = null;
			warehouseOut.setLocked(locked);
			warehouseOut.setLock_date(lock_date);
			
			result = this.warehouseOutService.edit(warehouseOut, warehouseOut2, flag1, flag2);
		}
		JSONObject json = JSONObject.fromObject(result);
		return json;
	}

	/**
	 * 需要传回查找到的分页结果，并且传回总数据的行数
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequiresUser
	@RequestMapping("listSplit")
	public JSONObject listSplit(HttpServletRequest request) throws Exception {
		Integer currentPage = Integer.parseInt(request.getParameter("currentPage"));
		Integer lineSize = Integer.parseInt(request.getParameter("lineSize"));
		String column = request.getParameter("column");
		String keyword = request.getParameter("keyword");
		if (column == null || "".equals(column) || "undefined".equals(column)) {
			column = null;
		}
		if (keyword == null || "".equals(keyword) || "undefined".equals(keyword)) {
			keyword = null;
		}
		Integer dateType = Integer.parseInt(request.getParameter("dateType"));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String start_date_string = null;
		String end_date_string = null;
		if(dateType == 1) {
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy");
			String year = sdf2.format(new Date());
			start_date_string = year + "-01-01";
			end_date_string = year + "-12-31";
		}else if(dateType == 2) {
			start_date_string = request.getParameter("start_date");
			end_date_string = request.getParameter("end_date");
		}
		Map<String, Object> map = new HashMap<String, Object>();
		Long count = (long) 0;
		List<WarehouseOut> result = new ArrayList<WarehouseOut>();
		Date start_date = null;
		Date end_date = null;
		if(start_date_string!=null && !"".equals(start_date_string)) {
			start_date = sdf.parse(start_date_string);
		}
		if(end_date_string!=null && !"".equals(end_date_string)) {
			end_date = sdf.parse(end_date_string);
		}
		count = this.warehouseOutService.getAllCount(column, keyword, start_date, end_date);
		result = this.warehouseOutService.getAllSplit(column, keyword, currentPage, lineSize, start_date, end_date);
		map.put("count", count);
		map.put("list", result);
		JSONObject json = JSONObject.fromObject(map);
		JsonNullConvert.filterNull(json);
		return json;
	}

	@RequiresUser
	@SuppressWarnings("unchecked")
	@RequestMapping("list")
	public List<WarehouseOut> list(HttpServletRequest request) throws Exception {
		List<WarehouseOut> result = new ArrayList<WarehouseOut>();
		result = this.warehouseOutService.getAll();

		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
		JSONArray jsonArray = JSONArray.fromObject(result, jsonConfig);
		JsonNullConvert.filterNull(jsonArray);
		return jsonArray;
	}
	
	@Override
	public String getType() {
		return null;
	}

}

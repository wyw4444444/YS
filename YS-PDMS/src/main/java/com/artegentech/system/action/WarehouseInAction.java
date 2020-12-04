package com.artegentech.system.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
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
import com.artegentech.system.vo.OutList;
import com.artegentech.system.vo.WarehouseIn;
import com.artegentech.system.vo.WarehouseOut;
import com.artegentech.util.JsonDateValueProcessor;
import com.artegentech.util.JsonNullConvert;
import com.artegentech.util.action.AbstractAction;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

@Controller
@ResponseBody
@RequestMapping("warehouseIn/*")
public class WarehouseInAction extends AbstractAction {

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
	public boolean addBatch(HttpServletRequest request) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy/MM/dd");
		String receivedString = request.getParameter("data");
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
		JSONArray jsonArray = JSONArray.fromObject(receivedString, jsonConfig);
		List<WarehouseIn> list = new ArrayList<WarehouseIn>();
		// 先生成新的入库记录编号
		String pre_in_id = "IN" + sdf.format(new Date());
		Long currentIdNum = this.warehouseInService.getDayIdNum(pre_in_id);
		for (int i = 1; i < jsonArray.size(); i++) {
			// 从第二行数据开始读取，第一行是为了完全显示字段所用的测试数据
			JSONArray jsonArray2 = JSONArray.fromObject(jsonArray.get(i), jsonConfig);
			Date in_date = sdf2.parse((String) jsonArray2.get(0));
			String part_code = (String) jsonArray2.get(1);
			Double quantity = Double.parseDouble((String) jsonArray2.get(2));
			String pur_sheet_id = (String) jsonArray2.get(3);
			String price_string = (String) jsonArray2.get(4);
			Double price = null;
			String member_id = (String) jsonArray2.get(5);
			String note = (String) jsonArray2.get(6);
			Integer in_reason = Integer.parseInt((String) jsonArray2.get(7));
			String pdr_no = (String) jsonArray2.get(8);
			Double surplus_quantity = quantity;
			Integer locked = 0;
			Date lock_date = null;
			String history_in_id = (String) jsonArray2.get(9);
			
			String string_in_reason = this.typeService.getById(in_reason).getSub_type();
			if("正常采购入库".equals(string_in_reason)) {
				// 如果是正常采购入库的话，采购单号可以为空，但是price不能为空
				if(pur_sheet_id==null || "".equals(pur_sheet_id)) {
					if(price_string==null || "".equals(price_string)) {
						return false;
					} else {
						price = Double.parseDouble(price_string);
					}
				}else {
					if(price_string==null || "".equals(price_string)) {
						return false;
					} else {
						price = Double.parseDouble(price_string);
					}
				}
				currentIdNum++;
				String in_id = pre_in_id + String.format("%04d", currentIdNum);
				WarehouseIn warehouseIn = new WarehouseIn();
				warehouseIn.setIn_id(in_id);
				warehouseIn.setIn_date(in_date);
				warehouseIn.setPart_code(part_code);
				warehouseIn.setQuantity(quantity);
				warehouseIn.setPur_sheet_id(pur_sheet_id);
				warehouseIn.setPrice(price);
				warehouseIn.setMember_id(member_id);
				warehouseIn.setNote(note);
				warehouseIn.setIn_reason(in_reason);
				warehouseIn.setPdr_no(pdr_no);
				warehouseIn.setSurplus_quantity(surplus_quantity);
				warehouseIn.setLocked(locked);
				warehouseIn.setLock_date(lock_date);
				warehouseIn.setHistory_in_id(history_in_id);
				list.add(warehouseIn);
			}else if("退库".equals(string_in_reason)) {
				// 如果是退库的话，则PDR号不能为空，并且单价需要根据PDR号抓出出库记录中对应ID反序后，按照数量计算对应几笔出库记录，抓出对应的单价
				if(pdr_no==null || "".equals(pdr_no)) {
					return false;
				}
				if(Double.doubleToLongBits(quantity)>this.warehouseOutService.getSumQuantityByPDRNoPartCode(pdr_no, part_code)) {
					System.err.println("退库数量大于此料号在此PDR号的出库数量！");
					return false;
				}
				List<WarehouseOut> warehouseOutList= this.warehouseOutService.getAllByPDRNoPartCode(pdr_no, part_code);
				Iterator<WarehouseOut> iter = warehouseOutList.iterator();
				if (iter != null) {
					while (iter.hasNext()) {
						WarehouseOut warehouseOut = iter.next();
						Double tempQuantity = quantity;
						List<OutList> outLists = this.outListService.getAllByOutIdDESC(warehouseOut.getOut_id());
						Iterator<OutList> iterOutList = outLists.iterator();
						if (iterOutList != null) {
							while (iterOutList.hasNext()) {
								OutList outList = iterOutList.next();
								Double listQuantity = outList.getQuantity();
								// 如果list中的数量大于等于此次退库数量的话，则直接将此出库记录对应的入库记录单价给到退库记录
								if(Double.doubleToLongBits(listQuantity) >= Double.doubleToLongBits(tempQuantity)) {
									price = this.warehouseInService.getById(outList.getIn_id()).getPrice();
									currentIdNum++;
									String in_id = pre_in_id + String.format("%04d", currentIdNum);
									WarehouseIn warehouseIn = new WarehouseIn();
									warehouseIn.setIn_id(in_id);
									warehouseIn.setIn_date(in_date);
									warehouseIn.setPart_code(part_code);
									warehouseIn.setQuantity(tempQuantity);
									warehouseIn.setPur_sheet_id(pur_sheet_id);
									warehouseIn.setPrice(price);
									warehouseIn.setMember_id(member_id);
									warehouseIn.setNote(note);
									warehouseIn.setIn_reason(in_reason);
									warehouseIn.setPdr_no(pdr_no);
									warehouseIn.setSurplus_quantity(surplus_quantity);
									warehouseIn.setLocked(locked);
									warehouseIn.setLock_date(lock_date);
									warehouseIn.setHistory_in_id(history_in_id);
									list.add(warehouseIn);
									tempQuantity = tempQuantity - listQuantity;
									break;
								}else{
									Double priceTemp = this.warehouseInService.getById(outList.getIn_id()).getPrice();
									String pur_sheet_idTemp = this.warehouseInService.getById(outList.getIn_id()).getPur_sheet_id();
									currentIdNum++;
									String in_id = pre_in_id + String.format("%04d", currentIdNum);
									WarehouseIn warehouseIn = new WarehouseIn();
									warehouseIn.setIn_id(in_id);
									warehouseIn.setIn_date(in_date);
									warehouseIn.setPart_code(part_code);
									warehouseIn.setQuantity(listQuantity);
									warehouseIn.setPur_sheet_id(pur_sheet_idTemp);
									warehouseIn.setPrice(priceTemp);
									warehouseIn.setMember_id(member_id);
									warehouseIn.setNote(note);
									warehouseIn.setIn_reason(in_reason);
									warehouseIn.setPdr_no(pdr_no);
									warehouseIn.setSurplus_quantity(surplus_quantity);
									warehouseIn.setLocked(locked);
									warehouseIn.setLock_date(lock_date);
									warehouseIn.setHistory_in_id(history_in_id);
									list.add(warehouseIn);
									tempQuantity = tempQuantity - listQuantity;
								}
							}
						}
						if(tempQuantity == 0) {
							break;
						}
						
					}
				}
			}else {
				currentIdNum++;
				String in_id = pre_in_id + String.format("%04d", currentIdNum);
				WarehouseIn warehouseIn = new WarehouseIn();
				warehouseIn.setIn_id(in_id);
				warehouseIn.setIn_date(in_date);
				warehouseIn.setPart_code(part_code);
				warehouseIn.setQuantity(quantity);
				warehouseIn.setPur_sheet_id(pur_sheet_id);
				warehouseIn.setPrice(price);
				warehouseIn.setMember_id(member_id);
				warehouseIn.setNote(note);
				warehouseIn.setIn_reason(in_reason);
				warehouseIn.setPdr_no(pdr_no);
				warehouseIn.setSurplus_quantity(surplus_quantity);
				warehouseIn.setLocked(locked);
				warehouseIn.setLock_date(lock_date);
				warehouseIn.setHistory_in_id(history_in_id);
				list.add(warehouseIn);
			}
		}
		return this.warehouseInService.addBatch(list);
	}
	
	@RequiresUser
	@RequestMapping("add")
	public boolean add(HttpServletRequest request) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
		
		// 先生成新的入库记录编号
		String pre_in_id = "IN" + sdf.format(new Date());
		Long currentIdNum = this.warehouseInService.getDayIdNum(pre_in_id);
		
		Date in_date = sdf2.parse(request.getParameter("in_date"));
		String part_code = request.getParameter("part_code");
		Double quantity = Double.parseDouble(request.getParameter("quantity"));
		String pur_sheet_id = request.getParameter("pur_sheet_id");
		String price_string = request.getParameter("price");
		Double price = null;
		String member_id = request.getParameter("member_id");
		String note = request.getParameter("note");
		Integer in_reason = Integer.parseInt(request.getParameter("in_reason"));
		String pdr_no = request.getParameter("pdr_no");
		Double surplus_quantity = quantity;
		Integer locked = 0;
		Date lock_date = null;
		String history_in_id = request.getParameter("history_in_id");
		
		String string_in_reason = this.typeService.getById(in_reason).getSub_type();
		if ("正常采购入库".equals(string_in_reason)) {
			// 如果是正常采购入库的话，采购单号可以为空，但是price不能为空
			if(pur_sheet_id==null || "".equals(pur_sheet_id)) {
				if(price_string==null || "".equals(price_string)) {
					return false;
				} else {
					price = Double.parseDouble(price_string);
				}
			}else {
				if(price_string==null || "".equals(price_string)) {
					return false;
				} else {
					price = Double.parseDouble(price_string);
				}
			}
			currentIdNum++;
			String in_id = pre_in_id + String.format("%04d", currentIdNum);
			WarehouseIn warehouseIn = new WarehouseIn();
			warehouseIn.setIn_id(in_id);
			warehouseIn.setIn_date(in_date);
			warehouseIn.setPart_code(part_code);
			warehouseIn.setQuantity(quantity);
			warehouseIn.setPur_sheet_id(pur_sheet_id);
			warehouseIn.setPrice(price);
			warehouseIn.setMember_id(member_id);
			warehouseIn.setNote(note);
			warehouseIn.setIn_reason(in_reason);
			warehouseIn.setPdr_no(pdr_no);
			warehouseIn.setSurplus_quantity(surplus_quantity);
			warehouseIn.setLocked(locked);
			warehouseIn.setLock_date(lock_date);
			warehouseIn.setHistory_in_id(history_in_id);
			return this.warehouseInService.add(warehouseIn);
		} else if ("退库".equals(string_in_reason)) {
			// 如果是退库的话，则PDR号不能为空，并且单价需要根据PDR号抓出出库记录中对应ID反序后，按照数量计算对应几笔出库记录，抓出对应的单价
			if(pdr_no==null || "".equals(pdr_no)) {
				return false;
			}
			if(Double.doubleToLongBits(quantity)>this.warehouseOutService.getSumQuantityByPDRNoPartCode(pdr_no, part_code)) {
				System.err.println("退库数量大于此料号在此PDR号的出库数量！");
				return false;
			}
			List<WarehouseOut> warehouseOutList= this.warehouseOutService.getAllByPDRNoPartCode(pdr_no, part_code);
			Iterator<WarehouseOut> iter = warehouseOutList.iterator();
			List<WarehouseIn> list = new ArrayList<WarehouseIn>();
			if (iter != null) {
				while (iter.hasNext()) {
					WarehouseOut warehouseOut = iter.next();
					Double tempQuantity = quantity;
					List<OutList> outLists = this.outListService.getAllByOutIdDESC(warehouseOut.getOut_id());
					Iterator<OutList> iterOutList = outLists.iterator();
					if (iterOutList != null) {
						while (iterOutList.hasNext()) {
							OutList outList = iterOutList.next();
							Double listQuantity = outList.getQuantity();
							// 如果list中的数量大于等于此次退库数量的话，则直接将此出库记录对应的入库记录单价给到退库记录
							if(Double.doubleToLongBits(listQuantity) >= Double.doubleToLongBits(tempQuantity)) {
								price = this.warehouseInService.getById(outList.getIn_id()).getPrice();
								currentIdNum++;
								String in_id = pre_in_id + String.format("%04d", currentIdNum);
								WarehouseIn warehouseIn = new WarehouseIn();
								warehouseIn.setIn_id(in_id);
								warehouseIn.setIn_date(in_date);
								warehouseIn.setPart_code(part_code);
								warehouseIn.setQuantity(tempQuantity);
								warehouseIn.setPur_sheet_id(pur_sheet_id);
								warehouseIn.setPrice(price);
								warehouseIn.setMember_id(member_id);
								warehouseIn.setNote(note);
								warehouseIn.setIn_reason(in_reason);
								warehouseIn.setPdr_no(pdr_no);
								warehouseIn.setSurplus_quantity(tempQuantity);
								warehouseIn.setLocked(locked);
								warehouseIn.setLock_date(lock_date);
								warehouseIn.setHistory_in_id(history_in_id);
								list.add(warehouseIn);
								tempQuantity = tempQuantity - listQuantity;
								break;
							}else{
								Double priceTemp = this.warehouseInService.getById(outList.getIn_id()).getPrice();
								String pur_sheet_idTemp = this.warehouseInService.getById(outList.getIn_id()).getPur_sheet_id();
								currentIdNum++;
								String in_id = pre_in_id + String.format("%04d", currentIdNum);
								WarehouseIn warehouseIn = new WarehouseIn();
								warehouseIn.setIn_id(in_id);
								warehouseIn.setIn_date(in_date);
								warehouseIn.setPart_code(part_code);
								warehouseIn.setQuantity(listQuantity);
								warehouseIn.setPur_sheet_id(pur_sheet_idTemp);
								warehouseIn.setPrice(priceTemp);
								warehouseIn.setMember_id(member_id);
								warehouseIn.setNote(note);
								warehouseIn.setIn_reason(in_reason);
								warehouseIn.setPdr_no(pdr_no);
								warehouseIn.setSurplus_quantity(listQuantity);
								warehouseIn.setLocked(locked);
								warehouseIn.setLock_date(lock_date);
								warehouseIn.setHistory_in_id(history_in_id);
								list.add(warehouseIn);
								tempQuantity = tempQuantity - listQuantity;
							}
						}
					}
					if(tempQuantity == 0) {
						break;
					}
					
				}
			}
			System.out.println("************************************ list **************************************");
			System.out.println(list);
			return this.warehouseInService.addBatch(list);
		}else {
			currentIdNum++;
			String in_id = pre_in_id + String.format("%04d", currentIdNum);
			WarehouseIn warehouseIn = new WarehouseIn();
			warehouseIn.setIn_id(in_id);
			warehouseIn.setIn_date(in_date);
			warehouseIn.setPart_code(part_code);
			warehouseIn.setQuantity(quantity);
			warehouseIn.setPur_sheet_id(pur_sheet_id);
			warehouseIn.setPrice(price);
			warehouseIn.setMember_id(member_id);
			warehouseIn.setNote(note);
			warehouseIn.setIn_reason(in_reason);
			warehouseIn.setPdr_no(pdr_no);
			warehouseIn.setSurplus_quantity(surplus_quantity);
			warehouseIn.setLocked(locked);
			warehouseIn.setLock_date(lock_date);
			warehouseIn.setHistory_in_id(history_in_id);
			return this.warehouseInService.add(warehouseIn);
		}
	}

	@RequiresUser
	@RequestMapping("update")
	public JSONObject update(HttpServletRequest request) throws Exception {
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
		WarehouseIn warehouseIn = new WarehouseIn();
		String in_id = request.getParameter("in_id");
		Date in_date = sdf2.parse(request.getParameter("in_date"));
		String part_code = request.getParameter("part_code");
		Double quantity = Double.parseDouble(request.getParameter("quantity"));
		String pur_sheet_id = request.getParameter("pur_sheet_id");
		Double price = Double.parseDouble(request.getParameter("price"));
		String member_id = request.getParameter("member_id");
		String note = request.getParameter("note");
		Integer in_reason = Integer.parseInt(request.getParameter("in_reason"));
		String pdr_no = request.getParameter("pdr_no");
		String history_in_id = request.getParameter("history_in_id");
		// 剩余数量、是否出库完毕
		// 以接收到的新数据与数据库中的旧数据对比后，来决定是否重新计算，再生成对应字段的新值
		// 先获取数据库中此入库ID对应的入库记录资料
		WarehouseIn warehouseIn2 = this.warehouseInService.getById(in_id);
		//System.out.println("warehouseIn2 : ");
		//System.out.println(warehouseIn2);
		String part_code2 = warehouseIn2.getPart_code();
		
		// 先将剩余数量设置为原数据，后面如果重新计算会自动更新
		Double surplus_quantity = warehouseIn2.getSurplus_quantity();
		// 锁定及锁定时间是不允许编辑的，只能维持系统中原来的数据，
		// 只有在执行“结账”动作的时候，才会对入库记录进行锁定的动作
		Integer locked = warehouseIn2.getLocked();
		Date lock_date = warehouseIn2.getLock_date();
		Double quantity2 = warehouseIn2.getQuantity();
		
		// flag来记录是否需要重新计算系统数据，如果为true，则后面需要调用重新计算方法
		boolean flag = false;
		
		Map<String, Object> result = new HashMap<String, Object>();
		if(result.isEmpty()) {
			// 如果记录已经锁定的话，则不允许修改此笔入库记录的料号
			if(warehouseIn2.getLocked()==1) {
				result.put("msg", "此入库记录已经结账，不允许修改！");
				result.put("result", false);
			}
			
			Double prePartInNoLockQuantity = this.warehouseInService.getSumQuantityByPartCode(part_code2);
			Double prePartOutNoLockQuantity = this.warehouseOutService.getSumQuantityByPartCode(part_code2);
			// 如果修改后warehouseIn的品号有变更
			if(!part_code.equals(part_code2)) {
				if(result.isEmpty()) {
					// 原此笔记录的剩余数量与入库数量相同，则说明原此笔记录没有出库过，即使变了料号，也不影响，不需要重新计算
					// 原此笔记录的剩余数量小于入库数量，则说明有从此笔记录出库过，则需要向下判断
					if(Double.doubleToLongBits(warehouseIn2.getSurplus_quantity()) < Double.doubleToLongBits(quantity2)) {
						// 原料号扣除此笔数量之后的总入库数量是否大于等于所有未锁定出库数量
						if(Double.doubleToLongBits(prePartInNoLockQuantity-quantity2) >= Double.doubleToLongBits(prePartOutNoLockQuantity)) {
							// 如果大于等于的话，则需执行重新计算
							flag = true;
						} else {
							result.put("msg", "修改后原料号总入库数量小于总出库数量！");
							result.put("result", false);
						}
					}
					// 修改后的料号都是一笔新资料，所以肯定不会发生已出库的状况，不需要判断
				}
			}else {
				// 如果料号没有修改，只是修改了 数量和单价的话，执行下面的判断程序
				// 先判断数量有变化的 
				if(result.isEmpty()) {
					// result为null，则说明判断过程中没有错误，继续往下执行
					// 如果数量有变更
					if(Double.doubleToLongBits(quantity) != Double.doubleToLongBits(warehouseIn2.getQuantity())) {
						System.out.println("前后数量不同！");
						System.out.println("quantity : " + quantity);
						System.out.println("quantity2 : " + quantity2);
						// 首先判断数量是增加还是减少
						// 如果是数量增加
						Double priSurQuantity = warehouseIn2.getSurplus_quantity();
						if(Double.doubleToLongBits(quantity) > Double.doubleToLongBits(quantity2)) {
							// 如果剩余数量等于入库数量，则说明此笔记录没有再参与出库过，即使修改数量，也不影响，故不需要重新计算
							// 但是在修改数量的基础上要同时修改剩余数量，增加对应的值
							if(Double.doubleToLongBits(priSurQuantity) == Double.doubleToLongBits(quantity2)) {
								surplus_quantity = surplus_quantity + (quantity - quantity2);
							}else if(Double.doubleToLongBits(priSurQuantity) > 0 && Double.doubleToLongBits(priSurQuantity) < Double.doubleToLongBits(quantity2)) {
								// 如果剩余数量大于0，且小于入库数量，则说明此笔记录还没有全部出库完毕，数量增加的话，直接针对此笔记录的入库数量和剩余数量都增加对应的值就可以了。不需重新计算
								surplus_quantity = surplus_quantity + (quantity - quantity2);
							}else if(Double.doubleToLongBits(priSurQuantity) == 0) {
								// 如果剩余数量等于0，则说明此笔记录已经全部出库，那么必须重新计算
								flag = true;
							}
						}else {
							if(Double.doubleToLongBits(quantity)==0) {
								// 如果数量修改为0,也就是取消此笔入库记录的话
								// 需要判断其剩余数量和入库数量的大小
								// 如果剩余数量与入库数量相同，说明没有出库过，则直接变更为0即可
								if(Double.doubleToLongBits(priSurQuantity) == Double.doubleToLongBits(quantity2)) {
									surplus_quantity = quantity;
								}else {
									// 如果剩余数量小于入库数量，则需要先判断修改后的入库总数量是否大于等于未锁定的出库数量
									if(Double.doubleToLongBits(prePartInNoLockQuantity-(quantity2 - quantity)) >= Double.doubleToLongBits(prePartOutNoLockQuantity)) {
										flag = true;
									}else {
										result.put("msg", "修改数量后总入库数量小于总出库数量！");
										result.put("result", false);
									}
								}
							}else {
								// 如果是数量减少，且不为0
								// 如果剩余数量等于入库数量，则说明此笔记录没有再参与出库过，即使修改数量，也不影响，故不需要重新计算
								// 但是在修改数量的基础上要同时修改剩余数量，减少对应的值
								if(Double.doubleToLongBits(priSurQuantity) == Double.doubleToLongBits(quantity2)) {
									surplus_quantity = surplus_quantity + (quantity - quantity2);
								}else if(Double.doubleToLongBits(priSurQuantity) >= 0) {
									// 如果剩余数量大于等于0，则需要先判断修改后的未锁定的入库总数量是否大于等于未锁定的出库数量
									if(Double.doubleToLongBits(prePartInNoLockQuantity-(quantity2 - quantity)) >= Double.doubleToLongBits(prePartOutNoLockQuantity)) {
										// 如果是的话
										// 如果剩余数量等于0，则必须重新计算
										if(Double.doubleToLongBits(priSurQuantity)==0) {
											flag = true;
										}else {
											// 如果剩余数量大于0，则判断剩余数量减去“增加”的数量是否大于0
											if(Double.doubleToLongBits(priSurQuantity-(quantity2 - quantity))<0) {
												flag=true;
											}else {
												surplus_quantity = priSurQuantity-(quantity2 - quantity);
											}
										}
									}else {
										result.put("msg", "修改数量后总入库数量小于总出库数量！");
										result.put("result", false);
									}
								}
							}
						}
					}
				}
			}
		}
		
		if(result.isEmpty()) {
			// result为null，则说明没有判断出错误，向下执行
			// flag为true，则说明需要重新计算，则先执行修改动作，再执行重新计算动作
			// flag为false，则说明不需要重新计算，直接执行修改动作即可
			// 所以直接先执行warehouseIn的修改动作，然后再根据flag的值执行重新计算动作
			warehouseIn.setIn_id(in_id);
			warehouseIn.setIn_date(in_date);
			warehouseIn.setPart_code(part_code);
			warehouseIn.setQuantity(quantity);
			warehouseIn.setPur_sheet_id(pur_sheet_id);
			warehouseIn.setPrice(price);
			warehouseIn.setMember_id(member_id);
			warehouseIn.setNote(note);
			warehouseIn.setIn_reason(in_reason);
			warehouseIn.setPdr_no(pdr_no);
			warehouseIn.setSurplus_quantity(surplus_quantity);
			warehouseIn.setLocked(locked);
			warehouseIn.setLock_date(lock_date);
			warehouseIn.setHistory_in_id(history_in_id);
			
			result = this.warehouseInService.edit(warehouseIn, warehouseIn2, flag);
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
		List<WarehouseIn> result = new ArrayList<WarehouseIn>();
		Date start_date = null;
		Date end_date = null;
		if(start_date_string!=null && !"".equals(start_date_string)) {
			start_date = sdf.parse(start_date_string);
		}
		if(end_date_string!=null && !"".equals(end_date_string)) {
			end_date = sdf.parse(end_date_string);
		}
		count = this.warehouseInService.getAllCount(column, keyword, start_date, end_date);
		result = this.warehouseInService.getAllSplit(column, keyword, currentPage, lineSize, start_date, end_date);
		map.put("count", count);
		map.put("list", result);
		JSONObject json = JSONObject.fromObject(map);
		JsonNullConvert.filterNull(json);
		return json;
	}
	
	/**
	 * 需要传回查找到的分页结果，并且传回总数据的行数（将库存整理后的资料拿掉，只保留原始数据）
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequiresUser
	@RequestMapping("listSplit2")
	public JSONObject listSplit2(HttpServletRequest request) throws Exception {
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
		List<WarehouseIn> result = new ArrayList<WarehouseIn>();
		Date start_date = null;
		Date end_date = null;
		if(start_date_string!=null && !"".equals(start_date_string)) {
			start_date = sdf.parse(start_date_string);
		}
		if(end_date_string!=null && !"".equals(end_date_string)) {
			end_date = sdf.parse(end_date_string);
		}
		count = this.warehouseInService.getAllCount2(column, keyword, start_date, end_date);
		result = this.warehouseInService.getAllSplit2(column, keyword, currentPage, lineSize, start_date, end_date);
		map.put("count", count);
		map.put("list", result);
		JSONObject json = JSONObject.fromObject(map);
		JsonNullConvert.filterNull(json);
		return json;
	}
	
	/**
	 * 需要传回查找到的分页结果，并且传回总数据的行数
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequiresUser
	@RequestMapping("listUnlockedSplit")
	public JSONObject listUnlockedSplit(HttpServletRequest request) throws Exception {
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
		List<WarehouseIn> result = new ArrayList<WarehouseIn>();
		Date start_date = null;
		Date end_date = null;
		if(start_date_string!=null && !"".equals(start_date_string)) {
			start_date = sdf.parse(start_date_string);
		}
		if(end_date_string!=null && !"".equals(end_date_string)) {
			end_date = sdf.parse(end_date_string);
		}
		count = this.warehouseInService.getAllUnlockedCount(column, keyword, start_date, end_date);
		result = this.warehouseInService.getAllUnlockedSplit(column, keyword, currentPage, lineSize, start_date, end_date);
		map.put("count", count);
		map.put("list", result);
		JSONObject json = JSONObject.fromObject(map);
		JsonNullConvert.filterNull(json);
		return json;
	}

	@RequiresUser
	@SuppressWarnings("unchecked")
	@RequestMapping("list")
	public List<WarehouseIn> list(HttpServletRequest request) throws Exception {
		List<WarehouseIn> result = new ArrayList<WarehouseIn>();
		result = this.warehouseInService.getAll();
		
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
		JSONArray jsonArray = JSONArray.fromObject(result, jsonConfig);
		JsonNullConvert.filterNull(jsonArray);
		return jsonArray;
	}

	/**
	 * 需要传回查找到的库存结果
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequiresUser
	@SuppressWarnings("unchecked")
	@RequestMapping("listStorage")
	public List<WarehouseIn> listStorage(HttpServletRequest request) throws Exception {
		List<WarehouseIn> result = new ArrayList<WarehouseIn>();
		result = this.warehouseInService.getAllStorage();
		
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
		JSONArray jsonArray = JSONArray.fromObject(result, jsonConfig);
		JsonNullConvert.filterNull(jsonArray);
		return jsonArray;
	}
	
	/**
	 * 需要传回查找到的库存分页结果，并且传回总数据的行数
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequiresUser
	@RequestMapping("listStorageSplit")
	public JSONObject listStorageSplit(HttpServletRequest request) throws Exception {
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
		Map<String, Object> map = new HashMap<String, Object>();
		Long count = (long) 0;
		List<WarehouseIn> result = new ArrayList<WarehouseIn>();
		count = this.warehouseInService.getAllStorageCount(column, keyword);
		result = this.warehouseInService.getAllStorageSplit(column, keyword, currentPage, lineSize);
		Double amount = this.warehouseInService.getSumStorageAmount(column, keyword);
		map.put("count", count);
		map.put("list", result);
		map.put("amount", amount);
		JSONObject json = JSONObject.fromObject(map);
		JsonNullConvert.filterNull(json);
		return json;
	}
	
	/**
	 * 需要传回查找到的库存分页结果，并且传回总数据的行数
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequiresUser
	@RequestMapping("listStorageByPartCodeSplit")
	public JSONObject listStorageByPartCodeSplit(HttpServletRequest request) throws Exception {
		Integer currentPage = Integer.parseInt(request.getParameter("currentPage"));
		Integer lineSize = Integer.parseInt(request.getParameter("lineSize"));
		Map<String, Object> map = new HashMap<String, Object>();
		Long count = (long) 0;
		List<WarehouseIn> result = new ArrayList<WarehouseIn>();
		count = this.warehouseInService.getAllStorageByPartCodeCount();
		result = this.warehouseInService.getAllStorageByPartCodeSplit(currentPage, lineSize);
		Double amount = this.warehouseInService.getSumStorageAmount(null, null);
		map.put("count", count);
		map.put("list", result);
		map.put("amount", amount);
		JSONObject json = JSONObject.fromObject(map);
		JsonNullConvert.filterNull(json);
		return json;
	}
	
	@Override
	public String getType() {
		return null;
	}

}

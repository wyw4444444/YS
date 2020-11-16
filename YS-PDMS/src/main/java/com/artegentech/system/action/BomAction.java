package com.artegentech.system.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.artegentech.system.dao.ITypeDAO;
import com.artegentech.system.service.IBomService;
import com.artegentech.system.service.ICheckLogService;
import com.artegentech.system.service.IPartInfoService;
import com.artegentech.system.vo.Bom;
import com.artegentech.system.vo.CheckLog;
import com.artegentech.system.vo.PartInfo;
import com.artegentech.system.vo.Type;
import com.artegentech.util.JsonNullConvert;
import com.artegentech.util.action.AbstractAction;
import com.artegentech.util.JsonDateValueProcessor;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

@Controller
@ResponseBody
@RequestMapping("bom/*")
public class BomAction extends AbstractAction {
	@Resource
	private IPartInfoService partInfoService;
	
	@Resource
	private ICheckLogService  checkLogService;
	
	@Resource
	private IBomService  bomService;
	
	@Resource
	private ITypeDAO typeDao;
	
	
	@RequiresUser
	@RequestMapping("addStocks")
	public JSONObject addStocks(HttpServletRequest request) throws Exception {
		
		String receivedString = request.getParameter("data");
		String member_id = request.getParameter("member_id");
		JsonConfig jsonConfig = new JsonConfig();
		JSONArray jsonArray = JSONArray.fromObject(receivedString, jsonConfig);
		
		List<Bom> list = new ArrayList<Bom>();
		List<Integer> list_part_code_upInPartInfo = new ArrayList<Integer>();
		List<Integer> list_part_code_upchecked = new ArrayList<Integer>();
		List<Integer> list_part_code_upLocked = new ArrayList<Integer>();
		List<Integer> list_part_code_upOther = new ArrayList<Integer>();
		List<Integer> list_part_code_downInPartInfo = new ArrayList<Integer>();
		List<Integer> list_unit = new ArrayList<Integer>();
		
		int count = 0;
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONArray jsonArray2 = JSONArray.fromObject(jsonArray.get(i), jsonConfig);

			String part_code_up = (String) jsonArray2.get(0);
			String part_code_down = (String) jsonArray2.get(1);
			Float dosage=Float.parseFloat((String) jsonArray2.get(2));
			Integer base=Integer.parseInt((String) jsonArray2.get(3));
			String unit = (String) jsonArray2.get(4);
			
			
			//確認part_code_up在partinfo中存在,且可以更新版本
			Integer ver=null;
			if("".equals(part_code_up)==false && part_code_up!=null) {
				PartInfo part=this.partInfoService.getByCode(part_code_up);
				if (part==null) {
					list_part_code_upInPartInfo.add(i);
				}else
				{
					ver=this.bomService.getNewVersion(part_code_up);
					if(ver==-2) {
						list_part_code_upLocked.add(i);
					}
					else if(ver==-3) {
						list_part_code_upchecked.add(i);
					}
					else if(ver==-1) {
						list_part_code_upOther.add(i);
					}
				}
			}
			//確認part_code_down在partinfo中存在
	
			if("".equals(part_code_down)==false && part_code_down!=null) {
				PartInfo part=this.partInfoService.getByCode(part_code_down);
				if (part==null) {
					list_part_code_downInPartInfo.add(i);
				}
			}
			
			
			//確認unit在type清單中有沒有或被锁定
			Map<String, Object> map1 = new HashMap<String, Object>();
			map1.put("parent_type", "单位");
			map1.put("sub_type", unit);
			map1.put("upper_id", 0);
			Type type=this.typeDao.findByParentAndSubAndUpper(map1);
			if (type==null||type.getLocked()==1) {
				list_unit.add(i);
			}

			Bom bom = new Bom();
			
			bom.setPart_code_up(part_code_up);
			bom.setPart_code_down(part_code_down);
			bom.setDosage(dosage);
			bom.setBase(base);
			bom.setUnit(unit);
			bom.setVer(ver);
			bom.setStatus(5);
			bom.setLocked(0);
			
			list.add(bom);
		}
		
		boolean result_confirm=false;
		boolean result_add=false;
		
		if(list_part_code_upInPartInfo.size()==0 && list_part_code_upchecked.size()==0 
				&&  list_part_code_upLocked.size()==0&&  list_part_code_upOther.size()==0
				&&  list_part_code_downInPartInfo.size()==0&&  list_unit.size()==0
			) {
			//result_confirm=true代表的是確認的結果沒有問題。
			result_confirm=true;
			result_add=this.bomService.addStocks(list,member_id);
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list_part_code_upInPartInfo", list_part_code_upInPartInfo);
		map.put("list_part_code_upchecked", list_part_code_upchecked);
		map.put("list_part_code_upLocked", list_part_code_upLocked);
		map.put("list_part_code_upOther", list_part_code_upOther);
		map.put("list_part_code_downInPartInfo", list_part_code_downInPartInfo);
		map.put("list_unit", list_unit);
		map.put("result_confirm", result_confirm);
		map.put("result_add", result_add);
		
		JSONObject json = JSONObject.fromObject(map);
		return json;
	}
	
	
	@RequiresUser
	@RequestMapping("add")
	public JSONObject add(HttpServletRequest request) throws Exception {
		String part_code_up = request.getParameter("part_code_up");
		String member_id = request.getParameter("member_id");
		String part_code_downs = request.getParameter("part_code_downs");
		String dosages = request.getParameter("dosages");
		String bases = request.getParameter("bases");
		String units = request.getParameter("units");
		
		List<String> part_code_downAry=new ArrayList<>();

		JsonConfig jsonConfig = new JsonConfig();
		JSONArray jsonArray= JSONArray.fromObject(part_code_downs, jsonConfig);
		for (int i = 0; i < jsonArray.size(); i++) {
			part_code_downAry.add((String)jsonArray.get(i));
		}
		
		List<Float> dosageAry=new ArrayList<>();


		JSONArray jsonArray1= JSONArray.fromObject(dosages, jsonConfig);
		for (int i = 0; i < jsonArray1.size(); i++) {
			dosageAry.add(Float.parseFloat((String)jsonArray1.get(i)));
		}
		
		List<Integer> baseAry=new ArrayList<>();

		JSONArray jsonArray2= JSONArray.fromObject(bases, jsonConfig);
		for (int i = 0; i < jsonArray2.size(); i++) {
			baseAry.add(Integer.parseInt((String)jsonArray2.get(i)));
		}
		
		List<String> unitAry=new ArrayList<>();

		JSONArray jsonArray3= JSONArray.fromObject(units, jsonConfig);
		for (int i = 0; i < jsonArray3.size(); i++) {
			unitAry.add((String)jsonArray3.get(i));
		}
		
		List<Integer> list_part_code_upInPartInfo = new ArrayList<Integer>();
		List<Integer> list_part_code_upchecked = new ArrayList<Integer>();
		List<Integer> list_part_code_upLocked = new ArrayList<Integer>();
		List<Integer> list_part_code_upOther = new ArrayList<Integer>();
		List<Integer> list_part_code_downInPartInfo = new ArrayList<Integer>();
		
		//確認part_code_up在partinfo中存在,且可以更新版本
//		Integer ver=this.bomService.getNewVersion(part_code_up);
		
		Integer ver=null;
	
		PartInfo part=this.partInfoService.getByCode(part_code_up);
		if (part==null) {
			list_part_code_upInPartInfo.add(0);
		}else
		{
			ver=this.bomService.getNewVersion(part_code_up);
			if(ver==-2) {
				list_part_code_upLocked.add(0);
			}
			else if(ver==-3) {
				list_part_code_upchecked.add(0);
			}
			else if(ver==-1) {
				list_part_code_upOther.add(0);
			}
		}
		
		List<Bom> list = new ArrayList<Bom>();
		
		for (int i = 0; i < part_code_downAry.size(); i++) {
			
			//確認part_code_down在partinfo中存在
			PartInfo part1=this.partInfoService.getByCode(part_code_downAry.get(i));
			if (part1==null) {
				list_part_code_downInPartInfo.add(i);
			}
			
			Bom bom = new Bom();
			bom.setPart_code_up(part_code_up);
			bom.setPart_code_down(part_code_downAry.get(i));
			bom.setDosage(dosageAry.get(i));
			bom.setBase(baseAry.get(i));
			bom.setUnit(unitAry.get(i));
			bom.setVer(ver);
			bom.setStatus(1);
			bom.setLocked(0);
			
			list.add(bom);
		}
		boolean result_confirm=false;
		boolean result_add=false;
			
			if(list_part_code_upInPartInfo.size()==0 && list_part_code_upchecked.size()==0 
					&&  list_part_code_upLocked.size()==0&&  list_part_code_upOther.size()==0
					&&  list_part_code_downInPartInfo.size()==0
				) {
				//result_confirm=true代表的是確認的結果沒有問題。
				result_confirm=true;
				result_add=this.bomService.add(list,member_id);
			}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list_part_code_upInPartInfo", list_part_code_upInPartInfo);
		map.put("list_part_code_upchecked", list_part_code_upchecked);
		map.put("list_part_code_upLocked", list_part_code_upLocked);
		map.put("list_part_code_upOther", list_part_code_upOther);
		map.put("list_part_code_downInPartInfo", list_part_code_downInPartInfo);
		map.put("result_confirm", result_confirm);
		map.put("result_add", result_add);
		
		JSONObject json = JSONObject.fromObject(map);
		return json;
	}
	
	

	@RequiresUser
	@RequestMapping("listBomById")
	public JSONObject listBomById(HttpServletRequest request) throws Exception {
		Integer id = Integer.parseInt(request.getParameter("id_check"));
		List<Bom> result = new ArrayList<>();
		result = this.bomService.getBomById(id);
	
		
		//以下為json中有null也不會有問題
		
		JSONArray json1 = JSONArray.fromObject(result);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
		JSONArray jsonArray = JSONArray.fromObject(json1, jsonConfig);
		for (int i = 0; i < jsonArray.size(); i++) {
			JsonNullConvert.filterNull(jsonArray.getJSONObject(i));
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", jsonArray);
		JSONObject json = JSONObject.fromObject(map);
		return json;
	}


	@RequiresUser
	@RequestMapping("update")
	public JSONObject update(HttpServletRequest request) throws Exception {
		Integer id = Integer.parseInt(request.getParameter("id"));
		String part_code_up = request.getParameter("part_code_up");
		String member_id = request.getParameter("member_id");
		String part_code_downs = request.getParameter("part_code_downs");
		String dosages = request.getParameter("dosages");
		String bases = request.getParameter("bases");
		String units = request.getParameter("units");
		String tips = request.getParameter("tips");
		
		List<String> part_code_downAry=new ArrayList<>();

		JsonConfig jsonConfig = new JsonConfig();
		JSONArray jsonArray= JSONArray.fromObject(part_code_downs, jsonConfig);
		for (int i = 0; i < jsonArray.size(); i++) {
			part_code_downAry.add((String)jsonArray.get(i));
		}
		
		List<Float> dosageAry=new ArrayList<>();


		JSONArray jsonArray1= JSONArray.fromObject(dosages, jsonConfig);
		for (int i = 0; i < jsonArray1.size(); i++) {
			dosageAry.add(Float.parseFloat((String)jsonArray1.get(i)));
		}
		
		List<Integer> baseAry=new ArrayList<>();

		JSONArray jsonArray2= JSONArray.fromObject(bases, jsonConfig);
		for (int i = 0; i < jsonArray2.size(); i++) {
			baseAry.add(Integer.parseInt((String)jsonArray2.get(i)));
		}
		
		List<String> unitAry=new ArrayList<>();

		JSONArray jsonArray3= JSONArray.fromObject(units, jsonConfig);
		for (int i = 0; i < jsonArray3.size(); i++) {
			unitAry.add((String)jsonArray3.get(i));
		}
		

		List<Integer> list_part_code_downInPartInfo = new ArrayList<Integer>();
		
		
		List<Bom> listAdd = new ArrayList<Bom>();
		List<Bom> listUpdate = new ArrayList<Bom>();
		List<Bom> listDel = new ArrayList<Bom>();
		for (int i = 0; i < part_code_downAry.size(); i++) {
			//確認part_code_down在partinfo中存在
			PartInfo part1=this.partInfoService.getByCode(part_code_downAry.get(i));
			if (part1==null) {
				list_part_code_downInPartInfo.add(i);
			}
		}
		List<Bom> ltbom = this.bomService.getBomById(id);
		
		if(ltbom.size()<=part_code_downAry.size()) {
			//更新
			for (int i = 0; i < ltbom.size(); i++) {
				Bom bom=ltbom.get(i);
				bom.setPart_code_down(part_code_downAry.get(i));
				bom.setDosage(dosageAry.get(i));
				bom.setBase(baseAry.get(i));
				bom.setUnit(unitAry.get(i));
				bom.setStatus(1);
				bom.setLocked(0);
				listUpdate.add(bom);
			}
			//新增
			for (int i = ltbom.size(); i < part_code_downAry.size(); i++) {
				Bom bom=new Bom();
				bom.setPart_code_up(part_code_up);;
				bom.setPart_code_down(part_code_downAry.get(i));
				bom.setDosage(dosageAry.get(i));
				bom.setBase(baseAry.get(i));
				bom.setUnit(unitAry.get(i));
				bom.setVer(ltbom.get(0).getVer());
				bom.setStatus(1);
				bom.setLocked(0);
				listAdd.add(bom);
			}
		}
		else {
			//更新
			for (int i = 0; i < part_code_downAry.size(); i++) {
				Bom bom=ltbom.get(i);
				bom.setPart_code_down(part_code_downAry.get(i));
				bom.setDosage(dosageAry.get(i));
				bom.setBase(baseAry.get(i));
				bom.setUnit(unitAry.get(i));
				bom.setStatus(1);
				bom.setLocked(0);
				listUpdate.add(bom);
			}
			//删除
			for (int i = part_code_downAry.size(); i < ltbom.size(); i++) {
				Bom bom=ltbom.get(i);
				bom.setStatus(1);
				bom.setLocked(0);
				listDel.add(bom);
			}
		}
	
		boolean result_confirm=false;
		boolean result_add=false;
			
			if(list_part_code_downInPartInfo.size()==0) {
				//result_confirm=true代表的是確認的結果沒有問題。
				result_confirm=true;
				
				result_add=this.bomService.updateUpdate(listUpdate,member_id,tips);
				this.bomService.updateAdd(listAdd);
				this.bomService.updateDel(listDel);
			}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list_part_code_downInPartInfo", list_part_code_downInPartInfo);
		map.put("result_confirm", result_confirm);
		map.put("result_add", result_add);
		
		JSONObject json = JSONObject.fromObject(map);
		return json;
	}
	

	@RequiresUser
	@RequestMapping("updateCheck")
	public boolean updateCheck(HttpServletRequest request) throws Exception {
		Integer id = Integer.parseInt(request.getParameter("id"));
		String member_id = request.getParameter("member_id");
		String part_code_up = request.getParameter("part_code_up");
		String tips = request.getParameter("tips");
		Integer status = Integer.parseInt(request.getParameter("status"));
		

		Bom bom = new Bom();
		bom.setId(id);
		bom.setPart_code_up(part_code_up);
		bom.setStatus(status);
		
		if(this.bomService.editStatus(bom)) {
			
			int id_check=bom.getId();
			CheckLog checklog=new CheckLog();
			checklog.setType_check("BOM");
			checklog.setId_check(id_check);
			checklog.setReg_time_apply(new Date());

			checklog.setMember_id(member_id);
			if(status==3) {
				if(tips.equals("")){
					checklog.setTips(part_code_up+"退回申请");
				}else {
					checklog.setTips(tips);
				}
				
			}else if(status==2) {
				if(tips.equals("")){
					checklog.setTips(part_code_up+"取回申请");
				}else {
					checklog.setTips(tips);
				}
			}
			else if(status==5) {
				if(tips.equals("")){
					checklog.setTips(part_code_up+"批准申请");
				}else {
					checklog.setTips(tips);
				}
			}
			else if(status==4) {
				if(tips.equals("")){
					checklog.setTips(part_code_up+"取消申请");
				}else {
					checklog.setTips(tips);
				}
			}
			checklog.setCheck_status(status);
			return this.checkLogService.add(checklog);
		}
		return false;
	}
	
	
	@RequiresUser
	@RequestMapping("listSplit0")
	public JSONObject listSplit0(HttpServletRequest request) throws Exception {

		String part_code_up = request.getParameter("part_code");
		List<Bom> result = new ArrayList<Bom>();
		result = this.bomService.getDownSplit(part_code_up,true);
		
		
		//以下為json中有null也不會有問題
		JSONArray json1 = JSONArray.fromObject(result);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
		JSONArray jsonArray = JSONArray.fromObject(json1, jsonConfig);
		for (int i = 0; i < jsonArray.size(); i++) {
			JsonNullConvert.filterNull(jsonArray.getJSONObject(i));
		}
		Map<String, Object> map1 = new HashMap<String, Object>();
		map1.put("list", jsonArray);
		JSONObject json = JSONObject.fromObject(map1);
		return json;
	}
	
	
	@RequiresUser
	@RequestMapping("listSplit1")
	public JSONObject listSplit1(HttpServletRequest request) throws Exception {

		String part_code_up = request.getParameter("part_code");
		List<Bom> result = new ArrayList<Bom>();
		result = this.bomService.getDownSplit(part_code_up,false);
		
		
		//以下為json中有null也不會有問題
		JSONArray json1 = JSONArray.fromObject(result);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
		JSONArray jsonArray = JSONArray.fromObject(json1, jsonConfig);
		for (int i = 0; i < jsonArray.size(); i++) {
			JsonNullConvert.filterNull(jsonArray.getJSONObject(i));
		}
		Map<String, Object> map1 = new HashMap<String, Object>();
		map1.put("list", jsonArray);
		JSONObject json = JSONObject.fromObject(map1);
		return json;
	}
	
	@RequiresUser
	@RequestMapping("listSplit2")
	public JSONObject listSplit2(HttpServletRequest request) throws Exception {

		String part_code_up = request.getParameter("part_code");
		List<Bom> result = new ArrayList<Bom>();
		result = this.bomService.getAllDownSplit(part_code_up);
		
		
		//以下為json中有null也不會有問題
		JSONArray json1 = JSONArray.fromObject(result);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
		JSONArray jsonArray = JSONArray.fromObject(json1, jsonConfig);
		for (int i = 0; i < jsonArray.size(); i++) {
			JsonNullConvert.filterNull(jsonArray.getJSONObject(i));
		}
		Map<String, Object> map1 = new HashMap<String, Object>();
		map1.put("list", jsonArray);
		JSONObject json = JSONObject.fromObject(map1);
		return json;
	}
	
	
	@RequiresUser
	@RequestMapping("listSplit3")
	public JSONObject listSplit3(HttpServletRequest request) throws Exception {

		String part_code_up = request.getParameter("part_code");
		List<Bom> result = new ArrayList<Bom>();
		result = this.bomService.getDownHistorySplit(part_code_up);
		
		
		//以下為json中有null也不會有問題
		JSONArray json1 = JSONArray.fromObject(result);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
		JSONArray jsonArray = JSONArray.fromObject(json1, jsonConfig);
		for (int i = 0; i < jsonArray.size(); i++) {
			JsonNullConvert.filterNull(jsonArray.getJSONObject(i));
		}
		Map<String, Object> map1 = new HashMap<String, Object>();
		map1.put("list", jsonArray);
		JSONObject json = JSONObject.fromObject(map1);
		return json;
	}
	
	@RequiresUser
	@RequestMapping("listSplit4")
	public JSONObject listSplit4(HttpServletRequest request) throws Exception {

		String part_code_down = request.getParameter("part_code");
		List<Bom> result = new ArrayList<Bom>();
		result = this.bomService.getUpSplit(part_code_down);
		
		
		//以下為json中有null也不會有問題
		JSONArray json1 = JSONArray.fromObject(result);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
		JSONArray jsonArray = JSONArray.fromObject(json1, jsonConfig);
		for (int i = 0; i < jsonArray.size(); i++) {
			JsonNullConvert.filterNull(jsonArray.getJSONObject(i));
		}
		Map<String, Object> map1 = new HashMap<String, Object>();
		map1.put("list", jsonArray);
		JSONObject json = JSONObject.fromObject(map1);
		return json;
	}
	
	@RequiresUser
	@RequestMapping("listSplit5")
	public JSONObject listSplit5(HttpServletRequest request) throws Exception {

		String part_code_down = request.getParameter("part_code");
		List<Bom> result = new ArrayList<Bom>();
		result = this.bomService.getUpTailSplit(part_code_down);
		
		
		//以下為json中有null也不會有問題
		JSONArray json1 = JSONArray.fromObject(result);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
		JSONArray jsonArray = JSONArray.fromObject(json1, jsonConfig);
		for (int i = 0; i < jsonArray.size(); i++) {
			JsonNullConvert.filterNull(jsonArray.getJSONObject(i));
		}
		Map<String, Object> map1 = new HashMap<String, Object>();
		map1.put("list", jsonArray);
		JSONObject json = JSONObject.fromObject(map1);
		return json;
	}
	
	
	
	
	
	
	
	
	
	@RequiresUser
	@RequestMapping("lockOrUnlock")
	public boolean lockOrUnlock(HttpServletRequest request) throws Exception {
		Integer id = Integer.parseInt(request.getParameter("id"));
		String part_code_up = request.getParameter("part_code_up");
		String member_id = request.getParameter("member_id");
		Integer locked = Integer.parseInt(request.getParameter("locked"));
		
		Bom bom = new Bom();
		bom.setId(id);
		bom.setPart_code_up(part_code_up);
		bom.setLocked(locked);
		
		if(this.bomService.editLock(bom)) {
			
			int id_check=bom.getId();
			CheckLog checklog=new CheckLog();
			checklog.setType_check("BOM");
			checklog.setId_check(id_check);
			checklog.setReg_time_apply(new Date());

			checklog.setMember_id(member_id);
			
			if(locked==1) {
				checklog.setTips(part_code_up+"-BOM废止");
				checklog.setCheck_status(6);
				
			}else if(locked==0) {
				checklog.setTips(part_code_up+"-BOM解除废止");
				checklog.setCheck_status(5);
			}
			return this.checkLogService.add(checklog);
		}
		return false;
	}
	
	
	
	
	
	
	
	
	
	
	
	/*	
	
	*//**
	 * 需要傳回查找到的首頁結果，並且傳回總數據的行數
	 * 
	 * @return
	 * @throws Exception
	 *//*
	@RequiresUser
	@RequestMapping("listUnlockedSplitByConditions")
	public JSONObject listUnlockedSplitByConditions(HttpServletRequest request) throws Exception {
		Integer currentPage = Integer.parseInt(request.getParameter("currentPage"));
		Integer lineSize = Integer.parseInt(request.getParameter("lineSize"));
		String part_code = request.getParameter("part_code");
		String tradename = request.getParameter("tradename");
		String spec = request.getParameter("spec");
		String prop = request.getParameter("prop");
		
		
		
		Map<String, Object> map = new HashMap<String, Object>();
		Long count = (long) 0;
		count = this.partInfoService.getAllUnlockedCountByConditions(part_code,tradename,spec,prop);
		List<PartInfo> result = new ArrayList<PartInfo>();
		result = this.partInfoService.getAllUnlockedSplitByConditions(part_code,tradename,spec,prop,currentPage, lineSize);
		map.put("count", count);
		map.put("list", result);
		JSONObject json = JSONObject.fromObject(map);
		return json;
	}

	*//**
	 * 需要傳回查找到的首頁結果，並且傳回總數據的行數
	 * 
	 * @return
	 * @throws Exception
	 *//*
	@RequiresUser
	@RequestMapping("listSplitByConditions")
	public JSONObject listSplitByConditions(HttpServletRequest request) throws Exception {
		Integer currentPage = Integer.parseInt(request.getParameter("currentPage"));
		Integer lineSize = Integer.parseInt(request.getParameter("lineSize"));
		String part_code = request.getParameter("part_code");
		String tradename = request.getParameter("tradename");
		String spec = request.getParameter("spec");
		String prop = request.getParameter("prop");
		
		Map<String, Object> map = new HashMap<String, Object>();
		Long count = (long) 0;
		count = this.partInfoService.getAllCountByConditions(part_code,tradename,spec,prop);
		List<PartInfo> result = new ArrayList<PartInfo>();
		result = this.partInfoService.getAllSplitByConditions(part_code,tradename,spec,prop,currentPage, lineSize);
		map.put("count", count);
		map.put("list", result);
		JSONObject json = JSONObject.fromObject(map);
		return json;
	}
	
	
	@RequiresUser
	@RequestMapping("lockOrUnlock")
	public boolean lockOrUnlock(HttpServletRequest request) throws Exception {
		Integer id = Integer.parseInt(request.getParameter("id"));
		String part_code = request.getParameter("part_code");
		String member_id = request.getParameter("member_id");
		Integer locked = Integer.parseInt(request.getParameter("locked"));
		
		PartInfo partinfo = new PartInfo();
		partinfo.setId(id);
		partinfo.setPart_code(part_code);
		partinfo.setLocked(locked);
		
		if(this.partInfoService.editLock(partinfo)) {
			
			int id_check=partinfo.getId();
			CheckLog checklog=new CheckLog();
			checklog.setType_check("料号");
			checklog.setId_check(id_check);
			checklog.setReg_time_apply(new Date());

			checklog.setMember_id(member_id);
			
			if(locked==1) {
				checklog.setTips(part_code+"废止料号");
				checklog.setCheck_status(6);
				
			}else if(locked==0) {
				checklog.setTips(part_code+"解除废止");
				checklog.setCheck_status(5);
			}
			return this.checkLogService.add(checklog);
		}
		return false;
	}
	
	
	
	*/
	
	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return null;
	}
}
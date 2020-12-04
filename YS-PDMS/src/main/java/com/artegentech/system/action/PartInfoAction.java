package com.artegentech.system.action;

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
import com.artegentech.system.service.ICheckLogService;
import com.artegentech.system.service.IPartInfoService;
import com.artegentech.system.vo.CheckLog;
import com.artegentech.system.vo.Member;
import com.artegentech.system.vo.PartInfo;
import com.artegentech.system.vo.Type;
import com.artegentech.util.JsonDateValueProcessor;
import com.artegentech.util.JsonNullConvert;
import com.artegentech.util.action.AbstractAction;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

@Controller
@ResponseBody
@RequestMapping("partinfo/*")
public class PartInfoAction extends AbstractAction {
	@Resource
	private IPartInfoService partInfoService;
	
	@Resource
	private ICheckLogService  checkLogService;
	
	@Resource
	private ITypeDAO typeDao;
	
	@RequestMapping("checkPartCode")
	@RequiresUser
	public void checkPartCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String part_code = request.getParameter("part_code");
		if (part_code == null || "".equals(part_code)) {
			super.print(response, false);
		} else {
			super.print(response, this.partInfoService.getByCode(part_code) == null);
		}
	}

	
	@RequiresUser
	@RequestMapping("add")
	public boolean add(HttpServletRequest request) throws Exception {
		String part_code = request.getParameter("part_code");
		String tradename = request.getParameter("tradename");
		String spec = request.getParameter("spec");
		String unit = request.getParameter("unit");
		Float loss=Float.parseFloat((String)request.getParameter("loss"));
		String member_id = request.getParameter("member_id");
		String props = request.getParameter("props");
		
		
		List<String> propAry=new ArrayList<>();

		JsonConfig jsonConfig = new JsonConfig();
		JSONArray jsonArray= JSONArray.fromObject(props, jsonConfig);
		for (int i = 0; i < jsonArray.size(); i++) {
			propAry.add((String)jsonArray.get(i));
		}
		int add=5-jsonArray.size();
		
		while(add>0) {
			propAry.add("");
			--add;
		}
		PartInfo partinfo = new PartInfo();
		partinfo.setPart_code(part_code);
		partinfo.setTradename(tradename);
		partinfo.setSpec(spec);
		partinfo.setUnit(unit);
		partinfo.setLoss(loss);
		partinfo.setLocked(0);
		partinfo.setStatus(1);
		partinfo.setProp1(propAry.get(0));
		partinfo.setProp2(propAry.get(1));
		partinfo.setProp3(propAry.get(2));
		partinfo.setProp4(propAry.get(3));
		partinfo.setProp5(propAry.get(4));
		
		if(this.partInfoService.add(partinfo)) {
			
			int id_check=partinfo.getId();
			CheckLog checklog=new CheckLog();
			checklog.setType_check("料号");
			checklog.setId_check(id_check);
			checklog.setReg_time_apply(new Date());

			checklog.setMember_id(member_id);
			checklog.setTips(part_code+"料号建立申请");
			checklog.setCheck_status(1);
			
			return this.checkLogService.add(checklog);
		}
		return false;
	}
	
	@RequiresUser
	@RequestMapping("listPartInfoById")
	public JSONObject listPartInfoById(HttpServletRequest request) throws Exception {
		Integer id = Integer.parseInt(request.getParameter("id_check"));
		
		Map<String, Object> map = new HashMap<String, Object>();

		PartInfo result = new PartInfo();
		result = this.partInfoService.getPartInfoById(id);
		map.put("list", result);
		JSONObject json = JSONObject.fromObject(map);
		return json;
	}
	@RequiresUser
	@RequestMapping("listPartInfoByCode")
	public JSONObject listPartInfoByCode(HttpServletRequest request) throws Exception {
		String part_code = request.getParameter("part_code");
		
		Map<String, Object> map = new HashMap<String, Object>();

		PartInfo result = new PartInfo();
		result = this.partInfoService.getByCode(part_code);
		map.put("list", result);
		JSONObject json = JSONObject.fromObject(map);
		return json;
	}

	@RequiresUser
	@RequestMapping("update")
	public boolean update(HttpServletRequest request) throws Exception {
		Integer id = Integer.parseInt(request.getParameter("id"));
		String part_code = request.getParameter("part_code");
		String tradename = request.getParameter("tradename");
		String spec = request.getParameter("spec");
		String unit = request.getParameter("unit");
		Float loss=Float.parseFloat((String)request.getParameter("loss"));
		String member_id = request.getParameter("member_id");
		String tips = request.getParameter("tips");
		String props = request.getParameter("props");
		
		
		List<String> propAry=new ArrayList<>();

		JsonConfig jsonConfig = new JsonConfig();
		JSONArray jsonArray= JSONArray.fromObject(props, jsonConfig);
		for (int i = 0; i < jsonArray.size(); i++) {
			propAry.add((String)jsonArray.get(i));
		}
		int add=5-jsonArray.size();
		
		while(add>0) {
			propAry.add("");
			--add;
		}
		PartInfo partinfo = new PartInfo();
		partinfo.setId(id);
		partinfo.setPart_code(part_code);
		partinfo.setTradename(tradename);
		partinfo.setSpec(spec);
		partinfo.setUnit(unit);
		partinfo.setLoss(loss);
		partinfo.setLocked(0);
		partinfo.setStatus(1);
		partinfo.setProp1(propAry.get(0));
		partinfo.setProp2(propAry.get(1));
		partinfo.setProp3(propAry.get(2));
		partinfo.setProp4(propAry.get(3));
		partinfo.setProp5(propAry.get(4));
		
		if(this.partInfoService.edit(partinfo)) {
			
			int id_check=partinfo.getId();
			CheckLog checklog=new CheckLog();
			checklog.setType_check("料号");
			checklog.setId_check(id_check);
			checklog.setReg_time_apply(new Date());

			checklog.setMember_id(member_id);
			if(tips.equals("")){
				checklog.setTips(part_code+"重新发起审核");
			}else {
				checklog.setTips(tips);
			}
			checklog.setCheck_status(1);
			
			return this.checkLogService.add(checklog);
		}
		return false;
	}
	
	
	
	@RequiresUser
	@RequestMapping("updateCheck")
	public boolean updateCheck(HttpServletRequest request) throws Exception {
		Integer id = Integer.parseInt(request.getParameter("id"));
		String member_id = request.getParameter("member_id");
		String part_code = request.getParameter("part_code");
		String tips = request.getParameter("tips");
		Integer status = Integer.parseInt(request.getParameter("status"));
		

		PartInfo partinfo = new PartInfo();
		partinfo.setId(id);
		partinfo.setPart_code(part_code);
		partinfo.setStatus(status);
		
		if(this.partInfoService.editStatus(partinfo)) {
			
			int id_check=partinfo.getId();
			CheckLog checklog=new CheckLog();
			checklog.setType_check("料号");
			checklog.setId_check(id_check);
			checklog.setReg_time_apply(new Date());

			checklog.setMember_id(member_id);
			if(status==3) {
				if(tips.equals("")){
					checklog.setTips(part_code+"退回申请");
				}else {
					checklog.setTips(tips);
				}
			}
			else if(status==2) {
				if(tips.equals("")){
					checklog.setTips(part_code+"取回申请");
				}else {
					checklog.setTips(tips);
				}
			}
			else if(status==5) {
				if(tips.equals("")){
					checklog.setTips(part_code+"批准申请");
				}else {
					checklog.setTips(tips);
				}
			}
			else if(status==4) {
				if(tips.equals("")){
					checklog.setTips(part_code+"取消申请");
				}else {
					checklog.setTips(tips);
				}
			}
			
			checklog.setCheck_status(status);
			return this.checkLogService.add(checklog);
		}
		return false;
	}
	
	
	/**
	 * 需要傳回查找到的首頁結果，並且傳回總數據的行數
	 * 
	 * @return
	 * @throws Exception
	 */
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

	/**
	 * 需要傳回查找到的首頁結果，並且傳回總數據的行數
	 * 
	 * @return
	 * @throws Exception
	 */
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
	@RequestMapping("listUnlocked")
	public List<PartInfo> listUnlocked(HttpServletRequest request) throws Exception {
		List<PartInfo> result = new ArrayList<PartInfo>();
		result = this.partInfoService.getAllUnlocked();

		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
		JSONArray jsonArray = JSONArray.fromObject(result, jsonConfig);
		return jsonArray;
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
				checklog.setTips(part_code+"-废止料号");
				checklog.setCheck_status(6);
				
			}else if(locked==0) {
				checklog.setTips(part_code+"-解除废止");
				checklog.setCheck_status(5);
			}
			return this.checkLogService.add(checklog);
		}
		return false;
	}
	
	
	
	@RequiresUser
	@RequestMapping("addStocks")
	public JSONObject addStocks(HttpServletRequest request) throws Exception {
		
		String receivedString = request.getParameter("data");
		String member_id = request.getParameter("member_id");

		JsonConfig jsonConfig = new JsonConfig();
		JSONArray jsonArray = JSONArray.fromObject(receivedString, jsonConfig);

		Map<String, Object> map = new HashMap<String, Object>();
		List<Integer> errorRows = new ArrayList<Integer>();
		
		int count = 0;
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONArray jsonArray2 = JSONArray.fromObject(jsonArray.get(i), jsonConfig);
			PartInfo partInfo = new PartInfo();
			String part_code = (String) jsonArray2.get(0);
			String tradename = (String) jsonArray2.get(1);
			String spec = (String) jsonArray2.get(2);
			String unit = (String) jsonArray2.get(3);
			Float loss=Float.parseFloat((String) jsonArray2.get(4));
			
			String prop1 = (String) jsonArray2.get(5);
			String prop2 = (String) jsonArray2.get(6);
			String prop3 = (String) jsonArray2.get(7);
			String prop4 = (String) jsonArray2.get(8);
			String prop5 = (String) jsonArray2.get(9);
			
			partInfo.setPart_code(part_code);
			partInfo.setTradename(tradename);
			partInfo.setSpec(spec);
			partInfo.setUnit(unit);
			partInfo.setLoss(loss);
			partInfo.setProp1(prop1);
			partInfo.setProp2(prop2);
			partInfo.setProp3(prop3);
			partInfo.setProp4(prop4);
			partInfo.setProp5(prop5);
			partInfo.setStatus(5);
			partInfo.setLocked(0);

			boolean bOK=true;
			//判断单位是否存在
			Map<String, Object> map1 = new HashMap<String, Object>();
			map1.put("parent_type","单位");
			map1.put("sub_type", unit);
			map1.put("upper_id", 0);
			if (this.typeDao.findByParentAndSubAndUpper(map1) == null) {
				bOK=false;
			}
			//判断属性是否存在
			String prop="属性1-"+prop1;
			if(!prop2.equals("")) {
				prop+="-"+prop2;
			}
			if(!prop3.equals("")) {
				prop+="-"+prop3;
			}
			if(!prop4.equals("")) {
				prop+="-"+prop4;
			}
			if(!prop5.equals("")) {
				prop+="-"+prop5;
			}
			Type type=this.typeDao.findJointType(prop);
			if (type== null) {
				bOK=false;
			}
			//创建料号/判断料号
			if(bOK&&!this.partInfoService.add(partInfo)) {
				bOK=false;
			}
			
			if(!bOK) {
				errorRows.add(i);
				System.out.println("error i = " + i);
			}else {
				count++;
				int id_check=partInfo.getId();
				CheckLog checklog=new CheckLog();
				checklog.setType_check("料号");
				checklog.setId_check(id_check);
				checklog.setReg_time_apply(new Date());

				checklog.setMember_id(member_id);
				checklog.setTips(part_code+"-料号批量建立");
				checklog.setCheck_status(5);
				
				 this.checkLogService.add(checklog);
			}
		}
		
		if (count == jsonArray.size()) {
			map.put("flag", true);
			map.put("errorRows", "");
		} else {
			map.put("flag", false);
			map.put("errorRows", errorRows);
		}
			
		
		
		JSONObject json = JSONObject.fromObject(map);
		return json;
	}
	
	
	
	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return null;
	}
}
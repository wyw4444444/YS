package com.artegentech.system.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.artegentech.system.dao.IBomDAO;
import com.artegentech.system.dao.ICheckLogDAO;
import com.artegentech.system.dao.IDocDAO;
import com.artegentech.system.dao.IMemberDAO;
import com.artegentech.system.dao.IPartInfoDAO;
import com.artegentech.system.service.ICheckLogService;
import com.artegentech.system.service.IPartInfoService;
import com.artegentech.system.service.abs.AbstractService;
import com.artegentech.system.vo.Bom;
import com.artegentech.system.vo.CheckLog;
import com.artegentech.system.vo.Doc;
import com.artegentech.system.vo.Member;
import com.artegentech.system.vo.PartInfo;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;

@Service
public class CheckLogServiceImpl extends AbstractService implements ICheckLogService {

	@Resource
	private ICheckLogDAO checklogDao;
	@Resource
	private IPartInfoDAO partInfoDao;

	@Resource
	private IBomDAO bomDao;
	@Resource
	private IDocDAO docDao;
	
	@Resource
	private IMemberDAO memberDAO;
	
	@Override
	public boolean add(CheckLog checklog) throws Exception {
		return this.checklogDao.doCreate(checklog);
	}
	
	//抓取待审核项目
	@Override
	public List<CheckLog> getAwaitCheckSplit(String member_id) throws Exception {
		List<CheckLog> result = new ArrayList<CheckLog>();
		Map<String, Object> map = new HashMap<String, Object>();
		
		
		//料号
		Iterator<PartInfo> iter=this.partInfoDao.findAwaitCheckSplit().iterator();
		while(iter.hasNext()) {
			PartInfo partInfo=iter.next();
			map.put("id_check",partInfo.getId());
			map.put("type_check","料号");
			CheckLog checklog=this.checklogDao.findById_CheckAndType(map);
			if (checklog!=null) {
				Member member=this.memberDAO.findById(checklog.getMember_id());
				checklog.setPartInfo(partInfo);
				checklog.setMember(member);
				
				result.add(checklog);
			}
		}
		//bom
		Iterator<Bom> iter1=this.bomDao.findAwaitCheckSplit().iterator();

		while(iter1.hasNext()) {
			Bom bom=iter1.next();
			map.put("id_check",bom.getId());
			map.put("type_check","BOM");
			CheckLog checklog=this.checklogDao.findById_CheckAndType(map);
			
			if (checklog!=null) {
				Member member=this.memberDAO.findById(checklog.getMember_id());
				
				checklog.setBom(bom);
				checklog.setMember(member);
				
				result.add(checklog);
			}
		}
		//doc
		Iterator<Doc> iterDoc=this.docDao.getDocByStatus(1).iterator();

		while(iterDoc.hasNext()) {
			Doc doc=iterDoc.next();
			map.put("id_check",doc.getId());
			map.put("type_check","圖檔");
			CheckLog checklog=this.checklogDao.findById_CheckAndType(map);
			
			if (checklog!=null) {
				Member member=this.memberDAO.findById(checklog.getMember_id());
				
				checklog.setDoc(doc);
				checklog.setMember(member);
				
				result.add(checklog);
			}
		}
		
		
		return result;
	}
	
	//抓取需重办项目
	@Override
	public List<CheckLog> getAwaitRehandleSplit(String member_id) throws Exception {
		List<CheckLog> result = new ArrayList<CheckLog>();
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("member_id",member_id);
		
		//料号
		Iterator<PartInfo> iter=this.partInfoDao.findAwaitRehandleSplit().iterator();
		while(iter.hasNext()) {
			PartInfo partInfo=iter.next();
			map.put("id_check",partInfo.getId());
			map.put("type_check","料号");
			CheckLog checklog=this.checklogDao.findById_CheckAndType(map);
			
			if (checklog!=null) {
				Member member=this.memberDAO.findById(checklog.getMember_id());
				
				checklog.setPartInfo(partInfo);
				checklog.setMember(member);
				
				result.add(checklog);
			}
		}
		
		//bom
		
		Iterator<Bom> iter1=this.bomDao.findAwaitRehandleSplit().iterator();

		while(iter1.hasNext()) {
			Bom bom=iter1.next();
			map.put("id_check",bom.getId());
			map.put("type_check","BOM");
			CheckLog checklog=this.checklogDao.findById_CheckAndType(map);
			
			if (checklog!=null) {
				Member member=this.memberDAO.findById(checklog.getMember_id());
				
				checklog.setBom(bom);
				checklog.setMember(member);
				
				result.add(checklog);
			}
		}
		//doc
		Iterator<Doc> iterDoc=this.docDao.getDocByStatus(3).iterator();

		while(iterDoc.hasNext()) {
			Doc doc=iterDoc.next();
			map.put("id_check",doc.getId());
			map.put("type_check","圖檔");
			CheckLog checklog=this.checklogDao.findById_CheckAndType(map);
			
			if (checklog!=null) {
				Member member=this.memberDAO.findById(checklog.getMember_id());
				
				checklog.setDoc(doc);
				checklog.setMember(member);
				
				result.add(checklog);
			}
		}
		
		return result;
	}
	
	
	//抓取已发起项目(可取回)
		@Override
		public List<CheckLog> getAwaitHandledSplit(String member_id) throws Exception {
			List<CheckLog> result = new ArrayList<CheckLog>();
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("member_id",member_id);
			
			//料号
			Iterator<PartInfo> iter=this.partInfoDao.findAwaitCheckSplit().iterator();
			while(iter.hasNext()) {
				PartInfo partInfo=iter.next();
				map.put("id_check",partInfo.getId());
				map.put("type_check","料号");
				CheckLog checklog=this.checklogDao.findById_CheckAndType(map);
				
				if (checklog!=null) {
					Member member=this.memberDAO.findById(checklog.getMember_id());
					
					checklog.setPartInfo(partInfo);
					checklog.setMember(member);
					
					result.add(checklog);
				}
			}
			
			//bom
			
			Iterator<Bom> iter1=this.bomDao.findAwaitCheckSplit().iterator();

			while(iter1.hasNext()) {
				Bom bom=iter1.next();
				map.put("id_check",bom.getId());
				map.put("type_check","BOM");
				CheckLog checklog=this.checklogDao.findById_CheckAndType(map);
				
				if (checklog!=null) {
					Member member=this.memberDAO.findById(checklog.getMember_id());
					
					checklog.setBom(bom);
					checklog.setMember(member);
					
					result.add(checklog);
				}
			}
			//doc
			Iterator<Doc> iterDoc=this.docDao.getDocByStatus(1).iterator();

			while(iterDoc.hasNext()) {
				Doc doc=iterDoc.next();
				map.put("id_check",doc.getId());
				map.put("type_check","圖檔");
				CheckLog checklog=this.checklogDao.findById_CheckAndType(map);
				
				if (checklog!=null) {
					Member member=this.memberDAO.findById(checklog.getMember_id());
					
					checklog.setDoc(doc);
					checklog.setMember(member);
					
					result.add(checklog);
				}
			}
			
			return result;
		}
		

	@Override
	public boolean updateBatchAwait(String id_check_selects, String type_check_selects,String member_id,Integer check_status) throws Exception {
		
		try {
			List<Integer> id_check_selectAry=new ArrayList<>();

			JsonConfig jsonConfig = new JsonConfig();
			JSONArray jsonArray= JSONArray.fromObject(id_check_selects, jsonConfig);
			for (int i = 0; i < jsonArray.size(); i++) {
				id_check_selectAry.add((Integer) jsonArray.get(i));
			}
	
			List<String> type_check_selectAry=new ArrayList<>();
			JSONArray jsonArray1= JSONArray.fromObject(type_check_selects, jsonConfig);
			for (int i = 0; i < jsonArray1.size(); i++) {
				type_check_selectAry.add((String) jsonArray1.get(i));
			}
			
			Iterator<Integer> iter=id_check_selectAry.iterator();
			int i=0;
			while(iter.hasNext()) {
				
				Integer id_check=iter.next();
				String type_check=type_check_selectAry.get(i);
				//更新partinfo status
				if(type_check.equals("料号")) {
			
					PartInfo partinfo=new PartInfo();
					partinfo.setId(id_check);
					partinfo.setStatus(check_status);
					this.partInfoDao.doUpdateStatus(partinfo);
				}
				//更新 BOM status
				if(type_check.equals("BOM")) {
					
					Bom bom=this.bomDao.findById(id_check);
					if(bom!=null) {
						bom.setStatus(check_status);
						this.bomDao.doUpdateStatus(bom);
					}
				}
				//更新圖檔 status
				if(type_check.equals("圖檔")) {
					
					Doc doc=this.docDao.findById(id_check);
					if(doc!=null) {
						doc.setStatus(check_status);
						this.docDao.doUpdateStatus(doc);
					}
				}
				//更新checklog
				String tips="";
				if(check_status==3) {
					tips="退回申请";
				}else if(check_status==5) {
					tips="批准申请";
				}
				
				CheckLog checkLog=new CheckLog();
				checkLog.setType_check(type_check);
				checkLog.setId_check(id_check);
				checkLog.setReg_time_apply(new Date());
				checkLog.setMember_id(member_id);
				checkLog.setTips(tips);
				checkLog.setCheck_status(check_status);
				this.checklogDao.doCreate(checkLog);
				i++;
			}
			
			return true;
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	
	
	@Override
	public List<CheckLog> getById_checkAndType(String type_check,Integer id_check) throws Exception {
		List<CheckLog> result = new ArrayList<CheckLog>();
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id_check",id_check);
		map.put("type_check",type_check);
		Iterator<CheckLog> iter=this.checklogDao.findById_CheckAndTypeAll(map).iterator();
		while(iter.hasNext()) {
			CheckLog checklog=iter.next();
			Member member=this.memberDAO.findById(checklog.getMember_id());
			checklog.setMember(member);
			result.add(checklog);
		}
		return result;
	}
	
	@Override
	public Long getCountByTypeAndPartCode(String type_check, String part_code) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		if ("".equals(part_code) || part_code == null || "null".equalsIgnoreCase(part_code)) {
			map.put("part_code", null);
		} else {
			map.put("part_code", "%" + part_code + "%");
		}
		map.put("type_check", type_check);
		if(type_check.equals("料号")) {
			return this.checklogDao.getCountByTypeAndPartCodeInPartInfo(map);
		}
		else if(type_check.equals("BOM")) {
			return this.checklogDao.getCountByTypeAndPartCodeInBom(map);
		}
		
		return null;
	}

	@Override
	public List<CheckLog> getSplitByTypeAndPartCode(String type_check, String part_code, Integer currentPage, Integer lineSize) throws Exception{
		List<CheckLog> result = new ArrayList<CheckLog>();
		
		Map<String, Object> map = super.handleParams1(currentPage, lineSize);
		if ("".equals(part_code) || part_code == null || "null".equalsIgnoreCase(part_code)) {
			map.put("part_code", null);
		} else {
			map.put("part_code", "%" + part_code + "%");
		}
		map.put("type_check", type_check);
		if(type_check.equals("料号")) {
			Iterator<CheckLog> iter=this.checklogDao.findByPartCodeInPartInfo(map).iterator();
			while(iter.hasNext()) {
				CheckLog checklog=iter.next();
				Member member=this.memberDAO.findById(checklog.getMember_id());
				checklog.setMember(member);
				PartInfo partInfo=this.partInfoDao.findById(checklog.getId_check());
				checklog.setPartInfo(partInfo);	
				result.add(checklog);
			}
		}
		else if(type_check.equals("BOM")) {
		
			Iterator<CheckLog> iter=this.checklogDao.findByPartCodeInBom(map).iterator();
			while(iter.hasNext()) {
				CheckLog checklog=iter.next();
				Member member=this.memberDAO.findById(checklog.getMember_id());
				checklog.setMember(member);
			
				Bom bom=this.bomDao.findById(checklog.getId_check());
				checklog.setBom(bom);
				result.add(checklog);
			}
		}
		return result;
		
	}
/*
	@Override
	public List<PartInfo> getAll() throws Exception {
		return this.partinfodao.findAll();
	}

	@Override
	public PartInfo getByCode(String part_code) throws Exception {
		return this.partinfodao.findByCode(part_code);
	}


	@Override
	public List<PartInfo> getAllUnlocked() throws Exception {
		return this.partinfodao.findAllUnlocked();
	}

	@Override
	public boolean edit(PartInfo PartInfo) throws Exception {
		if (this.partinfodao.findById(PartInfo.getPartInfo_id()) == null) {
			return false;
		} else {
			return this.partinfodao.doUpdate(PartInfo);
		}
	}

	@Override
	public PartInfo getByPartInfoCode(String PartInfo_code) throws Exception {
		return this.partinfodao.findByCode(PartInfo_code);
	}

	@Override
	public PartInfo getByPartInfoName(String PartInfo_name) throws Exception {
		return this.partinfodao.findByName(PartInfo_name);
	}

	@Override
	public Long getAllCount() throws Exception {
		return this.partinfodao.getAllCount();
	}

	@Override
	public Long getAllCount(String column, String keyword) throws Exception {
		Map<String, Object> map = super.handleParams(column, keyword);
		return this.partinfodao.getAllCount(map);
	}

	@Override
	public Long getAllUnlockedCount() throws Exception {
		return this.partinfodao.getAllUnlockedCount();
	}

	@Override
	public List<PartInfo> getAllSplit(String column, String keyword, Integer currentPage, Integer lineSize)
			throws Exception {
		Map<String, Object> map = super.handleParams(column, keyword, currentPage, lineSize);
		return this.partinfodao.findAllSplit(map);
	}

	@Override
	public List<PartInfo> getAllUnlockedSplit(String column, String keyword, Integer currentPage, Integer lineSize)
			throws Exception {
		Map<String, Object> map = super.handleParams(column, keyword, currentPage, lineSize);
		return this.partinfodao.findAllUnlockedSplit(map);
	}

	@Override
	public List<PartInfo> getAllUnlockedByMid(String member_id) throws Exception {
		return this.partinfodao.findAllPartInfoFlagByMid(member_id);
	}*/

}

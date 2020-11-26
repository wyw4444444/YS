package com.artegentech.system.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.artegentech.system.dao.ICheckLogDAO;
import com.artegentech.system.dao.IDocDAO;
import com.artegentech.system.service.IDocService;
import com.artegentech.system.service.abs.AbstractService;
import com.artegentech.system.vo.CheckLog;
import com.artegentech.system.vo.Doc;
import com.artegentech.system.vo.Part;

@Service
public class DocServiceImpl extends AbstractService implements IDocService {

	@Resource
	private IDocDAO docdao;
	@Resource
	private ICheckLogDAO checklogDao;

	@Override
	public boolean add(Doc doc) throws Exception {
		System.out.println(doc.getPart_code());
//		if (this.partdao.checkPartCode(part.getPart_code()) == null) {
//			return false;
//		}
		return this.docdao.add(doc);
	}

	@Override
	public boolean updateDoc(Doc Doc) throws Exception {
		// TODO Auto-generated method stub
		return this.docdao.updateDoc(Doc);
	}

	@Override
	public String getMaxVersion(String code) throws Exception {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("part_code", code);
		List<Doc> result = this.docdao.findNewByPartCode(map);
		if(result.size()==0) {
			return "0";
		}else {
			return result.get(0).getVersion();
		}
	}
	@Override
	public Long getAllCount(String column, String keyword) throws Exception {
		Map<String, Object> map = super.handleParams(column, keyword);
		return this.docdao.getAllCount(map);
	}
	@Override
	public List<Doc> getAllSplit(String column, String keyword, Integer currentPage, Integer lineSize)
			throws Exception {
		Map<String, Object> map = super.handleParams(column, keyword, currentPage, lineSize);
		List<Doc> doc = this.docdao.findAllSplit(map);
		return doc;
	}

	@Override
	public Doc findOnedoc(String part_code, Integer version) {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("part_code", part_code);
		map.put("version", version);
		return this.docdao.findOnedoc(map);
	}

	@Override
	public List<Doc> findByPartCode(String part_code,Integer currentPage, Integer lineSize) {
		// TODO Auto-generated method stub
		if ("".equals(part_code) || part_code == null || "null".equalsIgnoreCase(part_code)) {
		}else {
			part_code = "%"+part_code+"%";
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("part_code", part_code);
		map.put("start", (currentPage - 1) * lineSize);
		map.put("lineSize", lineSize);		
		return this.docdao.findByPartCode(map);
	}
	@Override
	public List<Doc> getDocByStatus(Integer status) throws Exception {
		// TODO Auto-generated method stub
		return this.docdao.getDocByStatus(status);
	}

	@Override
	public CheckLog getCheckLogByDoc(Integer id) throws Exception {
		// TODO Auto-generated method stub
		return this.docdao.getCheckLogByDoc(id);
	}
	@Override
	public List<Doc> findNewByPartCode(String part_code) {
		// TODO Auto-generated method stub
		if ("".equals(part_code) || part_code == null || "null".equalsIgnoreCase(part_code)) {
		}else {
			part_code = "%"+part_code+"%";
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("part_code", part_code);	
		return this.docdao.findNewByPartCode(map);
	}

	@Override
	public List<Doc> findAllNewDoc() {
		// TODO Auto-generated method stub
		return this.docdao.findAllNewDoc();
	}
	
	
	@Override
	public Part checkPartCode(Part part) throws Exception {
		System.out.println(part.getPart_code());
//		if (this.partdao.checkPartCode(part.getPart_code()) == null) {
//			return false;
//		}
		return this.docdao.checkPartCode(part.getPart_code());
	}
	@Override
	public Part getPartinfo(Part part) throws Exception {
		System.out.println(part.getPart_code());
//		if (this.partdao.checkPartCode(part.getPart_code()) == null) {
//			return false;
//		}
		return part;
	}

	@Override
	public boolean updateStatus(Doc doc) throws Exception {
		// TODO Auto-generated method stub
		Integer status = doc.getStatus();
		Integer id = doc.getId();
		String member_id = doc.getMember_id();
		String tips="";
		if(status==3) {
			tips="退回申请";
		}else if(status==5) {
			tips="批准申请";
		}
		
		CheckLog checkLog=new CheckLog();
		checkLog.setType_check("圖檔");
		checkLog.setId_check(id);
		checkLog.setReg_time_apply(new Date());
		checkLog.setMember_id(member_id);
		checkLog.setTips(tips);
		checkLog.setCheck_status(status);
		this.checklogDao.doCreate(checkLog);
		return this.docdao.doUpdateStatus(doc);
	}

	@Override
	public Doc findById(Integer id) throws Exception {
		// TODO Auto-generated method stub
		return this.docdao.findById(id);
	}

	
}

package com.artegentech.system.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.artegentech.system.dao.IActionDAO;
import com.artegentech.system.dao.IDeptDAO;
import com.artegentech.system.dao.IMemberDAO;
import com.artegentech.system.dao.IRecordDAO;
import com.artegentech.system.dao.IRoleDAO;
import com.artegentech.system.service.IMemberService;
import com.artegentech.system.service.IRecordService;
import com.artegentech.system.service.abs.AbstractService;
import com.artegentech.system.vo.Customer;
import com.artegentech.system.vo.Dept;
import com.artegentech.system.vo.Member;
import com.artegentech.system.vo.PDR;
import com.artegentech.system.vo.PDRCost;
import com.artegentech.system.vo.PDRDetail;
import com.artegentech.system.vo.PDRLog;
import com.artegentech.system.vo.Record;
import com.artegentech.system.vo.Role;

@Service
public class RecordServiceImpl extends AbstractService implements IRecordService {

	@Resource
	private IRecordDAO recordDAO;
	
	@Resource
	private IMemberDAO memberDAO;
	
	@Override
	public boolean recordmember(Map map) throws Exception {
		return this.recordDAO.doCreateRecord(map);
	}
	
	
	@Override
	public Long getAllCount(Map map) throws Exception {
		return this.recordDAO.getAllCount(map);
	}
	
	@Override
	public List<Record> getAllSplit(Map map)throws Exception {
		Iterator<Record> P = this.recordDAO.findAllSplit(map).iterator();
		
		if (P!= null) {
			List<Record> result = new ArrayList<Record>();
			while (P.hasNext()) {
				Record vo = P.next();
				
				
				Member member=this.memberDAO.findById(vo.getMember_id());
				vo.setMember(member);
				
				result.add(vo);
			}
			return result;
		}
		return null;
	}
	
}

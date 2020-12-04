package com.artegentech.system.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.artegentech.system.dao.IDeptDAO;
import com.artegentech.system.dao.IMemberDAO;
import com.artegentech.system.dao.IPDRLogDAO;
import com.artegentech.system.service.IDeptService;
import com.artegentech.system.service.IPDRLogService;
import com.artegentech.system.service.abs.AbstractService;
import com.artegentech.system.vo.Customer;
import com.artegentech.system.vo.Dept;
import com.artegentech.system.vo.Member;
import com.artegentech.system.vo.PDR;
import com.artegentech.system.vo.PDRDetail;
import com.artegentech.system.vo.PDRLog;

@Service
public class PDRLogServiceImpl extends AbstractService implements IPDRLogService {

	@Resource
	private IPDRLogDAO pdrlog;
	
	@Resource
	private IMemberDAO memberdao;

	public List<PDRLog> findLastById(String PDR_id)throws Exception{
		Iterator<PDRLog> P = this.pdrlog.findLastById(PDR_id).iterator();
		
		if (P!= null) {
			List<PDRLog> result = new ArrayList<PDRLog>();
			while (P.hasNext()) {
				PDRLog vo = P.next();
				
				Member member=this.memberdao.findById(vo.getMember_id());
				vo.setMember(member);
				
				result.add(vo);
			}
			return result;
		}
		return null;
	}
	
	

	public List<PDRLog> findByPDR_id(String PDR_id)throws Exception{
		Iterator<PDRLog> P = this.pdrlog.findById(PDR_id).iterator();
		
		if (P!= null) {
			List<PDRLog> result = new ArrayList<PDRLog>();
			while (P.hasNext()) {
				PDRLog vo = P.next();
				
				Member member=this.memberdao.findById(vo.getMember_id());
				vo.setMember(member);
				
				result.add(vo);
			}
			return result;
		}
		return null;
	}

	
	public boolean add(PDRLog vo)throws Exception{
		return this.pdrlog.doCreate(vo);
	}
	
	public boolean removeByno(Integer no)throws Exception{
		return this.pdrlog.removeByno(no);
	}
	
	@Override
	public boolean edit(PDRLog vo) throws Exception {
		return this.pdrlog.doUpdate(vo);
	}

}

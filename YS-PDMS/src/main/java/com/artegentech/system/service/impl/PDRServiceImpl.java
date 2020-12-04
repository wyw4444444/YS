package com.artegentech.system.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.artegentech.system.dao.ICustomerDAO;
import com.artegentech.system.dao.IMemberDAO;
import com.artegentech.system.dao.IPDRDAO;
import com.artegentech.system.dao.IPDRLogDAO;
import com.artegentech.system.dao.IRoleDAO;
import com.artegentech.system.dao.ITypeDAO;
import com.artegentech.system.service.IPDRCostService;
import com.artegentech.system.service.IPDRDetailService;
import com.artegentech.system.service.IPDRLogService;
import com.artegentech.system.service.IPDRService;
import com.artegentech.system.service.abs.AbstractService;
import com.artegentech.system.vo.Customer;
import com.artegentech.system.vo.Member;
import com.artegentech.system.vo.PDR;
import com.artegentech.system.vo.PDRCost;
import com.artegentech.system.vo.PDRDetail;
import com.artegentech.system.vo.PDRLog;
import com.artegentech.system.vo.Role;

import aj.org.objectweb.asm.Type;

@Service
public class PDRServiceImpl extends AbstractService implements IPDRService {

	@Resource
	private IPDRDAO PDRdao;
	
	@Resource
	private IRoleDAO roledao;
	
	@Resource
	private IMemberDAO memberdao;
	
	@Resource
	private ICustomerDAO customerdao;
	
	@Resource
	private ITypeDAO typedao;
	
	@Resource
	private IPDRLogService pdr_logservice;
	
	@Resource
	private IPDRDetailService pdrdetailservice;
	
	@Resource
	private IPDRCostService pdrcostservice;

	@Override
	public boolean add(PDR vo) throws Exception {
		return this.PDRdao.doCreate(vo);
	}

	@Override
	public boolean edit(PDR vo) throws Exception {
		return this.PDRdao.doUpdate(vo);
	}


	
	@Override
	public Long getAllCount(Map map) throws Exception {
		return this.PDRdao.getAllCount(map);
	}

	@Override
	public List<PDR> getAllSplit(Map map)throws Exception {
		Iterator<PDR> P = this.PDRdao.findAllSplit(map).iterator();
		
		if (P!= null) {
			List<PDR> result = new ArrayList<PDR>();
			while (P.hasNext()) {
				PDR vo = P.next();
				Customer customer=this.customerdao.findByNo(vo.getCustomer_no());
				if (customer !=null) {
					vo.setCustomer(customer);
				}
				
				Member member=this.memberdao.findById(vo.getPM());
				vo.setMember(member);
				Member manDetail=this.memberdao.findById(vo.getMan());
				vo.setManDetail(manDetail);
				
				com.artegentech.system.vo.Type type=this.typedao.findById(vo.getType_id_status());
				vo.setType(type);
				
				List<PDRLog> pdrlog=this.pdr_logservice.findLastById(vo.getPDR_id());
				vo.setPdrlog(pdrlog);
				
				List<PDRDetail> pdrdetail=this.pdrdetailservice.findByPDR_id(vo.getPDR_id());
				vo.setPdr_detail(pdrdetail);
				
				List<PDRCost> pdrcost=this.pdrcostservice.findByPDR_id(vo.getPDR_id());
				vo.setPdrcost(pdrcost);;
				
				
				result.add(vo);
			}
			return result;
		}
		return null;
	}

	
	
	@Override
	public PDR getByPDR_id(String PDR_id)throws Exception {
		PDR P = this.PDRdao.getByPDR_id(PDR_id);
		if(P==null) {
			return P;
		}else {
			Member member=this.memberdao.findById(P.getPM());
			P.setMember(member);
			List<PDRLog> pdrlog=this.pdr_logservice.findLastById(P.getPDR_id());
			P.setPdrlog(pdrlog);
			
			return P;			
		}
	}
	
	
	@Override
	public PDR getLast()throws Exception {
		return this.PDRdao.getLast();
	}

}

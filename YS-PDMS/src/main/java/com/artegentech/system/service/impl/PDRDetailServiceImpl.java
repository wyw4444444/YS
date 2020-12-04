package com.artegentech.system.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.artegentech.system.dao.IPDRDAO;
import com.artegentech.system.dao.IPDRDetailDAO;
import com.artegentech.system.service.IPDRDetailService;
import com.artegentech.system.service.IPDRService;
import com.artegentech.system.service.abs.AbstractService;
import com.artegentech.system.vo.PDR;
import com.artegentech.system.vo.PDRDetail;

@Service
public class PDRDetailServiceImpl extends AbstractService implements IPDRDetailService {

	@Resource
	private IPDRDetailDAO pdrdetailDAO;
	
	@Resource
	private IPDRDAO pdrDAO;
	
	@Resource
	private IPDRService pdrService;
	
	@Override
	public boolean  removeByPDR_id(String PDR_id) throws Exception{
		return this.pdrdetailDAO.removeByPDR_id(PDR_id);
	}
	
	@Override
	public boolean add(List<PDRDetail> list) throws Exception {
		boolean a=true;
		for (int i=0 ; i<list.size() ; i++){
			if(this.pdrdetailDAO.doCreate(list.get(i))==false) {
				a=false;
			}
		}
		return a;
	}

	public List<PDRDetail> findByLate()throws Exception{
		Iterator<PDRDetail> P=this.pdrdetailDAO.findByLate().iterator();
		if (P!= null) {
			List<PDRDetail> result = new ArrayList<PDRDetail>();
			while (P.hasNext()) {
				PDRDetail vo = P.next();
				
				PDR pdr=this.pdrService.getByPDR_id(vo.getPDR_id());
				vo.setPdr(pdr);
				
				result.add(vo);
			}
			return result;
		}
		return null;
	}
	
	public List<PDRDetail> findByDays()throws Exception{
		Iterator<PDRDetail> P=this.pdrdetailDAO.findByDays().iterator();
		if (P!= null) {
			List<PDRDetail> result = new ArrayList<PDRDetail>();
			while (P.hasNext()) {
				PDRDetail vo = P.next();
				
				PDR pdr=this.pdrService.getByPDR_id(vo.getPDR_id());
				vo.setPdr(pdr);
				
				result.add(vo);
			}
			return result;
		}
		return null;
	}

	public List<PDRDetail> findByPDR_id(String PDR_id)throws Exception{
		return this.pdrdetailDAO.findByPDR_id(PDR_id);
	}
	
}

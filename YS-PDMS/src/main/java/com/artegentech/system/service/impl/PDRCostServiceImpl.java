package com.artegentech.system.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.artegentech.system.dao.IPDRCostDAO;
import com.artegentech.system.dao.IPDRDAO;
import com.artegentech.system.dao.IPDRDetailDAO;
import com.artegentech.system.dao.IWarehouseInDAO;
import com.artegentech.system.dao.IWarehouseOutDAO;
import com.artegentech.system.service.IPDRCostService;
import com.artegentech.system.service.IPDRDetailService;
import com.artegentech.system.service.IPDRService;
import com.artegentech.system.service.abs.AbstractService;
import com.artegentech.system.vo.PDR;
import com.artegentech.system.vo.PDRCost;
import com.artegentech.system.vo.PDRDetail;
import com.artegentech.system.vo.WarehouseIn;
import com.artegentech.system.vo.WarehouseOut;

@Service
public class PDRCostServiceImpl extends AbstractService implements IPDRCostService {
	
	@Resource
	private IPDRCostDAO pdrcostDAO;
	
	@Resource
	private IPDRDAO pdrDAO;
	
	@Resource
	private IPDRService pdrService;
	@Resource
	private IWarehouseOutDAO warehouseOutDAO;
	@Resource
	private IWarehouseInDAO warehouseInDAO;
	
	@Override
	public boolean  removeByPDR_id(String PDR_id) throws Exception{
		return this.pdrcostDAO.removeByPDR_id(PDR_id);
	}
	
	@Override
	public boolean add(List<PDRCost> list) throws Exception {
		boolean a=true;
		for (int i=0 ; i<list.size() ; i++){
			if(this.pdrcostDAO.doCreate(list.get(i))==false) {
				a=false;
			}
		}
		return a;
	}

	public List<PDRCost> findByPDR_id(String PDR_id)throws Exception{
		List<PDRCost> list = this.pdrcostDAO.findByPDR_id(PDR_id);
		if(list!=null&&list.size()!=0) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("pdr_no", PDR_id);
			List<WarehouseOut> list2 = this.warehouseOutDAO.findAllByPDR(map);
			for (int i=0 ; i<list2.size() ; i++){
				String part_code = list2.get(i).getPart_code();
				List<WarehouseIn> list3 = this.warehouseInDAO.findAllNoLockedByPartCode(part_code);
				double amount = 0;
				double quantity = 0;
				for (int j=0 ; j<list3.size() ; j++){
					amount=amount+list3.get(j).getAmount();
					quantity=quantity+list3.get(j).getQuantity();
				}
				double count = list2.get(i).getTotal_quantity();
				double amount2 = amount/quantity*count;//入庫時總金額除以入庫時總數量，再乘以出庫時總數量，就等於出庫所需要的費用
				PDRCost pc = new PDRCost();
				pc.setPDR_id(PDR_id);
				pc.setEstimate_cost((float)amount2);
				pc.setDescription("出庫費用");
				pc.setName(part_code);
				list.add(pc);
			}			
		}
		return list;
	}
	
}

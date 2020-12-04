package com.artegentech.system.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.artegentech.system.dao.ICustomerDAO;
import com.artegentech.system.service.ICustomerService;
import com.artegentech.system.service.abs.AbstractService;
import com.artegentech.system.vo.Customer;


@Service
public class CustomerServiceImpl extends AbstractService implements ICustomerService {
	@Resource
	private ICustomerDAO customerDAO;
	
	@Override
	public Long getAllCount(String column, String keyword) throws Exception {
		Map<String, Object> map=super.handleParams(column, keyword);
		return this.customerDAO.getAllCount(map);
	}

	@Override
	public List<Customer> getAllSplit(String column, String keyword, Integer currentPage, Integer lineSize) throws Exception {
		Map<String, Object> map=super.handleParams(column, keyword, currentPage, lineSize);
		return this.customerDAO.findAllSplit(map);
	}
	

	@Override
	public boolean add(Customer customer) throws Exception {
		if (this.customerDAO. findByShort_name(customer.getShort_name()) == null) {
			return this.customerDAO.doCreate(customer);
		}
		return false;
	}

	@Override
	public boolean edit(Customer customer) throws Exception {
		Customer customer2 = this.customerDAO. findByNo(customer.getNo());
		if (customer2 == null) {
			return false;
		} else {
			return this.customerDAO.doUpdate(customer);
		}
	}
	
	@Override
	public Customer findByShort_name(String short_name) throws Exception {
		return this.customerDAO. findByShort_name(short_name);
	}
	
	
	@Override
	public Customer findByNo(Integer no) throws Exception {
		return this.customerDAO. findByNo(no);
	}
	
	

	@Override
	public Long getAllCount() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}


}

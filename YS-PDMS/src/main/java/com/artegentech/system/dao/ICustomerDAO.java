package com.artegentech.system.dao;

import java.util.List;
import java.util.Map;

import com.artegentech.system.vo.Customer;


public interface ICustomerDAO extends IDAO<Integer, Customer> {
	
	public Customer findByShort_name(String short_name) throws Exception;
	
	
	public Customer findByNo(Integer no) throws Exception;
	
	public List<Customer> findByFull_Name(String line_code) throws Exception;
	
}

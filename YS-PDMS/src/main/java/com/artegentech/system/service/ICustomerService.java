package com.artegentech.system.service;

import java.util.List;

import com.artegentech.system.vo.Customer;

public interface ICustomerService {
	
	
	public boolean add(Customer customer) throws Exception;

	
	public boolean edit(Customer customer) throws Exception;

	public List<Customer> getAllSplit(String column, String keyword, Integer currentPage, Integer lineSize) throws Exception;

	public Long getAllCount() throws Exception;

	public Long getAllCount(String column, String keyword) throws Exception;
	
	public Customer findByShort_name(String short_name) throws Exception;
	
	public Customer findByNo(Integer no) throws Exception;

}

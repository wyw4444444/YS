package com.artegentech.system.service;

import java.util.List;

import com.artegentech.system.vo.ClosingAccount;

public interface IClosingAccountService {

	/**
	 * 结账处理
	 * 
	 * @return
	 * @throws Exception
	 */
	public boolean add(ClosingAccount closingAccount) throws Exception;

	/**
	 * 查询全部结账时间
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<ClosingAccount> getAll() throws Exception;

	/**
	 * 模糊分页查询全部数据资料
	 * 
	 * @param currentPage 当前所在页
	 * @param lineSize    每页显示的数据行数
	 * @return 返回多个ClosingAccount对象，将以list集合形式返回。如果表中没有数据则返回list的集合长度为0(size()==0)
	 * @throws Exception 数据库未连接，或者数据库操作错误
	 */
	public List<ClosingAccount> getAllSplit(Integer currentPage, Integer lineSize) throws Exception;
	
	/**
	 * 抓取所有结账时间的总行数
	 * 
	 * @return
	 * @throws Exception
	 */
	public Long getAllCount() throws Exception;
}

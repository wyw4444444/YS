package com.artegentech.system.dao;

import com.artegentech.system.vo.ClosingAccount;

public interface IClosingAccountDAO extends IDAO<Integer, ClosingAccount> {

	/**
	 * 保存closingAccount数据
	 * 
	 * @param closingAccount
	 * @return 保存成功返回true，保存失败返回false
	 * @throws Exception 数据库未连接，或者数据库操作错误
	 */
	public boolean doCreate(ClosingAccount closingAccount) throws Exception;
	
	/**
	 * 查询数据表中的最近一笔结账时间
	 * 
	 * @return 返回ClosingAccount对象
	 * @throws Exception 数据库未连接，或者数据库操作错误
	 */
	public ClosingAccount findLatestClosingAccount() throws Exception;
}

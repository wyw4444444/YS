package com.artegentech.system.service;

import java.util.List;

import com.artegentech.system.vo.SafetyStorage;

public interface ISafetyStorageService {

	/**
	 * 增加安全库存信息
	 * 
	 * @param safetyStorage
	 * @return
	 * @throws Exception
	 */
	public boolean add(SafetyStorage safetyStorage) throws Exception;

	/**
	 * 查询所有安全库存信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<SafetyStorage> getAll() throws Exception;

	/**
	 * 模糊分页查询所有安全库存信息
	 * 
	 * @param column      要进行模糊查询的数据列
	 * @param keyword     要查询的关键字，如果关键字为空字符串则表示查询全部
	 * @param currentPage 当前所在页
	 * @param lineSize    每页显示的数据行数
	 * @return 返回多个SafetyStorage对象，将以list集合的形式返回。如果表中没有数据则返回的list集合长度为0(size()==0)
	 * @throws Exception 数据库未连接，或者数据库操作错误
	 */
	public List<SafetyStorage> getAllSplit(String column, String keyword, Integer currentPage, Integer lineSize)
			throws Exception;

	/**
	 * 查询所有最新安全库存信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<SafetyStorage> getAllLatest() throws Exception;

	/**
	 * 模糊分页查询所有最新安全库存信息
	 * 
	 * @param currentPage 当前所在页
	 * @param lineSize    每页显示的数据行数
	 * @return 返回多个SafetyStorage对象，将以list集合的形式返回。如果表中没有数据则返回的list集合长度为0(size()==0)
	 * @throws Exception 数据库未连接，或者数据库操作错误
	 */
	public List<SafetyStorage> getAllLatestSplit(Integer currentPage, Integer lineSize) throws Exception;

	/**
	 * 抓取所有安全库存资料的总行数
	 * 
	 * @return
	 * @throws Exception
	 */
	public Long getAllCount() throws Exception;

	/**
	 * 抓取所有安全库存资料的总行数
	 * 
	 * @return
	 * @throws Exception
	 */
	public Long getAllCount(String column, String keyword) throws Exception;

	/**
	 * 抓取所有最新安全库存资料的总行数
	 * 
	 * @return
	 * @throws Exception
	 */
	public Long getAllLatestCount() throws Exception;

}

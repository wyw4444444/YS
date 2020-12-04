package com.artegentech.system.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.artegentech.system.vo.WarehouseIn;

public interface IWarehouseInService {

	/**
	 * 批量增加入库记录
	 * 
	 * @param WarehouseIn
	 * @return
	 * @throws Exception
	 */
	public boolean addBatch(List<WarehouseIn> list);
	
	/**
	 * 增加入库记录
	 * 
	 * @param warehouseIn
	 * @return
	 * @throws Exception
	 */
	public boolean add(WarehouseIn warehouseIn) throws Exception;

	/**
	 * 修改入库记录
	 * 
	 * @param warehouseIn
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> edit(WarehouseIn warehouseIn, WarehouseIn warehouseIn2, Boolean flag);

	/**
	 * 查询全部入库记录
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<WarehouseIn> getAll() throws Exception;
	
	/**
	 * 模糊分页查询全部数据资料
	 * 
	 * @param column      要进行模糊查询的数据列
	 * @param keyword     要查询的关键字，如果关键字为空字符串则表示查询全部
	 * @param currentPage 当前所在页
	 * @param lineSize    每页显示的数据行数
	 * @param start_date  入库日期范围起始日期
	 * @param end_date    入库日期范围结束日期
	 * @return 返回多个WarehouseIn对象，将以list集合形式返回。如果表中没有数据则返回list的集合长度为0(size()==0)
	 * @throws Exception 数据库未连接，或者数据库操作错误
	 */
	public List<WarehouseIn> getAllSplit(String column, String keyword, Integer currentPage, Integer lineSize,
			Date start_date, Date end_date) throws Exception;
	
	/**
	 * 模糊分页查询全部数据资料
	 * 
	 * @param column      要进行模糊查询的数据列
	 * @param keyword     要查询的关键字，如果关键字为空字符串则表示查询全部
	 * @param currentPage 当前所在页
	 * @param lineSize    每页显示的数据行数
	 * @param start_date  入库日期范围起始日期
	 * @param end_date    入库日期范围结束日期
	 * @return 返回多个WarehouseIn对象，将以list集合形式返回。如果表中没有数据则返回list的集合长度为0(size()==0)
	 * @throws Exception 数据库未连接，或者数据库操作错误
	 */
	public List<WarehouseIn> getAllSplit2(String column, String keyword, Integer currentPage, Integer lineSize,
			Date start_date, Date end_date) throws Exception;
	
	/**
	 * 模糊分页查询全部未锁定数据资料
	 * 
	 * @param column      要进行模糊查询的数据列
	 * @param keyword     要查询的关键字，如果关键字为空字符串则表示查询全部
	 * @param currentPage 当前所在页
	 * @param lineSize    每页显示的数据行数
	 * @param start_date  入库日期范围起始日期
	 * @param end_date    入库日期范围结束日期
	 * @return 返回多个WarehouseIn对象，将以list集合形式返回。如果表中没有数据则返回list的集合长度为0(size()==0)
	 * @throws Exception 数据库未连接，或者数据库操作错误
	 */
	public List<WarehouseIn> getAllUnlockedSplit(String column, String keyword, Integer currentPage, Integer lineSize,
			Date start_date, Date end_date) throws Exception;
	
	/**
	 * 根据in_id查询入库记录
	 * 
	 * @param in_id
	 * @return
	 * @throws Exception
	 */
	public WarehouseIn getById(String in_id) throws Exception;

	/**
	 * 抓取所有入库记录的总行数
	 * 
	 * @param column      要进行模糊查询的数据列
	 * @param keyword     要查询的关键字，如果关键字为空字符串则表示查询全部
	 * @param start_date  入库日期范围起始日期
	 * @param end_date    入库日期范围结束日期
	 * @return
	 * @throws Exception
	 */
	public Long getAllCount(String column, String keyword, Date start_date, Date end_date) throws Exception;
	
	/**
	 * 抓取所有入库记录的总行数
	 * 
	 * @param column      要进行模糊查询的数据列
	 * @param keyword     要查询的关键字，如果关键字为空字符串则表示查询全部
	 * @param start_date  入库日期范围起始日期
	 * @param end_date    入库日期范围结束日期
	 * @return
	 * @throws Exception
	 */
	public Long getAllCount2(String column, String keyword, Date start_date, Date end_date) throws Exception;
	
	/**
	 * 抓取所有未锁定的入库记录的总行数
	 * 
	 * @param column      要进行模糊查询的数据列
	 * @param keyword     要查询的关键字，如果关键字为空字符串则表示查询全部
	 * @param start_date  入库日期范围起始日期
	 * @param end_date    入库日期范围结束日期
	 * @return
	 * @throws Exception
	 */
	public Long getAllUnlockedCount(String column, String keyword, Date start_date, Date end_date) throws Exception;

	/**
	 * 根据数据表名称与入库记录ID前10位查询出共有多少个入库记录，以便形成新的入库ID
	 * 
	 * @param pre_in_id(IN20201023代表入库ID的前10位)
	 * @return 匹配pre_in_id的已经出现的记录数量
	 * @throws Exception
	 */
	public Long getDayIdNum(String pre_in_id) throws Exception;
	
	/**
	 * 查询全部库存
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<WarehouseIn> getAllStorage() throws Exception;
	
	/**
	 * 分页查询全部库存
	 * 
	 * @param params 传入的参数如下：<br>
	 *               1、key=column,value=模糊查询数据列<br>
	 *               2、key=keyword,value=模糊查询关键字，如果关键字为空字符串则表示查询全部<br>
	 *               3、key=start,value=(currentPage-1)*lizeSize，开始记录数<br>
	 *               4、key=lineSize,value=每页显示的数据行数<br>
	 * @return 返回多个WarehouseIn对象，将以list集合形式返回。如果表中没有数据则返回list的集合长度为0(size()==0)
	 * @throws Exception 数据库未连接，或者数据库操作错误
	 */
	public List<WarehouseIn> getAllStorageSplit(String column, String keyword, Integer currentPage, Integer lineSize) throws Exception;

	/**
	 * 抓取所有库存的总行数
	 * 
	 * params 传入的参数如下：<br>
	 *               1、key=column,value=模糊查询数据列<br>
	 *               2、key=keyword,value=模糊查询关键字，如果关键字为空字符串则表示查询全部<br>
	 * @return
	 * @throws Exception
	 */
	public Long getAllStorageCount(String column, String keyword) throws Exception;
	
	/**
	 * 分页查询全部库存，以料号加总
	 * 
	 * @param currentPage 当前所在页
	 * @param lineSize    每页显示的数据行数
	 * @return 返回多个WarehouseIn对象，将以list集合形式返回。如果表中没有数据则返回list的集合长度为0(size()==0)
	 * @throws Exception 数据库未连接，或者数据库操作错误
	 */
	public List<WarehouseIn> getAllStorageByPartCodeSplit(Integer currentPage, Integer lineSize) throws Exception;

	/**
	 * 抓取所有库存的总行数，以料号加总
	 * 
	 * @return
	 * @throws Exception
	 */
	public Long getAllStorageByPartCodeCount() throws Exception;
	
	/**
	 * 以part_code计算所有未锁定的入库记录的quantity
	 * 
	 * @param part_code
	 * @return 使用SUM()函数的统计结果
	 * @throws Exception 数据库未连接，或者数据库操作错误
	 */
	public Double getSumQuantityByPartCode(String part_code) throws Exception;
	
	/**
	 * 以part_code计算所有未锁定的入库记录的剩余数量surplus_quantity
	 * 
	 * @param part_code
	 * @return 使用SUM()函数的统计结果
	 * @throws Exception 数据库未连接，或者数据库操作错误
	 */
	public Double getSumSurplusQuantityByPartCode(String part_code) throws Exception;
	
	/**
	 * 获取当前所有库存总金额（指已入库后的）
	 * 
	 * @return 使用SUM()函数的统计结果
	 * @throws Exception 数据库未连接，或者数据库操作错误
	 */
	public Double getSumStorageAmount(String column, String keyword) throws Exception;
}

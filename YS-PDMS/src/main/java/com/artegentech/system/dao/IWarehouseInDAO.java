package com.artegentech.system.dao;

import java.util.List;
import java.util.Map;

import com.artegentech.system.vo.WarehouseIn;

public interface IWarehouseInDAO extends IDAO<String, WarehouseIn> {

	/**
	 * 保存warehouse_in数据表的数据
	 * 
	 * @param params 传入的参数如下：WarehouseIn数组<br>
	 * @return 保存成功返回true，保存失败返回false
	 * @throws Exception 数据库未连接，或者数据库操作错误
	 */
	public boolean doCreateBatch(List<WarehouseIn> warehouseIns) throws Exception;

	/**
	 * 根据采购记录ID前10位查询出共有多少个入库记录，以便形成新的入库ID
	 * 
	 * @param pre_in_id(IN20201023代表采购ID的前10位)
	 * @return 使用COUNT()函数的统计结果
	 * @throws Exception
	 */
	public Long getDayIdNum(String pre_in_id) throws Exception;

	/**
	 * 根据part_code查询所有剩余数量不为0的入库记录
	 * 
	 * @param params part_code
	 * @return
	 * @throws Exception
	 */
	public List<WarehouseIn> findAllSurplusListByPartCode(String part_code) throws Exception;
	
	/**
	 * 根据part_code查询所有未锁定入库记录
	 * 
	 * @param part_code
	 * @return
	 * @throws Exception
	 */
	public List<WarehouseIn> findAllNoLockedByPartCode(String part_code) throws Exception;
	
	/**
	 * 根据part_code查询未锁定的数据量
	 * 
	 * @param part_code
	 * @return 使用COUNT()函数的统计结果
	 * @throws Exception 数据库未连接，或者数据库操作错误
	 */
	public Long getAllNoLockedByPartCodeCount(String part_code) throws Exception;

	/**
	 * 抓取当前库存资料，已入库、剩余数量大于0
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<WarehouseIn> findAllStorage() throws Exception;

	/**
	 * 分页抓取当前库存资料，已入库、剩余数量大于0
	 * 
	 * @param params 传入的参数如下：<br>
	 *               1、key=column,value=模糊查询数据列<br>
	 *               2、key=keyword,value=模糊查询关键字，如果关键字为空字符串则表示查询全部<br>
	 *               3、key=start,value=(currentPage-1)*lizeSize，开始记录数<br>
	 *               4、key=lineSize,value=每页显示的数据行数<br>
	 * @return
	 * @throws Exception
	 */
	public List<WarehouseIn> findAllStorageSplit(Map<String, Object> params) throws Exception;

	/**
	 * 统计库存的数据量
	 * 
	 * @param params 传入的参数如下：<br>
	 *               1、key=column,value=模糊查询数据列<br>
	 *               2、key=keyword,value=模糊查询关键字，如果关键字为空字符串则表示查询全部<br>
	 * @return 使用COUNT()函数的统计结果
	 * @throws Exception 数据库未连接，或者数据库操作错误
	 */
	public Long getAllStorageCount(Map<String, Object> params) throws Exception;

	/**
	 * 分页抓取当前分料号的库存资料，已入库、剩余数量大于0
	 * 
	 * @param params 传入的参数如下：<br>
	 *               3、key=start,value=(currentPage-1)*lizeSize，开始记录数<br>
	 *               4、key=lineSize,value=每页显示的数据行数<br>
	 * @return
	 * @throws Exception
	 */
	public List<WarehouseIn> findAllStorageByPartCodeSplit(Map<String, Object> params) throws Exception;

	/**
	 * 分料号统计库存的数据量
	 * 
	 * @return 使用COUNT()函数的统计结果
	 * @throws Exception 数据库未连接，或者数据库操作错误
	 */
	public Long getAllStorageByPartCodeCount() throws Exception;
	
	/**
	 * 根据part_code抓取未锁定的总的入库数量
	 * 
	 * @param part_code
	 * @return 使用SUM()函数的统计结果
	 * @throws Exception 数据库未连接，或者数据库操作错误
	 */
	public Double getSumQuantityByPartCode(String part_code) throws Exception;
	
	/**
	 * 根据part_code抓取未锁定的当前的剩余数量
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
	public Double getSumStorageAmount(Map<String, Object> params) throws Exception;
	
	/**
	 * 分页抓取当前库存资料，已入库、剩余数量大于0
	 * 
	 * @param params 传入的参数如下：<br>
	 *               1、key=column,value=模糊查询数据列<br>
	 *               2、key=keyword,value=模糊查询关键字，如果关键字为空字符串则表示查询全部<br>
	 *               3、key=start,value=(currentPage-1)*lizeSize，开始记录数<br>
	 *               4、key=lineSize,value=每页显示的数据行数<br>
	 * @return
	 * @throws Exception
	 */
	public List<WarehouseIn> findAllSplit2(Map<String, Object> params) throws Exception;
	
	/**
	 * 统计库存的数据量
	 * 
	 * @param params 传入的参数如下：<br>
	 *               1、key=column,value=模糊查询数据列<br>
	 *               2、key=keyword,value=模糊查询关键字，如果关键字为空字符串则表示查询全部<br>
	 * @return 使用COUNT()函数的统计结果
	 * @throws Exception 数据库未连接，或者数据库操作错误
	 */
	public Long getAllCount2(Map<String, Object> params) throws Exception;

}

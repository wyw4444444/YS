package com.artegentech.system.dao;

import java.util.List;
import java.util.Map;

import com.artegentech.system.vo.WarehouseOut;

public interface IWarehouseOutDAO extends IDAO<String, WarehouseOut> {

	/**
	 * 根据数据表名称与出库记录ID前11位查询出共有多少个出库记录，以便形成新的出库ID
	 * 
	 * @param pre_out_id(OUT20201026代表出库ID的前11位)
	 * @return 使用COUNT()函数的统计结果
	 * @throws Exception
	 */
	public Long getDayIdNum(String pre_out_id) throws Exception;
	
	/**
	 * 根据part_code查询所有未锁定出库记录
	 * 
	 * @param part_code
	 * @return
	 * @throws Exception
	 */
	public List<WarehouseOut> findAllNoLockedByPartCode(String part_code) throws Exception;
	
	/**
	 * 根据part_code查询未锁定的数据量
	 * 
	 * @param part_code
	 * @return 使用COUNT()函数的统计结果
	 * @throws Exception 数据库未连接，或者数据库操作错误
	 */
	public Long getAllNoLockedByPartCodeCount(String part_code) throws Exception;
	
	/**
	 * 根据part_code抓取未锁定的总的出库数量
	 * 
	 * @param part_code
	 * @return 使用SUM()函数的统计结果
	 * @throws Exception 数据库未连接，或者数据库操作错误
	 */
	public Double getSumQuantityByPartCode(String part_code) throws Exception;
	
	/**
	 * 根据pdr_no和part_code抓取销售记录，倒序排序
	 * 
	 * @param params 传入的参数如下：<br>
	 *               1、key=pdr_no,value=pdr_no<br>
	 *               2、key=part_code,value=part_code<br>
	 * @return
	 * @throws Exception
	 */
	public List<WarehouseOut> findAllByPDRNoPartCode(Map<String, Object> params) throws Exception;
	
	public List<WarehouseOut> findAllByPDR(Map<String, Object> params) throws Exception;
	
	/**
	 * 根据pdr_no和part_code抓取销售数量
	 * 
	 * @param params 传入的参数如下：<br>
	 *               1、key=pdr_no,value=pdr_no<br>
	 *               2、key=part_code,value=part_code<br>
	 * @return
	 * @throws Exception
	 */
	public Double getSumQuantityByPDRNoPartCode(Map<String, Object> params) throws Exception;
}

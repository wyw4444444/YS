package com.artegentech.system.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.artegentech.system.vo.WarehouseOut;

public interface IWarehouseOutService {

	/**
	 * 增加出库记录
	 * 
	 * @param warehouseOut
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> add(WarehouseOut warehouseOut);

	/**
	 * 修改出库记录
	 * 
	 * @param warehouseOut
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> edit(WarehouseOut warehouseOut, WarehouseOut warehouseOut2, Boolean flag1, Boolean flag2);

	/**
	 * 查询全部出库记录
	 * 
	 * @param tblwarehouseOut 出库记录所在的数据表名称
	 * @return
	 * @throws Exception
	 */
	public List<WarehouseOut> getAll() throws Exception;

	/**
	 * 模糊分页查询全部数据资料
	 * 
	 * @param column          要进行模糊查询的数据列
	 * @param keyword         要查询的关键字，如果关键字为空字符串则表示查询全部
	 * @param currentPage     当前所在页
	 * @param lineSize        每页显示的数据行数
	 * @param start_date      出库日期范围起始日期
	 * @param end_date        出库日期范围结束日期
	 * @return 返回多个WarehouseOut对象，将以list集合形式返回。如果表中没有数据则返回list的集合长度为0(size()==0)
	 * @throws Exception 数据库未连接，或者数据库操作错误
	 */
	public List<WarehouseOut> getAllSplit(String column, String keyword, Integer currentPage, Integer lineSize,
			Date start_date, Date end_date) throws Exception;

	/**
	 * 根据out_id查询出库记录
	 * 
	 * @param out_id
	 * @return
	 * @throws Exception
	 */
	public WarehouseOut getById(String out_id) throws Exception;

	/**
	 * 抓取所有出库记录的总行数
	 * 
	 * @param params 传入的参数如下：<br>
	 *               1、key=column,value=模糊查询数据列<br>
	 *               2、key=keyword,value=模糊查询关键字，如果关键字为空字符串则表示查询全部<br>
	 *               3、key=start_date,value=start_date<br>
	 *               4、key=end_date,value=end_date<br>
	 * @return
	 * @throws Exception
	 */
	public Long getAllCount(String column, String keyword, Date start_date, Date end_date) throws Exception;

	/**
	 * 根据出库记录ID前11位查询出共有多少个出库记录，以便形成新的出库ID
	 * 
	 * @param pre_out_id(OUT20201026代表出库ID的前11位)<br>
	 * @return 匹配pre_out_id的已经出现的记录数量
	 * @throws Exception
	 */
	public Long getDayIdNum(String pre_out_id) throws Exception;
	
	/**
	 * 以part_code计算所有未锁定的出库记录的quantity
	 * 
	 * @param part_code
	 * @return 使用SUM()函数的统计结果
	 * @throws Exception 数据库未连接，或者数据库操作错误
	 */
	public Double getSumQuantityByPartCode(String part_code) throws Exception;
	
	public List<WarehouseOut> getAllByPDRNoPartCode(String pdr_no, String part_code) throws Exception;
	
	public Double getSumQuantityByPDRNoPartCode(String pdr_no, String part_code) throws Exception;

}

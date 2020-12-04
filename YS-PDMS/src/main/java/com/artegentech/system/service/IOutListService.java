package com.artegentech.system.service;

import java.util.List;

import com.artegentech.system.vo.OutList;
import com.artegentech.system.vo.WarehouseOut;

public interface IOutListService {
	
	/**
	 * 批量增加出库清单
	 * 
	 * @param outLists
	 * @return
	 * @throws Exception
	 */
	public boolean addStocks(WarehouseOut warehouseOut);
	
	/**
	 * 增加出库清单
	 * 
	 * @param outlist
	 * @return
	 * @throws Exception
	 */
	public boolean add(OutList outlist) throws Exception;

	/**
	 * 修改出库清单
	 * 
	 * @param salelist
	 * @return
	 * @throws Exception
	 */
	public boolean edit(OutList outlist) throws Exception;

	/**
	 * 基于in_id修改出库清单中的pur_price
	 * 
	 * @param in_id
	 * @param pur_price
	 * @return
	 * @throws Exception
	 */
	public boolean editPurPriceByInId(String in_id, Double pur_price) throws Exception;

	/**
	 * 根据outlist_id查询出库清单
	 * 
	 * @param outlist_id
	 * @return
	 * @throws Exception
	 */
	public OutList getById(Integer outlist_id) throws Exception;

	/**
	 * 查询全部出库清单
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<OutList> getAll() throws Exception;

	/**
	 * 模糊分页查询全部数据资料
	 * 
	 * @param column      要进行模糊查询的数据列
	 * @param keyword     要查询的关键字，如果关键字为空字符串则表示查询全部
	 * @param currentPage 当前所在页
	 * @param lineSize    每页显示的数据行数
	 * @return 返回多个OutList对象，将以list集合形式返回。如果表中没有数据则返回list的集合长度为0(size()==0)
	 * @throws Exception 数据库未连接，或者数据库操作错误
	 */
	public List<OutList> getAllSplit(String column, String keyword, Integer currentPage, Integer lineSize) throws Exception;

	/**
	 * 抓取所有出库清单的总行数
	 * 
	 * @param params 传入的参数如下：<br>
	 *               1、key=column,value=模糊查询数据列<br>
	 *               2、key=keyword,value=模糊查询关键字，如果关键字为空字符串则表示查询全部<br>
	 * @return
	 * @throws Exception
	 */
	public Long getAllCount(String column, String keyword) throws Exception;

	/**
	 * 以part_code重新计算入库、出库、出库清单等资料
	 * 
	 * @param part_code
	 * @return 
	 * @throws Exception
	 */
	public boolean recalculateData(String part_code);
	
	public List<OutList> getAllByOutIdDESC(String out_id) throws Exception;
}

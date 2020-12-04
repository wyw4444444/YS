package com.artegentech.system.dao;

import java.util.List;
import java.util.Map;

import com.artegentech.system.vo.OutList;

public interface IOutListDAO extends IDAO<Integer, OutList> {

	/**
	 * 本次修改是基于in_id修改入库对应单价的处理
	 * 
	 * @param params 传入的参数如下：<br>
	 *               1、key=in_id,value=in_id<br>
	 *               2、key=pur_price,value=pur_price<br>
	 * @return 修改成功返回true，修改失败返回false
	 * @throws Exception 数据库未连接，或者数据库操作错误
	 */
	public boolean doUpdatePurPriceByInId(Map<String, Object> params) throws Exception;
	
	/**
	 * 根据out_id查询数据表中的全部出库清单数据
	 * 
	 * @param out_id
	 * @return 返回多个OutList对象，将以list集合的形式返回。如果表中没有数据则返回的list集合长度为0(size()==0)
	 * @throws Exception 数据库未连接，或者数据库操作错误
	 */
	public List<OutList> findAllByOutId(String out_id) throws Exception;
	
	/**
	 * 根据in_id查询数据表中的全部出库清单数据
	 * 
	 * @param in_id
	 * @return 返回多个OutList对象，将以list集合的形式返回。如果表中没有数据则返回的list集合长度为0(size()==0)
	 * @throws Exception 数据库未连接，或者数据库操作错误
	 */
	public List<OutList> findAllByInId(String in_id) throws Exception;

	/**
	 * 进行outlist数据表的删除，本次修改是基于id的删除处理
	 * 
	 * @param outlist_id
	 * @return 修改成功返回true，修改失败返回false
	 * @throws Exception 数据库未连接，或者数据库操作错误
	 */
	public boolean doRemove(Integer outlist_id) throws Exception;
	
	/**
	 * 进行outlist数据表的删除，本次修改是基于out_id的删除处理，主要考虑将sale的数量修改为0时候的状况
	 * 
	 * @param out_id
	 * @return 修改成功返回true，修改失败返回false
	 * @throws Exception 数据库未连接，或者数据库操作错误
	 */
	public boolean doRemoveByOutId(String out_id) throws Exception;
	
	/**
	 * 根据out_id查询数据表中的全部出库清单数据
	 * 
	 * @param out_id
	 * @return 返回多个OutList对象，将以list集合的形式返回。如果表中没有数据则返回的list集合长度为0(size()==0)
	 * @throws Exception 数据库未连接，或者数据库操作错误
	 */
	public List<OutList> findAllByOutIdDESC(String out_id) throws Exception;
	
}

package com.artegentech.system.dao;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 定義V數據表的操作標準
 * 
 * @author Gavin Guo
 * @param <K> K指的是數據的類型，比如mid是String，則根據mid查詢的時候K寫為String
 * @param <V> 需要操作的V數據表，也就是vo類的類名
 */
public interface IDAO<K, V> {
	/**
	 * 保存V數據表中的數據
	 * 
	 * @param vo 要保存數據的vo對象
	 * @return 保存成功返回true，保存失敗返回false
	 * @throws Exception 數據庫未連接，或者數據庫操作錯誤
	 */
	public boolean doCreate(V vo) throws Exception;

	/**
	 * 進行V數據表的更新，本次修改是基於id的修改處理
	 * 
	 * @param vo 要修改的數據的vo對象
	 * @return 修改成功返回true，修改失敗返回false
	 * @throws Exception 數據庫未連接，或者數據庫操作錯誤
	 */
	public boolean doUpdate(V vo) throws Exception;

	/**
	 * 數據的刪除處理，會將所有要刪除的數據保存在Set集合中
	 * 
	 * @param ids 所有要刪除的ID編號
	 * @return 如果要刪除的數量符合最終的執行標準則返回true，否則返回false
	 * @throws Exception 數據庫未連接，或者數據庫操作錯誤
	 */
	public boolean doRemoveBatch(Set<K> ids) throws Exception;

	/**
	 * 根據ID查詢V數據表中的數據
	 * 
	 * @param id 要查詢的ID
	 * @return 如果數據找到則以vo類對象的形式返回，否則返回null
	 * @throws Exception 數據庫未連接，或者數據庫操作錯誤
	 */
	public V findById(K id) throws Exception;

	/**
	 * 查詢數據表中的全部數據
	 * 
	 * @return 返回多個V對象，將以list的集合形式返回。 如果表中沒有數據則返回的list集合長度為0(size()==0)
	 * @throws Exception 數據庫未連接，或者數據庫操作錯誤
	 */
	public List<V> findAll() throws Exception;

	/**
	 * 查詢數據表中未鎖定的全部數據
	 * 
	 * @return 返回多個V對象，將以list的集合形式返回。 如果表中沒有數據則返回的list集合長度為0(size()==0)
	 * @throws Exception 數據庫未連接，或者數據庫操作錯誤
	 */
	public List<V> findAllUnlocked() throws Exception;

	/**
	 *  所有模糊查詢的數據的分頁顯示處理
	 * @param params 傳入參數如下：<br>
	 * 1、key=column,value=模糊查詢數據列<br>
	 * 2、key=keyword,value=模糊查詢關鍵字<br>
	 * 3、key=start,value=(currentPage-1)*lizeSize，開始記錄數<br>
	 * 4、key=lineSize,value=每頁顯示的數據行數<br>
	 * @return
	 * @throws Exception
	 */
	public List<V> findAllSplit(Map<String,Object> params) throws Exception;

	/**
	 *  所有模糊查詢的未鎖定的數據的分頁顯示處理
	 * @param params 傳入參數如下：<br>
	 * 1、key=column,value=模糊查詢數據列<br>
	 * 2、key=keyword,value=模糊查詢關鍵字<br>
	 * 3、key=start,value=(currentPage-1)*lizeSize，開始記錄數<br>
	 * 4、key=lineSize,value=每頁顯示的數據行數<br>
	 * @return
	 * @throws Exception
	 */
	public List<V> findAllUnlockedSplit(Map<String,Object> params)
			throws Exception;

	/**
	 * 統計數據表中的全部數據量
	 * 
	 * @return 使用COUNT()函數的統計結果
	 * @throws Exception 數據庫未連接，或者數據庫操作錯誤
	 */
	public Long getAllCount() throws Exception;

	/**
	 * 統計數據表中未鎖定的全部數據量
	 * 
	 * @return 使用COUNT()函數的統計結果
	 * @throws Exception 數據庫未連接，或者數據庫操作錯誤
	 */
	public Long getAllUnlockedCount() throws Exception;

	/**
	 * 統計模糊查詢的數據量
	 * 
	 * @param column  需要進行查詢的數據列
	 * @param keyword 要查詢的關鍵字，如果關鍵字為空字符串則表示查詢全部
	 * @return 使用COUNT()函數的統計結果
	 * @throws Exception 數據庫未連接，或者數據庫操作錯誤
	 */
	public Long getAllCount(Map<String,Object> params) throws Exception;

	/**
	 * 統計模糊查詢的未鎖定的數據量
	 * 
	 * @param column  需要進行查詢的數據列
	 * @param keyword 要查詢的關鍵字，如果關鍵字為空字符串則表示查詢全部
	 * @return 使用COUNT()函數的統計結果
	 * @throws Exception 數據庫未連接，或者數據庫操作錯誤
	 */
	public Long getAllUnlockedCount(Map<String,Object> params) throws Exception;
}

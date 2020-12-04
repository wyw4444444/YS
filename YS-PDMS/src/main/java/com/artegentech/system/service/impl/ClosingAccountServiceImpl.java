package com.artegentech.system.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.artegentech.system.dao.IClosingAccountDAO;
import com.artegentech.system.dao.IWarehouseInDAO;
import com.artegentech.system.dao.IWarehouseOutDAO;
import com.artegentech.system.service.IClosingAccountService;
import com.artegentech.system.service.abs.AbstractService;
import com.artegentech.system.vo.ClosingAccount;
import com.artegentech.system.vo.WarehouseIn;
import com.artegentech.system.vo.WarehouseOut;

@Service
public class ClosingAccountServiceImpl extends AbstractService implements IClosingAccountService {
	
	@Resource
	IClosingAccountDAO closingAccountDAO;
	
	@Resource
	IWarehouseInDAO warehouseInDAO;
	
	@Resource
	IWarehouseOutDAO warehouseOutDAO;
	
	@Transactional(rollbackFor=Exception.class)
	@Override
	public boolean add(ClosingAccount closingAccount) throws Exception {
		/*
		 * 将所有当前库存记录抓出
		 * 循环处理，执行如下操作：
		 * 1、锁定原记录
		 * 2、将原记录中的剩余数量当做新记录的入库数量
		 * 3、将原记录中的in_id当做新记录的history_in_id
		 * 4、新记录的其它栏位与原记录相同
		 * 将所有未锁定的出库记录抓出
		 * 循环处理，全部锁定
		 * 所有动作处理完毕后，将此closingAccount对象写入数据库中记录下来
		 */
		Date lock_date = closingAccount.getClosing_date();
		try {
			// 先抓取当前所有库存记录
			List<WarehouseIn> warehouseIns = this.warehouseInDAO.findAllUnlocked();
			Iterator<WarehouseIn> iterIn = warehouseIns.iterator();
			// 先生成新的入库记录编号
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			String pre_in_id = "VN" + sdf.format(new Date());
			Long currentIdNum = this.warehouseInDAO.getDayIdNum(pre_in_id + "%");
			Long in_id_num = (long) 1;
			if (iterIn != null) {
				while (iterIn.hasNext()) {
					WarehouseIn warehouseIn = iterIn.next();
					// 锁定原对象
					warehouseIn.setLocked(1);
					warehouseIn.setLock_date(lock_date);
					
					// 将原对象更新到数据库中
					this.warehouseInDAO.doUpdate(warehouseIn);
					
					if(warehouseIn.getSurplus_quantity()!=0) {
						WarehouseIn warehouseIn2 = new WarehouseIn();
						// 将原对象的所有属性COPY新对象中
						BeanUtils.copyProperties(warehouseIn2, warehouseIn);
						// 新对象中将入库数量设置为剩余数量
						warehouseIn2.setQuantity(warehouseIn2.getSurplus_quantity());
						// 新对象中的history_in_id设置为原对象的in_id
						warehouseIn2.setHistory_in_id(warehouseIn.getIn_id());
						// 重新生成新对象的in_id
						String in_id = pre_in_id + String.format("%04d", currentIdNum + in_id_num);
						in_id_num++;
						warehouseIn2.setIn_id(in_id);
						// 将锁定和锁定日期重设
						warehouseIn2.setLocked(0);
						warehouseIn2.setLock_date(null);
						
						// 将新对象建立到数据库中
						this.warehouseInDAO.doCreate(warehouseIn2);
					}
				}
			}
			
			// 抓取所有未锁定的出库记录
			List<WarehouseOut> warehouseOuts = this.warehouseOutDAO.findAllUnlocked();
			Iterator<WarehouseOut> iterOut = warehouseOuts.iterator();
			if (iterOut != null) {
				while (iterOut.hasNext()) {
					WarehouseOut warehouseOut = iterOut.next();
					// 锁定原对象
					warehouseOut.setLocked(1);
					warehouseOut.setLock_date(lock_date);
					
					// 将原对象更新到数据库中
					this.warehouseOutDAO.doUpdate(warehouseOut);
				}
			}
						
			this.closingAccountDAO.doCreate(closingAccount);
		}catch(Exception e) {
			e.printStackTrace();     
	        //就是这一句了, 加上之后抛了异常就能回滚（有这句代码就不需要再手动抛出运行时异常了）
	        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();  
	        return false;
		}
		return true;
	}

	@Override
	public List<ClosingAccount> getAll() throws Exception {
		return this.closingAccountDAO.findAll();
	}

	@Override
	public List<ClosingAccount> getAllSplit(Integer currentPage, Integer lineSize) throws Exception {
		Map<String, Object> map = super.handleParams(null, null, currentPage, lineSize);
		return this.closingAccountDAO.findAllSplit(map);
	}

	@Override
	public Long getAllCount() throws Exception {
		return this.closingAccountDAO.getAllCount();
	}
}

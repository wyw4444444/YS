package com.artegentech.system.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.artegentech.system.dao.IOutListDAO;
import com.artegentech.system.dao.IWarehouseInDAO;
import com.artegentech.system.dao.IWarehouseOutDAO;
import com.artegentech.system.service.IOutListService;
import com.artegentech.system.service.abs.AbstractService;
import com.artegentech.system.vo.OutList;
import com.artegentech.system.vo.WarehouseIn;
import com.artegentech.system.vo.WarehouseOut;

@Service
public class OutListServiceImpl extends AbstractService implements IOutListService {

	private static Logger log = Logger.getLogger(OutListServiceImpl.class);

	@Resource
	IOutListDAO outListDAO;

	@Resource
	IWarehouseInDAO warehouseInDAO;

	@Resource
	IWarehouseOutDAO warehouseOutDAO;

	@Transactional(rollbackFor = Exception.class)
	@Override
	public boolean addStocks(WarehouseOut warehouseOut) {
		try {
			String part_code = warehouseOut.getPart_code();
			List<WarehouseIn> warehouseIns = this.warehouseInDAO.findAllSurplusListByPartCode(part_code);
			Double total_quantity = warehouseOut.getTotal_quantity();
			Double tempQuantity = total_quantity;
			Iterator<WarehouseIn> iter = warehouseIns.iterator();
			if (iter != null) {
				while (iter.hasNext()) {
					WarehouseIn warehouseIn = iter.next();
					Double thisQuantity = warehouseIn.getSurplus_quantity();
					if (Double.doubleToLongBits(thisQuantity) > Double.doubleToLongBits(tempQuantity)) {
						// 如果此笔剩余数量大于未处理完的数量，则说明此笔可以直接处理完，并且此笔也不会出库完
						Double newSusplusQuantity = thisQuantity - tempQuantity;
						warehouseIn.setSurplus_quantity(newSusplusQuantity);
						// 更新入库数据的剩余数量
						this.warehouseInDAO.doUpdate(warehouseIn);

						// 增加出库清单记录
						OutList outList = new OutList();
						outList.setOut_id(warehouseOut.getOut_id());
						outList.setIn_id(warehouseIn.getIn_id());
						outList.setQuantity(tempQuantity);
						outList.setPur_price(warehouseIn.getPrice());
						this.outListDAO.doCreate(outList);

						// 更新未处理完的数量
						tempQuantity = tempQuantity - tempQuantity;
					} else if (Double.doubleToLongBits(thisQuantity) <= Double.doubleToLongBits(tempQuantity)) {
						// 如果此笔剩余数量小于等于未处理完的数量，则说明此笔剩余数量需要归零
						Double newSusplusQuantity = (double) 0;
						warehouseIn.setSurplus_quantity(newSusplusQuantity);
						// 更新入库数据的剩余数量
						this.warehouseInDAO.doUpdate(warehouseIn);

						// 增加出库清单记录
						OutList outList = new OutList();
						outList.setOut_id(warehouseOut.getOut_id());
						outList.setIn_id(warehouseIn.getIn_id());
						outList.setQuantity(thisQuantity); // 将此笔入库记录的剩余数量作为出库清单的此笔数量
						outList.setPur_price(warehouseIn.getPrice());
						this.outListDAO.doCreate(outList);

						// 更新未处理完的数量
						tempQuantity = tempQuantity - thisQuantity;
					}
					if(tempQuantity == 0) {
						// 全部计算完毕后退出循环
						break;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			// 就是这一句了, 加上之后抛了异常就能回滚（有这句代码就不需要再手动抛出运行时异常了）
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return false;
		}
		return true;
	}

	@Override
	public boolean add(OutList outlist) throws Exception {
		return this.outListDAO.doCreate(outlist);
	}

	@Override
	public boolean edit(OutList outlist) throws Exception {
		if (this.outListDAO.findById(outlist.getOutlist_id()) == null) {
			return false;
		} else {
			return this.outListDAO.doUpdate(outlist);
		}
	}

	@Override
	public boolean editPurPriceByInId(String in_id, Double pur_price) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("in_id", in_id);
		map.put("pur_price", pur_price);
		if (this.outListDAO.findAllByInId(in_id).size() != 0) {
			return this.outListDAO.doUpdatePurPriceByInId(map);
		} else {
			return true;
		}
	}

	@Override
	public OutList getById(Integer outlist_id) throws Exception {
		return this.outListDAO.findById(outlist_id);
	}

	@Override
	public List<OutList> getAll() throws Exception {
		return this.outListDAO.findAll();
	}

	@Override
	public List<OutList> getAllSplit(String column, String keyword, Integer currentPage, Integer lineSize) throws Exception {
		Map<String, Object> map = super.handleParams(column, keyword, currentPage, lineSize);
		return this.outListDAO.findAllSplit(map);
	}

	@Override
	public Long getAllCount(String column, String keyword) throws Exception {
		Map<String, Object> map = super.handleParams(column, keyword);
		return this.outListDAO.getAllCount(map);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public boolean recalculateData(String part_code) {
		// 执行重新计算的时候，需要将利润以及总利润都进行计算，purPriceFlag为true则需要将利润重新滚算以便，查询in_id对应的所有出库清单
		log.info("重算入出库记录，参数如下：");
		log.info("part_code : " + part_code);

		try {
			// 将此part_code入库记录中未锁定的所有记录抓出
			List<WarehouseIn> warehouseIns = this.warehouseInDAO.findAllNoLockedByPartCode(part_code);
			// 将此part_code所有未锁定的出库记录抓出，依据出库id排序
			// 同时将每笔出库记录对应的outlist抓出
			List<WarehouseOut> warehouseOuts = this.warehouseOutDAO.findAllNoLockedByPartCode(part_code);
			Iterator<WarehouseOut> iter = warehouseOuts.iterator();
			if (iter != null) {
				while (iter.hasNext()) {
					WarehouseOut warehouseOut = iter.next();
					String out_id = warehouseOut.getOut_id();
					warehouseOut.setOutLists(this.outListDAO.findAllByOutId(out_id));
				}
			}
			// log.info(warehouseOuts);
			// 先将各个数据回归原始状态
			// 入库记录中将surplus_quantity剩余数量设置为入库的数量
			Iterator<WarehouseIn> iterIn = warehouseIns.iterator();
			if (iterIn != null) {
				while (iterIn.hasNext()) {
					WarehouseIn warehouseIn = iterIn.next();
					warehouseIn.setSurplus_quantity(warehouseIn.getQuantity());
				}
			}

			// 开始执行重新计算动作
			// 以出库记录list作为一层循环，入库记录作为二层循环
			Iterator<WarehouseOut> iterOut = warehouseOuts.iterator();
			if (iterOut != null) {
				while (iterOut.hasNext()) {
					WarehouseOut warehouseOut = iterOut.next();
					log.info(warehouseOut);
					if (Double.doubleToLongBits(warehouseOut.getTotal_quantity()) == 0) {
						// 如果此出库记录的数量为0，则说明是被修改过的资料，将outlist设置为null，后面处理的时候将数据表中的资料删除
						warehouseOut.setOutLists(null);
						continue;
					}
					Double total_quantity = warehouseOut.getTotal_quantity();
					Double tempQuantity = total_quantity;
					Integer listNum = 0; // 设置一个outlist的数量记录变量，记录一个warehouseOut需要多少个warehouseIn提供资料
					// 建立一个临时的outlist数组，此warehouseOut的outlist先向此数组中增加，最后再将此数组的值更新到此warehouseOut的outlist数组中，保留原数组中的outlist_id
					List<OutList> tempList = new ArrayList<OutList>();
					Iterator<WarehouseIn> iterWarehouseIn = warehouseIns.iterator();
					if (iterWarehouseIn != null) {
						while (iterWarehouseIn.hasNext()) {
							WarehouseIn warehouseIn = iterWarehouseIn.next();
							Double thisQuantity = warehouseIn.getSurplus_quantity();
							// 只有剩余数量大于0的时候才会向下计算，如果剩余数量等于0，代表此warehouseIn已经被处理完了
							if (Double.doubleToLongBits(thisQuantity) > 0) {
								if (Double.doubleToLongBits(thisQuantity) > Double.doubleToLongBits(tempQuantity)) {
									// 如果此笔剩余数量大于未处理完的数量，则说明此笔可以直接处理完，并且此笔也不会出库完
									Double newSusplusQuantity = thisQuantity - tempQuantity;
									warehouseIn.setSurplus_quantity(newSusplusQuantity);

									// 增加出库清单记录
									OutList outList = new OutList();
									outList.setOut_id(warehouseOut.getOut_id());
									outList.setIn_id(warehouseIn.getIn_id());
									outList.setQuantity(tempQuantity);
									outList.setPur_price(warehouseIn.getPrice());
									log.info(outList);
									tempList.add(outList);
									listNum++;

									// 更新未处理完的数量
									tempQuantity = tempQuantity - tempQuantity;
								} else if (Double.doubleToLongBits(thisQuantity) <= Double
										.doubleToLongBits(tempQuantity)) {
									// 如果此笔剩余数量小于等于未处理完的数量，则说明此笔剩余数量需要归零
									Double newSusplusQuantity = (double) 0;
									warehouseIn.setSurplus_quantity(newSusplusQuantity);

									// 增加出库清单记录
									OutList outList = new OutList();
									outList.setOut_id(warehouseOut.getOut_id());
									outList.setIn_id(warehouseIn.getIn_id());
									outList.setQuantity(thisQuantity); // 将此笔入库记录的剩余数量作为出库清单的此笔数量
									outList.setPur_price(warehouseIn.getPrice());
									log.info(outList);
									tempList.add(outList);
									listNum++;

									// 更新未处理完的数量
									tempQuantity = tempQuantity - thisQuantity;
								}

								if (Double.doubleToLongBits(tempQuantity) == 0) {
									// 剩余的未处理数量为0，说明此warehouseOut的数量已经处理完毕，则跳出warehouseIn循环
									break;
								}
							}
						}
					}
					// 将warehouseOut中的outlist与此循环中生成的tempList进行处理
					// 按照顺序先将tempList中每项内容赋值给outlist
					// 如果实际的项数比outlist中项数多，则多的部分没有outlist_id(null)，最终执行的时候需要add到tbloutlist中，否则update
					// 如果实际的项数比outlist中项数少，则少的部分将outlist中的对应的项删除
					List<OutList> thisSaleLists = warehouseOut.getOutLists();
					// 如果warehouseOut中的outlist为null，则说明tempList中所有的内容都需要新加入
					if (thisSaleLists == null) {
						warehouseOut.setOutLists(tempList);
					} else {
						int lenTemp = tempList.size();
						int lenThis = thisSaleLists.size();

						if (lenTemp == lenThis) {
							// 如果两个数组长度相同，则直接将新数组中每个的值赋值到既有数组中
							for (int i = 0; i < lenTemp; i++) {
								thisSaleLists.get(i).setIn_id(tempList.get(i).getIn_id());
								thisSaleLists.get(i).setQuantity(tempList.get(i).getQuantity());
								thisSaleLists.get(i).setPur_price(tempList.get(i).getPur_price());
							}
							warehouseOut.setOutLists(thisSaleLists);
						} else if (lenTemp > lenThis) {
							// 如果新数组比旧数组长，那么多出来的就要以outlist_id为null的形式增加到旧数组中
							for (int i = 0; i < lenThis; i++) {
								thisSaleLists.get(i).setIn_id(tempList.get(i).getIn_id());
								thisSaleLists.get(i).setQuantity(tempList.get(i).getQuantity());
								thisSaleLists.get(i).setPur_price(tempList.get(i).getPur_price());
							}
							for (int j = lenThis; j < lenTemp; j++) {
								thisSaleLists.add(tempList.get(j));
							}
							warehouseOut.setOutLists(thisSaleLists);
						} else if (lenTemp < lenThis) {
							// 如果新数组比旧数组短，那么旧数组中多出来的部分的sal_id字段设置为null，作为删除数据库数据时的判断条件
							for (int i = 0; i < lenTemp; i++) {
								thisSaleLists.get(i).setIn_id(tempList.get(i).getIn_id());
								thisSaleLists.get(i).setQuantity(tempList.get(i).getQuantity());
								thisSaleLists.get(i).setPur_price(tempList.get(i).getPur_price());
							}
							for (int j = lenTemp; j < lenThis; j++) {
								thisSaleLists.get(j).setOut_id(null);
							}
							warehouseOut.setOutLists(thisSaleLists);
						}
						log.info(warehouseOut.getOutLists());
					}
				}
			}

			// 全部计算完毕后，开始处理写数据库的动作
			// warehouseIns数据直接循环更新数据库
			Iterator<WarehouseIn> iterWarehouseIn2 = warehouseIns.iterator();
			if (iterWarehouseIn2 != null) {
				while (iterWarehouseIn2.hasNext()) {
					WarehouseIn warehouseIn = iterWarehouseIn2.next();
					this.warehouseInDAO.doUpdate(warehouseIn);
				}
			}

			// warehouseOut更新数据库的同时，也需要将其对应的outlist更新入数据库
			Iterator<WarehouseOut> iterWarehouseOut2 = warehouseOuts.iterator();
			if (iterWarehouseOut2 != null) {
				while (iterWarehouseOut2.hasNext()) {
					WarehouseOut warehouseOut = iterWarehouseOut2.next();
					this.warehouseOutDAO.doUpdate(warehouseOut);

					List<OutList> outLists = warehouseOut.getOutLists();
					if (outLists == null) {
						// 如果此warehouseOut的outlists为null，则说明其没有交易清单，故将数据表中sal_id对应的outlist全部删除
						this.outListDAO.doRemoveByOutId(warehouseOut.getOut_id());
					} else {
						Iterator<OutList> iterOutList = outLists.iterator();
						if (iterOutList != null) {
							while (iterOutList.hasNext()) {
								OutList outList = iterOutList.next();
								if (outList.getOut_id() != null) {
									// out_id不为null，说明此outlist需要更新资料
									if (outList.getOutlist_id() != null) {
										this.outListDAO.doUpdate(outList);
									} else {
										this.outListDAO.doCreate(outList);
									}
								} else {
									this.outListDAO.doRemove(outList.getOutlist_id());
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			// 就是这一句了, 加上之后抛了异常就能回滚（有这句代码就不需要再手动抛出运行时异常了）
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return false;
		}
		return true;
	}
	
	@Override
	public List<OutList> getAllByOutIdDESC(String out_id) throws Exception {
		return this.outListDAO.findAllByOutIdDESC(out_id);
	}
}

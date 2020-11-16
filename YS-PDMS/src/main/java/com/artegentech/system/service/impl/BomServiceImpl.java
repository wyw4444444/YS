package com.artegentech.system.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.artegentech.system.dao.IBomDAO;
import com.artegentech.system.dao.IPartInfoDAO;
import com.artegentech.system.service.IBomService;
import com.artegentech.system.service.ICheckLogService;
import com.artegentech.system.service.abs.AbstractService;
import com.artegentech.system.vo.Bom;
import com.artegentech.system.vo.CheckLog;
import com.artegentech.system.vo.PartInfo;

@Service
public class BomServiceImpl extends AbstractService implements IBomService {

	@Resource
	private IBomDAO bomDao;
	
	@Resource
	private ICheckLogService  checkLogService;
	
	@Resource
	private IPartInfoDAO partInfoDao;
	
	@Override
	public boolean addStocks(List<Bom> ltBom,String member_id) throws Exception {
		boolean a=true;
		List<String> ltCode_up=new ArrayList<>();
	
		for (int i=0 ; i<ltBom.size() ; i++){
			if(this.bomDao.doCreate(ltBom.get(i))==false) {
				a=false;
			}
			String part_code_up=ltBom.get(i).getPart_code_up();
	
			boolean bNew=true;
			Iterator<String> iter=ltCode_up.iterator();
			while(iter.hasNext()) {
				String part_code=iter.next();
				if(part_code.equals(part_code_up)) {
					bNew=false;
					break;
				}
				
			}
			if(bNew) {
				int id_check=ltBom.get(i).getId();
				CheckLog checklog=new CheckLog();
				checklog.setType_check("BOM");
				checklog.setId_check(id_check);
				checklog.setReg_time_apply(new Date());
				checklog.setMember_id(member_id);
				checklog.setTips(part_code_up+"-BOM批量建立");
				checklog.setCheck_status(5);
				 this.checkLogService.add(checklog);
				 ltCode_up.add(part_code_up);
				
			}
		}
		return a;
	}
	
	
	@Override
	public boolean add(List<Bom> ltBom,String member_id) throws Exception {
		boolean a=true;
		List<String> ltCode_up=new ArrayList<>();
	
		for (int i=0 ; i<ltBom.size() ; i++){
			if(this.bomDao.doCreate(ltBom.get(i))==false) {
				a=false;
			}
			String part_code_up=ltBom.get(i).getPart_code_up();
	
			boolean bNew=true;
			Iterator<String> iter=ltCode_up.iterator();
			while(iter.hasNext()) {
				String part_code=iter.next();
				if(part_code.equals(part_code_up)) {
					bNew=false;
					break;
				}
				
			}
			if(bNew) {
				int id_check=ltBom.get(i).getId();
				CheckLog checklog=new CheckLog();
				checklog.setType_check("BOM");
				checklog.setId_check(id_check);
				checklog.setReg_time_apply(new Date());
				checklog.setMember_id(member_id);
				checklog.setTips(part_code_up+"-BOM建立申请");
				checklog.setCheck_status(1);
				 this.checkLogService.add(checklog);
				 ltCode_up.add(part_code_up);
				
			}
		}
		return a;
	}
	
	@Override
	public Integer getNewVersion(String part_code_up) throws Exception {
		Integer result=-1;
		Bom bom=this.bomDao.getMaxVersionByPart_code_up(part_code_up);
		if(bom==null) {
			result=0;//不存在此料号BOM
		}else if(bom.getStatus().equals(4)) {
			result=bom.getVer();//前一次料号BOM取消
		}else if(bom.getStatus()==5) {
			result=bom.getVer()+1;
		}else if(bom.getLocked().equals(1)) {
			result=-2;//料号BOM已被废止
		}else if(bom.getStatus().equals(1)||bom.getStatus().equals(2)||bom.getStatus().equals(3)) {
			result=-3;//当前料号BOM正在审核中
		}
		return result;
	}
	
	
	
	@Override
	public List<Bom> getBomById(Integer id)throws Exception {
		Bom bom=this.bomDao.findById(id);
		
		return this.bomDao.getByPart_code_upAndVer(bom);
	}
	
	
	@Override
	public boolean updateUpdate(List<Bom> ltBom,String member_id,String tips) throws Exception {
		boolean a=true;
		for (int i=0 ; i<ltBom.size() ; i++){
			if(this.bomDao.doUpdate(ltBom.get(i))==false) {
				a=false;
			}
		}
		int id_check=ltBom.get(0).getId();
		CheckLog checklog=new CheckLog();
		checklog.setType_check("BOM");
		checklog.setId_check(id_check);
		checklog.setReg_time_apply(new Date());
		checklog.setMember_id(member_id);
		if(tips.equals("")) {
			tips=ltBom.get(0).getPart_code_up()+"-BOM重新发起申请";
		}
		checklog.setTips(tips);  //新
		checklog.setCheck_status(1);
		 this.checkLogService.add(checklog);
		
		return a;
	}
	
	
	@Override
	public boolean updateAdd(List<Bom> ltBom) throws Exception {
		boolean a=true;
		for (int i=0 ; i<ltBom.size() ; i++){
			if(this.bomDao.doCreate(ltBom.get(i))==false) {
				a=false;
			}
		}
		return a;
	}
	
	@Override
	public boolean updateDel(List<Bom> ltBom) throws Exception {
		boolean a=true;
		for (int i=0 ; i<ltBom.size() ; i++){
			if(this.bomDao.doDelete(ltBom.get(i))==false) {
				a=false;
			}
		}
		return a;
	}
	
	public boolean editStatus(Bom bom) throws Exception {
		
		boolean a=true;
		Iterator<Bom> iter=getBomById(bom.getId()).iterator();
		while(iter.hasNext()) {
			Bom old=iter.next();
			
			//取回、退回、发行申请时,只能是待审核状态
			if((bom.getStatus().equals(2)||bom.getStatus().equals(3)||bom.getStatus().equals(5))&& !old.getStatus().equals(1)) {
				return false;
			}
			old.setStatus(bom.getStatus());
			if(this.bomDao.doUpdateStatus(old)==false) {
				a=false;
			}
		}
		return a;
	}
	
	
	@Override
	public List<Bom> getDownSplit(String part_code_up,boolean All) throws Exception{

		List<Bom> result = new ArrayList<Bom>();
		
		List<Bom> validBom = new ArrayList<Bom>();
		Iterator<Bom> iter= All?this.bomDao.findAllPart_code_upAll().iterator():this.bomDao.findAllPart_code_up().iterator();
		while (iter.hasNext()) {
			Bom vo = iter.next();
			String part_code_up1=vo.getPart_code_up();
			
			List<Bom> ltBom=All?this.bomDao.findByPart_code_upAll(part_code_up1):this.bomDao.findByPart_code_up(part_code_up1);
			if(ltBom.size()>0) {
				Bom bom=new Bom();
				bom.setPart_code_up(part_code_up1);
				bom.setLevel(0);
				bom.setVer(ltBom.get(0).getVer());
				bom.setLocked(ltBom.get(0).getLocked());
				bom.setId(ltBom.get(0).getId());
				validBom.add(bom);
			}
			Iterator<Bom> iter1=ltBom.iterator();
			
			while (iter1.hasNext()) {
				Bom vo1 = iter1.next();
				vo1.setId(ltBom.get(0).getId());
				vo1.setLevel(1);
				validBom.add(vo1);
			}			
		}		
		Iterator<Bom> iter2= validBom.iterator();
		while (iter2.hasNext()) {
			Bom vo2 = iter2.next();
			
			String part_code_up2=vo2.getPart_code_up();
			if(part_code_up2.contains(part_code_up)) {
				if(vo2.getLevel().equals(0)) {
					vo2.setPartInfo(this.partInfoDao.findByCode(vo2.getPart_code_up()));
				}
				else if(vo2.getLevel().equals(1)) {
					vo2.setPartInfo(this.partInfoDao.findByCode(vo2.getPart_code_down()));
				}
				result.add(vo2);
			}
			
		}
		return result;

	}
	
	
	@Override
	public List<Bom> getAllDownSplit(String part_code_up) throws Exception{
		List<Bom> result = new ArrayList<Bom>();
		List<Bom> result1 = new ArrayList<Bom>();
		
		List<Bom> validBom = new ArrayList<Bom>();
		Iterator<Bom> iter= this.bomDao.findAllPart_code_up().iterator();
		while (iter.hasNext()) {
			Bom vo = iter.next();
			String part_code_up1=vo.getPart_code_up();
			
			List<Bom> ltBom=this.bomDao.findByPart_code_up(part_code_up1);
			if(ltBom.size()>0) {
				Bom bom=new Bom();
				bom.setPart_code_up(part_code_up1);
				bom.setLevel(0);
				bom.setVer(ltBom.get(0).getVer());
				validBom.add(bom);
			}
			Iterator<Bom> iter1=ltBom.iterator();
			
			while (iter1.hasNext()) {
				Bom vo1 = iter1.next();
				vo1.setLevel(0);
				validBom.add(vo1);
			}			
		}
			
		List<String> ltpart_code_up = new ArrayList<String>();
		
		Iterator<Bom> iter2= validBom.iterator();
		while (iter2.hasNext()) {
			Bom vo2 = iter2.next();
			String part_code_up2=vo2.getPart_code_up();
			if(part_code_up2.contains(part_code_up)) {
				ltpart_code_up.add(part_code_up2);
			}
			
		}
		//java 去除重复
		  HashSet hs = new HashSet(ltpart_code_up);   
		  ltpart_code_up.clear();   
		  ltpart_code_up.addAll(hs);
		  
		Iterator<String> iter3= ltpart_code_up.iterator();
		while(iter3.hasNext()) {
			String part_code=iter3.next();
			result1.addAll(getStepDownSplit(validBom ,part_code,0));
		}
		
		Iterator<Bom> iter4= result1.iterator();
		
		while(iter4.hasNext()) {
			Bom vo4 = iter4.next();
			
			if(vo4.getLevel().equals(0)||vo4.getLevel().equals(10000)) {
				vo4.setPartInfo(this.partInfoDao.findByCode(vo4.getPart_code_up()));
			}
			else{
				vo4.setPartInfo(this.partInfoDao.findByCode(vo4.getPart_code_down()));
			}
			result.add(vo4);
		}
		
		return  result;
	}
	
	
	@Override
	public List<Bom> getStepDownSplit(List<Bom> validBom ,String part_code_up,Integer level) throws Exception{
		List<Bom> result = new ArrayList<Bom>();
		
		Iterator<Bom> iter = validBom.iterator();
		if (iter != null) {
			++level;
			while (iter.hasNext()) {
				Bom vo = iter.next();
				if(vo.getPart_code_up().equals(part_code_up)) {
					
						if(vo.getPart_code_down()!=null) {
							vo.setLevel(level);
						}
						else if(level!=1){
							vo.setLevel(10000);
						}
					result.add(vo);
					List<Bom> result1 = getStepDownSplit(validBom ,vo.getPart_code_down(),level);
					if(result1!=null){
						result.addAll(result1);
					}
				}
			}
			return result;
		}
		return null;
	}
	
	@Override
	public List<Bom> getUpSplit(String part_code_down) throws Exception{
		List<Bom> result = new ArrayList<Bom>();
		
		List<Bom> validBom = new ArrayList<Bom>();
		Iterator<Bom> iter= this.bomDao.findAllPart_code_up().iterator();
		while (iter.hasNext()) {
			Bom vo = iter.next();
			String part_code_up1=vo.getPart_code_up();
			
			List<Bom> ltBom=this.bomDao.findByPart_code_up(part_code_up1);

			Iterator<Bom> iter1=ltBom.iterator();
			
			while (iter1.hasNext()) {
				Bom vo1 = iter1.next();
				vo1.setLevel(0);
				validBom.add(vo1);
			}			
		}
		Iterator<Bom> iter2= validBom.iterator();
		while (iter2.hasNext()) {
			Bom vo2 = iter2.next();
			
			String part_code_down2=vo2.getPart_code_down();
			if(part_code_down2.contains(part_code_down)) {
	
				vo2.setPartInfo(this.partInfoDao.findByCode(vo2.getPart_code_down()));
				result.add(vo2);
				
				Bom bom=new Bom();
				bom.setPart_code_up(vo2.getPart_code_up());
				bom.setLevel(-1);
				bom.setVer(vo2.getVer());
				bom.setPartInfo(this.partInfoDao.findByCode(vo2.getPart_code_up()));
				result.add(bom);
			}
		}
		return result;
	}
	
	
	@Override
	public List<Bom> getDownHistorySplit(String part_code_up) throws Exception{

		List<Bom> result = new ArrayList<Bom>();
		
		List<Bom> validBom = new ArrayList<Bom>();
		Iterator<Bom> iter= this.bomDao.findAllPart_code_up().iterator();
		while (iter.hasNext()) {
			Bom vo = iter.next();
			String part_code_up1=vo.getPart_code_up();
			
			List<Bom> ltBom=this.bomDao.findAllVerByPart_code_up(part_code_up1);
			Integer ver=-1;
			if(ltBom.size()>0) {
				ver=ltBom.get(0).getVer();
				Bom bom=new Bom();
				bom.setPart_code_up(part_code_up1);
				bom.setLevel(0);
				bom.setVer(ver);
				validBom.add(bom);
			}
			Iterator<Bom> iter1=ltBom.iterator();
			while (iter1.hasNext()) {
				Bom vo1 = iter1.next();
				if(!vo1.getVer().equals(ver)) {
					ver=vo1.getVer();
					Bom bom=new Bom();
					bom.setPart_code_up(part_code_up1);
					bom.setLevel(0);
					bom.setVer(ver);
					validBom.add(bom);
				}
				vo1.setLevel(1);
				validBom.add(vo1);
			}			
		}	
		Iterator<Bom> iter2= validBom.iterator();
		while (iter2.hasNext()) {
			Bom vo2 = iter2.next();
			
			String part_code_up2=vo2.getPart_code_up();
			if(part_code_up2.contains(part_code_up)) {
				if(vo2.getLevel().equals(0)) {
					vo2.setPartInfo(this.partInfoDao.findByCode(vo2.getPart_code_up()));
				}
				else if(vo2.getLevel().equals(1)) {
					vo2.setPartInfo(this.partInfoDao.findByCode(vo2.getPart_code_down()));
				}
				result.add(vo2);
			}
			
		}
		return result;
	}
	
	@Override
	public List<Bom> getUpTailSplit(String part_code_down) throws Exception{
		List<Bom> result = new ArrayList<Bom>();
		
		List<Bom> validBom = new ArrayList<Bom>();
		Iterator<Bom> iter= this.bomDao.findAllPart_code_up().iterator();
		while (iter.hasNext()) {
			Bom vo = iter.next();
			String part_code_up1=vo.getPart_code_up();
			
			List<Bom> ltBom=this.bomDao.findByPart_code_up(part_code_up1);

			Iterator<Bom> iter1=ltBom.iterator();
			
			while (iter1.hasNext()) {
				Bom vo1 = iter1.next();
				vo1.setLevel(0);
				validBom.add(vo1);
			}			
		}
		
		List<String> ltpart_code_down = new ArrayList<String>();
		
		Iterator<Bom> iter2= validBom.iterator();
		while (iter2.hasNext()) {
			Bom vo2 = iter2.next();
			String part_code_down2=vo2.getPart_code_down();
			if(part_code_down2.contains(part_code_down)) {
				ltpart_code_down.add(part_code_down2);
			}
		}		
		//java 去除重复
		  HashSet hs = new HashSet(ltpart_code_down);   
		  ltpart_code_down.clear();   
		  ltpart_code_down.addAll(hs);
		
		
		List<Bom> result4 = new ArrayList<Bom>();
		
		Iterator<String> iter3s= ltpart_code_down.iterator();
		while (iter3s.hasNext()) {
			String part_code=iter3s.next();
			Bom bom=new Bom();
			bom.setPart_code_up(part_code);
			bom.setPart_code_down(part_code);
			
			List<Bom> result3 = new ArrayList<Bom>();
			List<Bom> tp=getStepUpSplit(validBom,bom,1);
			Iterator<Bom> itertp= tp.iterator();
			while (itertp.hasNext()) {
				Bom tp3=itertp.next();
				
				boolean bExist=false;
				Iterator<Bom> iter3= result3.iterator();
				while (iter3.hasNext()) {
					Bom vo3=iter3.next();
					if(vo3.getPart_code_up().equals(tp3.getPart_code_up())){
						bExist=true;
						break;
					}
				}
				if(!bExist) {
					result3.add(tp3);
				}
			}
			result4.addAll(result3);
		}
		//有效BOM
		Iterator<Bom> iter4= result4.iterator();
		while (iter4.hasNext()) {
			Bom vo4 = iter4.next();
		
			vo4.setPartInfo(this.partInfoDao.findByCode(vo4.getPart_code_down()));
			vo4.setVer(null);
			result.add(vo4);
			
			Bom bom=new Bom();
			bom.setPart_code_up(vo4.getPart_code_up());
			bom.setLevel(10000);
			bom.setVer(null);
			bom.setPartInfo(this.partInfoDao.findByCode(vo4.getPart_code_up()));
			result.add(bom);
		}
		return result;
	}
	
	
	@Override
	public List<Bom> getStepUpSplit(List<Bom> validBom,Bom bom,Integer level) throws Exception{
		List<Bom> result = new ArrayList<Bom>();
		boolean bEnd=true;
		Iterator<Bom> iter1 = validBom.iterator();
		while (iter1.hasNext()) {
			Bom vo1 = iter1.next();
			if(vo1.getPart_code_down().equals(bom.getPart_code_up())) {
				Bom nbom=new Bom();
				BeanUtils.copyProperties(bom,nbom);
	//			nbom=bom;
				nbom.setPart_code_up(vo1.getPart_code_up());
	//			Float dosage=bom.getDosage()*vo1.getDosage()*vo1.getBase();
	//			nbom.setDosage(dosage);
				bEnd=false;
		
				//继续往下找
				List<Bom> result1 = getStepUpSplit(validBom,nbom,++level);
				if(result1!=null){
					result.addAll(result1);
				}
			}
		}
		//结束,加入result
		if(bEnd==true) {
			result.add(bom);
		}
		return result;
	}
	
	
	public boolean editLock(Bom bom) throws Exception {
			return this.bomDao.doUpdateLock(bom);
	}
	
	
}

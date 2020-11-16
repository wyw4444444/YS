package com.artegentech.system.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.artegentech.system.dao.IPartDAO;
import com.artegentech.system.service.IPartService;
import com.artegentech.system.service.abs.AbstractService;
import com.artegentech.system.vo.Part;

@Service
public class PartServiceImpl extends AbstractService implements IPartService {

	@Resource
	private IPartDAO partdao;

	@Override
	public Part checkPartCode(Part part) throws Exception {
		System.out.println(part.getPart_code());
//		if (this.partdao.checkPartCode(part.getPart_code()) == null) {
//			return false;
//		}
		return this.partdao.checkPartCode(part.getPart_code());
	}
	@Override
	public Part getPartinfo(Part part) throws Exception {
		System.out.println(part.getPart_code());
//		if (this.partdao.checkPartCode(part.getPart_code()) == null) {
//			return false;
//		}
		return part;
	}

}

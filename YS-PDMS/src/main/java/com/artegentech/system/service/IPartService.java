package com.artegentech.system.service;

import java.util.List;

import com.artegentech.system.vo.Part;

public interface IPartService {
	/**
	 * 增加部門
	 * 
	 * @param Part
	 * @return
	 * @throws Exception
	 */
	public Part checkPartCode(Part Part) throws Exception;

	public Part getPartinfo(Part part) throws Exception;

}

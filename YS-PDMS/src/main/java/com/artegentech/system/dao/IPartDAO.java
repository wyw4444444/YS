package com.artegentech.system.dao;

import java.util.List;

import com.artegentech.system.vo.Part;

public interface IPartDAO extends IDAO<Integer, Part> {
	/**
	 * 檢查part_code是否存在
	 * 

	 */
	public Part checkPartCode(String part_code) throws Exception;

}

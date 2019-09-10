/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.acout.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.acout.entity.Save;
import com.thinkgem.jeesite.modules.acout.dao.SaveDao;

/**
 * 商品Service
 * @author 鄢嘉骏
 * @version 2019-09-10
 */
@Service
@Transactional(readOnly = true)
public class SaveService extends CrudService<SaveDao, Save> {

	public Save get(String id) {
		return super.get(id);
	}
	
	public List<Save> findList(Save save) {
		return super.findList(save);
	}
	
	public Page<Save> findPage(Page<Save> page, Save save) {
		return super.findPage(page, save);
	}
	
	@Transactional(readOnly = false)
	public void save(Save save) {
		super.save(save);
	}
	
	@Transactional(readOnly = false)
	public void delete(Save save) {
		super.delete(save);
	}
	
}
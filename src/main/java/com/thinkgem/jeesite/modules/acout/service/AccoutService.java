/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.acout.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.acout.entity.Accout;
import com.thinkgem.jeesite.modules.acout.dao.AccoutDao;

/**
 * 订单Service
 * @author 鄢嘉骏
 * @version 2019-09-10
 */
@Service
@Transactional(readOnly = true)
public class AccoutService extends CrudService<AccoutDao, Accout> {

	public Accout get(String id) {
		return super.get(id);
	}
	
	public List<Accout> findList(Accout accout) {
		return super.findList(accout);
	}
	
	public Page<Accout> findPage(Page<Accout> page, Accout accout) {
		return super.findPage(page, accout);
	}
	
	@Transactional(readOnly = false)
	public void save(Accout accout) {
		super.save(accout);
	}
	
	@Transactional(readOnly = false)
	public void delete(Accout accout) {
		super.delete(accout);
	}
	
}
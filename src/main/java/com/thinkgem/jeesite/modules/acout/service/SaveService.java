/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.acout.service;

import java.util.List;

import com.thinkgem.jeesite.modules.acout.dao.TypeDao;
import com.thinkgem.jeesite.modules.acout.entity.Type;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
		return dao.findList(save);
	}
	
	public Page<Save> findPage(Page<Save> page, Save save) {
		save.setPage(page);
		List<Save> list=dao.findList(save);
		User thisUser=UserUtils.getUser();//获得当前用户
		for (Save item:list) {
			item.setUser(thisUser);
			Save save1=dao.findDatail(item);
			if(save1!=null)
			{
				item.setCheckedCount(save1.getCheckedCount());
			}
			else
			{
				item.setCheckedCount("0");
			}
//			item.setRestCount(save1.getRestCount());//查询库存里的该商品数量
		}
		page.setList(list);
		return page;
	}
	
	@Transactional(readOnly = false)
	public void save(Save save) {
		super.save(save);
	}
	
	@Transactional(readOnly = false)
	public void delete(Save save) {
		super.delete(save);
	}

	@Autowired
	TypeDao typeDao;

	public Page<Type> saveList(Page<Type> page,Type type) {
		type.setPage(page);
		 page.setList(typeDao.findSaveList(type));
		 return page;
	}
}
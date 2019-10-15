/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.acout.service;

import java.util.ArrayList;
import java.util.List;

import com.thinkgem.jeesite.common.utils.IdGen;
import com.thinkgem.jeesite.modules.acout.entity.AccoutList;
import com.thinkgem.jeesite.modules.acout.entity.Save;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.apache.shiro.authc.Account;
import org.springframework.beans.factory.annotation.Autowired;
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

	//查看购物车
	public Page<Accout> findMyCarPage(Page<Accout> page, Accout accout) {
		accout.setPage(page);
		accout.setUser(UserUtils.getUser());
		accout.setStatus("0");
		List<Accout> list=dao.findMyCarList(accout);
		for(Accout item:list)//将每一个订单都处理好
		{
			item.setAccoutGroup(null);
//			if(accout.getRemarks()!=null&&!accout.getRemarks().equals("")){}
			item.setRemarks(accout.getRemarks());
			item.setSaveList(dao.findMyCarListByGroup(item));
		}
		page.setList(getFinalPrice(list));
		return page;
	}

	//查看以往订单
	public Page<Accout> findMyAccountPage(Page<Accout> page, Accout accout) {
		accout.setPage(page);
		accout.setUser(UserUtils.getUser());
		accout.setStatus("1");
		List<Accout> list=dao.findMyCarList(accout);
		for(Accout item:list)
		{
			item.setRemarks(accout.getRemarks());
			item.setSaveList(dao.findHistoryByGroup(item));
		}
		page.setList(getFinalPrice(list));
		return page;
	}
	//查看所有订单
	public Page<Accout> findAllAccount(Page<Accout> page, Accout accout) {
		accout.setPage(page);
//		accout.setUser(UserUtils.getUser());
		accout.setStatus("3");
		List<Accout> list=dao.findMyCarList(accout);
		for(Accout item:list)
		{
			item.setRemarks(accout.getRemarks());
			item.setSaveList(dao.findHistoryByGroup(item));
		}
		page.setList(getFinalPrice(list));
		return page;
	}
	//ajax提交生成初始数据
	@Transactional(readOnly = false)
	public void addNewAccount(String saveId,String count) {
		Accout accout=new Accout();
		accout.setSaveId(saveId);
		accout.setCount(count);
		accout.preInsert();
		addToCar(accout);
	}
	//添加购物车
	@Transactional(readOnly = false)
	public void addToCar(Accout accout) {
		accout.setStatus("0");//加入购物车
		accout.setUser(UserUtils.getUser());
		dao.insert(accout);
	}
	//删除某种商品BySaveId
	@Transactional(readOnly = false)
	public void clearAccountById(String saveId) {
		dao.clearAccountById(saveId);
	}
	//提交订单I
	@Transactional(readOnly = false)
	public void addMyCarList(List<Save> list) {
		//便于处理 先删除所有的商品 再重新建订单;
		String groupId=IdGen.uuid();//设置组Id
		for(Save item:list)
		{
			clearAccountById(item.getId());
			Accout accout=new Accout();
			accout.setSaveId(item.getId());
			accout.setCount(item.getCount());
			accout.preInsert();
			accout.setAccoutGroup(groupId);
			accout.setStatus("1");//提交订单
			accout.setUser(UserUtils.getUser());
			dao.insert(accout);

			Save save=saveService.get(item.getId());//修改商品余量
			String count=Integer.toString(Integer.parseInt(save.getCount())-Integer.parseInt(accout.getCount()));
			save.setCount(count);
			saveService.save(save);
		}
	}
	@Autowired
	SaveService saveService;
	//确认订单
	@Transactional(readOnly = false)
	public void confirmAccount(Accout accout) {
		String groupId=accout.getAccoutGroup();//设置组Id
		List<Accout> list=dao.findGroupById(groupId);
		for (int i=0;i<list.size();i++)
		{
			Accout item=get(list.get(i).getId());
			item.setStatus("2");//确认订单
			item.preUpdate();
			super.save(item);
		}
	}

	//	计算总价
	public List<Accout> getFinalPrice(List<Accout> list) {
		for (int i=0;i<list.size();i++)
		{
			float sum=0;
			for (int j=0;j<list.get(i).getSaveList().size();j++)
			{
				sum=sum+Float.parseFloat(list.get(i).getSaveList().get(j).getCount())*Float.parseFloat(list.get(i).getSaveList().get(j).getPrice());
			}
			list.get(i).setCount(Float.toString(sum));
		}
		return list;
	}

}
/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.acout.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.acout.entity.Accout;
import com.thinkgem.jeesite.modules.acout.entity.Save;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 订单DAO接口
 * @author 鄢嘉骏
 * @version 2019-09-10
 */
@MyBatisDao
public interface AccoutDao extends CrudDao<Accout> {
    List<Accout> findGroupById(String id);
    List<Accout> findMyCarList(Accout accout);
    List<Save>   findMyCarListByGroup(Accout accout);
    List<Save>   findHistoryByGroup(Accout accout);
    void clearAccountById(String saveId);

}
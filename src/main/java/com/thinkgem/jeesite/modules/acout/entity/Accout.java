/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.acout.entity;

import com.thinkgem.jeesite.modules.sys.entity.User;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 订单Entity
 * @author 鄢嘉骏
 * @version 2019-09-10
 */
public class Accout extends DataEntity<Accout> {
	
	private static final long serialVersionUID = 1L;
	private User user;		// 归属用户
	private String status;		// 状态0购物车
	private String count;		// 数量
	private String accoutGroup;		// 订单组的归属
	private String saveId;		// 商品id
	
	public Accout() {
		super();
	}

	public Accout(String id){
		super(id);
	}

	@NotNull(message="归属用户不能为空")
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	@Length(min=1, max=1, message="状态0购物车长度必须介于 1 和 1 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@Length(min=1, max=14, message="数量长度必须介于 1 和 14 之间")
	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}
	
	@Length(min=1, max=255, message="订单组的归属长度必须介于 1 和 255 之间")
	public String getAccoutGroup() {
		return accoutGroup;
	}

	public void setAccoutGroup(String accoutGroup) {
		this.accoutGroup = accoutGroup;
	}
	
	@Length(min=1, max=255, message="商品id长度必须介于 1 和 255 之间")
	public String getSaveId() {
		return saveId;
	}

	public void setSaveId(String saveId) {
		this.saveId = saveId;
	}
	
}
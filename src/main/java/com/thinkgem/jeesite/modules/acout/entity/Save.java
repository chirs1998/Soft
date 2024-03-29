/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.acout.entity;

import com.thinkgem.jeesite.modules.sys.entity.User;
import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 商品Entity
 * @author 鄢嘉骏
 * @version 2019-09-10
 */
public class Save extends DataEntity<Save> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 商品名称
	private String imgUrl;		// 图片地址
	private String price;		// price
	private String count;		// 数量
	private Type type;			//种类
	private String checkedCount;//已选数量
	private String restCount;//已选数量
	private User user;//当前用户

	public Save() {
		super();
	}

	public Save(String id){
		super(id);
	}

	@Length(min=0, max=255, message="商品名称长度必须介于 0 和 255 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=255, message="图片地址长度必须介于 0 和 255 之间")
	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	
	@Length(min=1, max=10, message="price长度必须介于 1 和 10 之间")
	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}
	
	@Length(min=0, max=255, message="数量长度必须介于 0 和 255 之间")
	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public String getCheckedCount() {
		return checkedCount;
	}

	public void setCheckedCount(String checkedCount) {
		this.checkedCount = checkedCount;
	}

	public String getRestCount() {
		return restCount;
	}

	public void setRestCount(String restCount) {
		this.restCount = restCount;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.acout.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinkgem.jeesite.modules.acout.entity.Save;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.acout.entity.Accout;
import com.thinkgem.jeesite.modules.acout.service.AccoutService;

import java.util.List;

/**
 * 商品种类Controller
 * @author 鄢嘉骏
 * @version 2019-10-02
 */
@Controller
@RequestMapping(value = "${adminPath}/acout/accout")
public class AccoutController extends BaseController {

	@Autowired
	private AccoutService accoutService;
	
	@ModelAttribute
	public Accout get(@RequestParam(required=false) String id) {
		Accout entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = accoutService.get(id);
		}
		if (entity == null){
			entity = new Accout();
		}
		return entity;
	}
	
	@RequiresPermissions("acout:accout:view")
	@RequestMapping(value = {"list", ""})
	public String list(Accout accout, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Accout> page = accoutService.findPage(new Page<Accout>(request, response), accout); 
		model.addAttribute("page", page);
		return "modules/acout/accoutList";
	}

	@RequiresPermissions("acout:accout:view")
	@RequestMapping(value = "form")
	public String form(Accout accout, Model model) {
		model.addAttribute("accout", accout);
		return "modules/acout/accoutForm";
	}

	@RequiresPermissions("acout:accout:edit")
	@RequestMapping(value = "save")
	public String save(Accout accout, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, accout)){
			return form(accout, model);
		}
		accoutService.save(accout);
		addMessage(redirectAttributes, "保存商品种类成功");
		return "redirect:"+Global.getAdminPath()+"/acout/accout/?repage";
	}
	
	@RequiresPermissions("acout:accout:edit")
	@RequestMapping(value = "delete")
	public String delete(Accout accout, RedirectAttributes redirectAttributes) {
		accoutService.delete(accout);
		addMessage(redirectAttributes, "删除商品种类成功");
		return "redirect:"+Global.getAdminPath()+"/acout/accout/?repage";
	}
	//查看购物车
	@RequestMapping(value = "findMyCar")
	public String findMyCar(Accout accout,  HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Accout> page = accoutService.findMyCarPage(new Page<Accout>(request, response), accout);
		model.addAttribute("page", page);
		return "modules/acout/accoutList";
	}
	//查看所有订单
	@RequestMapping(value = "findMyAccount")
	public String findMyAccount(Accout accout,  HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Accout> page = accoutService.findMyAccountPage(new Page<Accout>(request, response), accout);
		model.addAttribute("page", page);
		return "modules/acout/historyList";
	}

	//添加商品进购物车
	@ResponseBody
	@RequestMapping(value = "addNewAccount")
	public String addNewAccount(String saveId,String count) {
		accoutService.addNewAccount( saveId, count);
		return "OK";
	}

	//删除某一种商品
	@ResponseBody
	@RequestMapping(value = "clearAccountById")
	public String clearAccountById(String saveId) {
		accoutService.clearAccountById( saveId);
		return "OK";
	}
	//提交购物车
	@ResponseBody
	@RequestMapping(value = "addMyCarList")
	public String addMyCarList(@RequestBody List<Save> list) {
		accoutService.addMyCarList(list);
		return "OK";
	}

	//订单管理界面
	@RequestMapping(value = "manageAccount")
	public String manageAccount(Accout accout,  HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Accout> page = accoutService.findAllAccount(new Page<Accout>(request, response), accout);
		model.addAttribute("page", page);
		return "modules/acout/allHistoryList";
	}
	//完成订单
	@RequestMapping(value = "finishAccout")
	public String finishAccout(Accout accout,  HttpServletRequest request, HttpServletResponse response, Model model) {
		accoutService.confirmAccount(accout);
//		Page<Accout> page = accoutService.findAllAccount(new Page<Accout>(request, response), accout);
//		model.addAttribute("page", page);
		return "modules/acout/allHistoryList";
	}
}
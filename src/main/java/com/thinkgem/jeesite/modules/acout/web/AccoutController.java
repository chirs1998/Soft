/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.acout.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.acout.entity.Accout;
import com.thinkgem.jeesite.modules.acout.service.AccoutService;

/**
 * 订单Controller
 * @author 鄢嘉骏
 * @version 2019-09-10
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
		addMessage(redirectAttributes, "保存订单成功");
		return "redirect:"+Global.getAdminPath()+"/acout/accout/?repage";
	}
	
	@RequiresPermissions("acout:accout:edit")
	@RequestMapping(value = "delete")
	public String delete(Accout accout, RedirectAttributes redirectAttributes) {
		accoutService.delete(accout);
		addMessage(redirectAttributes, "删除订单成功");
		return "redirect:"+Global.getAdminPath()+"/acout/accout/?repage";
	}

	@RequiresPermissions("acout:accout:detail")
	@RequestMapping(value = "accountDetail")
	public String accountDetail(Accout accout, RedirectAttributes redirectAttributes) {
		accoutService.delete(accout);
		addMessage(redirectAttributes, "删除订单成功");
		return "modules/acout/accountDetail";
	}


}
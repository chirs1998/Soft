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
import com.thinkgem.jeesite.modules.acout.entity.Save;
import com.thinkgem.jeesite.modules.acout.service.SaveService;

/**
 * 商品Controller
 * @author 鄢嘉骏
 * @version 2019-09-10
 */
@Controller
@RequestMapping(value = "${adminPath}/acout/save")
public class SaveController extends BaseController {

	@Autowired
	private SaveService saveService;
	
	@ModelAttribute
	public Save get(@RequestParam(required=false) String id) {
		Save entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = saveService.get(id);
		}
		if (entity == null){
			entity = new Save();
		}
		return entity;
	}
	
	@RequiresPermissions("acout:save:view")
	@RequestMapping(value = {"list", ""})
	public String list(Save save, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Save> page = saveService.findPage(new Page<Save>(request, response), save); 
		model.addAttribute("page", page);
		return "modules/acout/saveList";
	}

	@RequiresPermissions("acout:save:view")
	@RequestMapping(value = "form")
	public String form(Save save, Model model) {
		model.addAttribute("save", save);
		return "modules/acout/saveForm";
	}

	@RequiresPermissions("acout:save:edit")
	@RequestMapping(value = "save")
	public String save(Save save, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, save)){
			return form(save, model);
		}
		saveService.save(save);
		addMessage(redirectAttributes, "保存商品成功");
		return "redirect:"+Global.getAdminPath()+"/acout/save/?repage";
	}
	
	@RequiresPermissions("acout:save:edit")
	@RequestMapping(value = "delete")
	public String delete(Save save, RedirectAttributes redirectAttributes) {
		saveService.delete(save);
		addMessage(redirectAttributes, "删除商品成功");
		return "redirect:"+Global.getAdminPath()+"/acout/save/?repage";
	}

}
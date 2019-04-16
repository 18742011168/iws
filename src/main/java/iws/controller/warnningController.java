package iws.controller;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import iws.beans.warnning;
import iws.service.warnningService;

@Controller
public class warnningController {
	
	@Autowired
	private warnningService warnningservice;
	@RequestMapping(value="/iws/warnning")
	@RequiresPermissions("querywarnning")
	public String newwarnning() {
		return "warnning";
	}
	
	@RequestMapping(value="/iws/warnning/all")
	@RequiresPermissions("querywarnning")
	public String allwarnning(Model model) {
		List<warnning> warnninglist=warnningservice.allwaring();
		model.addAttribute("warnninglist",warnninglist);
		return "warnning";
	}

}

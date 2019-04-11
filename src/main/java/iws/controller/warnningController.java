package iws.controller;

import java.util.List;

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
	@RequestMapping(value="/iws/manager/warnning")
	public String newwarnning() {
		return "manager_warnning";
	}
	
	@RequestMapping(value="/iws/manager/warnning/all")
	public String allwarnning(Model model) {
		List<warnning> warnninglist=warnningservice.allwaring();
		model.addAttribute("warnninglist",warnninglist);
		return "manager_warnning";
	}

}

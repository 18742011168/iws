package iws.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import iws.service.changeOrderService;
import iws.service.goodsService;
import iws.service.inOrderService;
import iws.service.outOrderService;
import iws.service.userService;
import iws.service.wareHouseService;
import iws.service.warnningService;

@Controller
public class totalController {

	@Autowired
	private userService userservice;
	
	@Autowired
	private goodsService goodsservice;
	
	@Autowired
	private outOrderService outorderservice;
	
	@Autowired
	private inOrderService inorderservice;
	
	@Autowired
	private changeOrderService changeorderservice;
	
	@Autowired
	private wareHouseService warehouseservice;
	
	@Autowired
	private warnningService warnningservice;
	
	
	@RequestMapping("/iws")
	public String total(Model model) {
		int usernumber=userservice.usernumber();
		int goodsnumner=goodsservice.goodsnumber();
		int warehousenumber=warehouseservice.warehousenumber();
		int warnningnumber=warnningservice.warnningnumber();
		int outordernumber=outorderservice.outordernumber();
		int inordernumber=inorderservice.inordernumber();
		int changeordernumber=changeorderservice.changeordernumber();
		int ordernumber=outordernumber+inordernumber+changeordernumber;
		model.addAttribute("usernumber",usernumber);
		model.addAttribute("goodsnumner",goodsnumner);
		model.addAttribute("warehousenumber",warehousenumber);
		model.addAttribute("warnningnumber",warnningnumber);
		model.addAttribute("outordernumber",outordernumber);
		model.addAttribute("inordernumber",inordernumber);
		model.addAttribute("changeordernumber",changeordernumber);
		model.addAttribute("ordernumber",ordernumber);
		
		return "total";
	}
}

package iws.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

//import iws.beans.inOrder;
import iws.beans.outOrder;
import iws.service.changeOrderService;
//import iws.service.inOrderService;
import iws.service.outOrderService;

@Controller
public class orderController {
	
	@Autowired
	private changeOrderService changeorderservice;
	
	@Autowired
	private outOrderService outorderservice;
	
	@RequestMapping(value="/allchangeorder")
	public String allorder(Model model) {
		model.addAttribute("orders",changeorderservice.allchangeorder());
		return "allorder";
	}
	
	@RequestMapping(value="/outorder/addoutorder")
	public String addinorder(@ModelAttribute("outOrder") outOrder outorder ) {
		int result=outorderservice.addoutorder(outorder);
		if(result==1) 
			System.out.println("插入成功");
		else
			System.out.println("插入失败");
		return "hello";
	}
	
	@RequestMapping(value="/outorder/deleteoutorder")
	public String deleteoutorder(String orderId) {
		int result=outorderservice.deleteoutorder(orderId);
		if(result==1) 
			System.out.println("删除成功");
		else
			System.out.println("删除失败");
		return "hello";
	}
	
     
}

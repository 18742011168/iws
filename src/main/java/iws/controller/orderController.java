package iws.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import iws.beans.inOrder;
import iws.service.changeOrderService;
import iws.service.inOrderService;

@Controller
public class orderController {
	
	@Autowired
	private changeOrderService changeorderservice;
	
	@Autowired
	private inOrderService inorderdervice;
	
	@RequestMapping(value="/allchangeorder")
	public String allorder(Model model) {
		model.addAttribute("orders",changeorderservice.allchangeorder());
		return "allorder";
	}
	
	@RequestMapping(value="/inorder/addinorder")
	public String addinorder(@ModelAttribute("inOrder") inOrder inorder ) {
		if(inorderdervice.addinorder(inorder))
			System.out.println("插入成功");
		return "hello";
	}

}

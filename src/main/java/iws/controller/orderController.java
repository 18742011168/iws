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
	private inOrderService inorderservice;
	
	@RequestMapping(value="/allchangeorder")
	public String allorder(Model model) {
		model.addAttribute("orders",changeorderservice.allchangeorder());
		return "allorder";
	}
	
	@RequestMapping(value="/inorder/addinorder")
	public String addinorder(@ModelAttribute("inOrder") inOrder inorder ) {
		int result=inorderservice.addinorder(inorder);
		if(result==1) 
			System.out.println("插入成功");
		else
			System.out.println("插入失败");
		return "hello";
	}
	
	@RequestMapping(value="/inorder/deleteinorder")
	public String deleteinorder(String orderId) {
		int result=inorderservice.deleteinorder(orderId);
		if(result==1) 
			System.out.println("删除成功");
		else
			System.out.println("删除失败");
		return "hello";
	}
	
     
}

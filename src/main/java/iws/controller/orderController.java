package iws.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import iws.beans.changeOrder;
import iws.beans.goods;
import iws.beans.inOrder;
//import iws.beans.inOrder;
import iws.beans.outOrder;
import iws.service.changeOrderService;
import iws.service.goodsService;
import iws.service.inOrderService;
//import iws.service.inOrderService;
import iws.service.outOrderService;

@Controller
public class orderController {
	
	@Autowired
	private changeOrderService changeorderservice;
	
	@Autowired
	private outOrderService outorderservice;
	
	@Autowired
	private goodsService goodsservice;
	
	@Autowired
	private inOrderService inorderservice;
	
	
	/*
	@RequestMapping(value="/allchangeorder")
	public String allorder(Model model) {
		model.addAttribute("orders",changeorderservice.allchangeorder());
		return "allorder";
	}
	*/
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
	
	@RequestMapping(value= {"/iws/manager/order"})
	public String allorder(Model model) {
		List<outOrder> outorderlist=outorderservice.alloutorder();
		List<inOrder> inorderlist=inorderservice.allinorder();
		List<changeOrder> changeorderlist=changeorderservice.allchangeorder();
		
		model.addAttribute("outorders",outorderlist);
		model.addAttribute("inorders",inorderlist);
		model.addAttribute("changeorders",changeorderlist);
		return "manager_order";
	}
	@GetMapping(value="/iws/manager/order/goods/{orderId}")
	public String order_goods(@PathVariable("orderId") String orderId,Model model) {
		System.out.println(orderId);
		List<goods> goodslist=goodsservice.findbyorder(orderId);
		model.addAttribute("goodslist",goodslist);
		return "manager_order_goods";
		/*
		if(!goodslist.isEmpty()) {
			String meg="hello";
			model.addAttribute("meg",meg);
			return "hello";
		}
		else
		{
			String meg="error";
			model.addAttribute("meg",meg);
			return "error";
		}
		*/	
	}
	
	
	
     
}

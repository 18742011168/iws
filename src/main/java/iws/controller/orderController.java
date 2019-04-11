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
	@GetMapping(value= {"/iws/manager/order/goods/{orderId}"})
	public String order_goods(@PathVariable("orderId") String orderId,Model model) {
		System.out.println(orderId);
		List<goods> goodslist=goodsservice.findbyorder(orderId);
		String message="订单 "+orderId+"包含的货物";
		model.addAttribute("message",message);
		model.addAttribute("goodslist",goodslist);
		return "order_warehouse_goods";
		
	}
	
	@RequestMapping(value= {"/iws/godownner/order"})
	public String allorder2(Model model) {
		List<outOrder> outorderlist=outorderservice.alloutorder();
		List<inOrder> inorderlist=inorderservice.allinorder();
		List<changeOrder> changeorderlist=changeorderservice.allchangeorder();
		
		model.addAttribute("outorders",outorderlist);
		model.addAttribute("inorders",inorderlist);
		model.addAttribute("changeorders",changeorderlist);
		return "godownner_order";
	}
	
	@GetMapping(value= {"/iws/godownner/order/goods/{orderId}"})
	public String order_goods2(@PathVariable("orderId") String orderId,Model model) {
		System.out.println(orderId);
		List<goods> goodslist=goodsservice.findbyorder(orderId);
		String message="订单 "+orderId+"包含的货物";
		model.addAttribute("message",message);
		model.addAttribute("goodslist",goodslist);
		return "godownner_order_goods";
		
	}
	
	@GetMapping(value= {"/iws/godownner/order/begin/{orderId}"})
	public String beginorder(@PathVariable("orderId") String orderId,Model model) {
		
		// service 层update()是相同的，所以用三个orderservice中的任意一个即可
		changeorderservice.updatechangeorder(orderId,"执行中");
		String message="订单 "+ orderId+" 开始执行";
		List<outOrder> outorderlist=outorderservice.alloutorder();
		List<inOrder> inorderlist=inorderservice.allinorder();
		List<changeOrder> changeorderlist=changeorderservice.allchangeorder();
		model.addAttribute("outorders",outorderlist);
		model.addAttribute("inorders",inorderlist);
		model.addAttribute("changeorders",changeorderlist);
		model.addAttribute("message",message);
		return "godownner_order";
	}
	
	@GetMapping(value= {"/iws/godownner/order/complete/{orderId}"})
	public String completeorder(@PathVariable("orderId") String orderId,Model model) {
		changeorderservice.updatechangeorder(orderId,"已完成");
		String message="订单 "+ orderId+" 完成";
		List<outOrder> outorderlist=outorderservice.alloutorder();
		List<inOrder> inorderlist=inorderservice.allinorder();
		List<changeOrder> changeorderlist=changeorderservice.allchangeorder();
		model.addAttribute("outorders",outorderlist);
		model.addAttribute("inorders",inorderlist);
		model.addAttribute("changeorders",changeorderlist);
		model.addAttribute("message",message);
		return "godownner_order";
	}
	
     
}

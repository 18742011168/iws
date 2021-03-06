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
	
	
	
	
	
	@RequestMapping(value= {"/iws/manager/order"})
	public String manager_allorder(Model model) {
		List<outOrder> outorderlist=outorderservice.alloutorder();
		List<inOrder> inorderlist=inorderservice.allinorder();
		List<changeOrder> changeorderlist=changeorderservice.allchangeorder();
		
		model.addAttribute("outorders",outorderlist);
		model.addAttribute("inorders",inorderlist);
		model.addAttribute("changeorders",changeorderlist);
		return "manager_order";
	}
	@GetMapping(value= {"/iws/manager/order/goods/{orderId}"})
	public String manager_order_goods(@PathVariable("orderId") String orderId,Model model) {
		System.out.println(orderId);
		List<goods> goodslist=goodsservice.findbyorder(orderId);
		String message="订单 "+orderId+"包含的货物";
		model.addAttribute("message",message);
		model.addAttribute("goodslist",goodslist);
		return "manager_order_warehouse_goods";
		
	}
	
	@RequestMapping(value= {"/iws/godownner/order"})
	public String godownner_allorder(Model model) {
		List<outOrder> outorderlist=outorderservice.alloutorder();
		List<inOrder> inorderlist=inorderservice.allinorder();
		List<changeOrder> changeorderlist=changeorderservice.allchangeorder();
		
		model.addAttribute("outorders",outorderlist);
		model.addAttribute("inorders",inorderlist);
		model.addAttribute("changeorders",changeorderlist);
		return "godownner_order";
	}
	
	@GetMapping(value= {"/iws/godownner/order/goods/{orderId}"})
	public String godownner_order_goods(@PathVariable("orderId") String orderId,Model model) {
		System.out.println(orderId);
		List<goods> goodslist=goodsservice.findbyorder(orderId);
		String message="订单 "+orderId+"包含的货物";
		model.addAttribute("message",message);
		model.addAttribute("goodslist",goodslist);
		model.addAttribute("orderId",orderId);
		return "godownner_order_goods";
		
	}
	
	@GetMapping(value= {"/iws/godownner/order/begin/{orderId}"})
	public String beginorder(@PathVariable("orderId") String orderId,Model model) {
		
		// service 层update()是相同的，所以用三个orderservice中的任意一个即可
		int result=changeorderservice.updatechangeorder(orderId,"执行中");
		String message="";
		switch(result) {
		case -1:
			message="订单 "+ orderId+" 不存在";
			break;
		case -2:
			message="订单 "+ orderId+" 已完成，不可开始";
			break;
		case 0:
			message="订单 "+ orderId+" 开始失败";
			break;
		default:
			message="订单 "+ orderId+" 执行中";
			break;
		}
		
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
		// service 层update()是相同的，所以用三个orderservice中的任意一个即可
		int result=changeorderservice.updatechangeorder(orderId,"已完成");
		String message="";
		switch(result) {
		case -1:
			message="订单 "+ orderId+" 不存在";
			break;
		case -3:
			message="订单 "+ orderId+" 中有货物在运输中，不可结束";
			break;
		case 0:
			message="订单 "+ orderId+" 结束失败";
			break;
		default:
			message="订单 "+ orderId+" 已完成";
			break;

		}
		
		List<outOrder> outorderlist=outorderservice.alloutorder();
		List<inOrder> inorderlist=inorderservice.allinorder();
		List<changeOrder> changeorderlist=changeorderservice.allchangeorder();
		model.addAttribute("outorders",outorderlist);
		model.addAttribute("inorders",inorderlist);
		model.addAttribute("changeorders",changeorderlist);
		model.addAttribute("message",message);
		return "godownner_order";
	}
	
	@RequestMapping(value= {"/iws/finance/order"})
	public String finance_allorder(Model model) {
		List<outOrder> outorderlist=outorderservice.alloutorder();
		List<inOrder> inorderlist=inorderservice.allinorder();
		List<changeOrder> changeorderlist=changeorderservice.allchangeorder();
		
		model.addAttribute("outorders",outorderlist);
		model.addAttribute("inorders",inorderlist);
		model.addAttribute("changeorders",changeorderlist);
		return "finance_order";
	}
	
	@GetMapping(value= {"/iws/finance/order/goods/{orderId}"})
	public String finance_order_goods(@PathVariable("orderId") String orderId,Model model) {
		System.out.println(orderId);
		List<goods> goodslist=goodsservice.findbyorder(orderId);
		String message="订单 "+orderId+"包含的货物";
		model.addAttribute("message",message);
		model.addAttribute("goodslist",goodslist);
		return "finance_order_warehouse_goods";
		
	}
	
	@GetMapping(value= {"/iws/finance/order/delete/{orderId}"})
	public String finance_deleteorder(@PathVariable("orderId") String orderId,Model model) {
		String message="";
		int result=changeorderservice.deletechangeorder(orderId);
		switch(result) {
		case -1:
			message="订单 "+orderId+" 不存在";
			break;
		case -2:
			message="订单 "+orderId+" 已执行，不可删除";
			break;
		case 0:
			message="订单 "+orderId+" 删除失败";
			break;
		default:
			message="订单 "+orderId+" 删除成功";
			break;
		}
		List<outOrder> outorderlist=outorderservice.alloutorder();
		List<inOrder> inorderlist=inorderservice.allinorder();
		List<changeOrder> changeorderlist=changeorderservice.allchangeorder();
		model.addAttribute("message",message);
		model.addAttribute("outorders",outorderlist);
		model.addAttribute("inorders",inorderlist);
		model.addAttribute("changeorders",changeorderlist);
		return "finance_order";
	}
	
	@RequestMapping(value= {"/iws/finance/order/add_html"})
	public String finance_addorder_html() {
		return "finance_order_add";
	}
	
	@RequestMapping(value= {"/iws/finance/order/add"})
	public String finance_addorder(@ModelAttribute("changeOrder" )changeOrder changeorder,Model model) {
		
		if("出库".equals(changeorder.getType())) {
		
			outOrder outorder=new outOrder();
			outorder.setOrderId(changeorder.getOrderId());
			outorder.setGoodId(changeorder.getGoodId());
			outorder.setPreWarehouseId(changeorder.getPreWarehouseId());
			outorder.setState(changeorder.getState());
			outorder.setType(changeorder.getType());
			int result=outorderservice.addoutorder(outorder);
			String message="";
			switch(result) {
			case -1:
				message="货物 "+changeorder.getGoodId()+" 不存在";
				model.addAttribute("message",message);
				return "finance_order_add";
			case -2:
				message="货物 "+changeorder.getGoodId()+" 已出库，或在运输中";
				model.addAttribute("message",message);
				return "finance_order_add";
			case -3:
				message="货物 "+changeorder.getGoodId()+" 不在仓库 "+changeorder.getPreWarehouseId()+" 中";
				model.addAttribute("message",message);
				return "finance_order_add";
			case -4:
				message="订单 "+changeorder.getOrderId()+" 重复";
				model.addAttribute("message",message);
				return "finance_order_add";
			case 0:
				message="出库订单 "+changeorder.getOrderId()+" 添加失败";
				model.addAttribute("message",message);
				return "finance_order_add";
			default:
				message="出库订单 "+changeorder.getOrderId()+" 添加成功";
				model.addAttribute("message",message);
				return "finance_order_add";
			}
			
		}
		else if("入库".equals(changeorder.getType())) {
			inOrder inorder=new inOrder();
			inorder.setOrderId(changeorder.getOrderId());
			inorder.setGoodId(changeorder.getGoodId());
			inorder.setNextWarehouseId(changeorder.getNextWarehouseId());
			inorder.setState(changeorder.getState());
			inorder.setType(changeorder.getType());
			int result=inorderservice.addinorder(inorder);
			String message="";
			switch(result) {
			case -2:
				message="货物 "+changeorder.getGoodId()+" 不存在";
				model.addAttribute("message",message);
				return "finance_order_add";
			case -3:
				message="货物 "+changeorder.getGoodId()+" 已入库，或在运输中";
				model.addAttribute("message",message);
				return "finance_order_add";
			case -1:
				message="仓库  "+changeorder.getNextWarehouseId()+" 已满或库房好错误，请重新选择";
				model.addAttribute("message",message);
				return "finance_order_add";
			case -4:
				message="订单 "+changeorder.getOrderId()+" 重复";
				model.addAttribute("message",message);
				return "finance_order_add";
			case 0:
				message="入库订单 "+changeorder.getOrderId()+" 添加失败";
				model.addAttribute("message",message);
				return "finance_order_add";
			default:
				message="入库订单 "+changeorder.getOrderId()+" 添加成功";
				model.addAttribute("message",message);
				return "finance_order_add";
			}
		}
		else if("位置变更".equals(changeorder.getType())) {
			int result=changeorderservice.addchangeorder(changeorder);
			String message="";
			switch(result) {
			case -1:
				message="货物 "+changeorder.getGoodId()+" 不存在";
				model.addAttribute("message",message);
				return "finance_order_add";
			case -2:
				message="货物 "+changeorder.getGoodId()+" 在运输中";
				model.addAttribute("message",message);
				return "finance_order_add";
			case -3:
				message="货物 "+changeorder.getGoodId()+" 不在仓库 "+changeorder.getPreWarehouseId()+" 中";
				model.addAttribute("message",message);
				return "finance_order_add";
			case -4:
				message="仓库  "+changeorder.getNextWarehouseId()+" 错误或库房已满，请重新下选择库房";
				model.addAttribute("message",message);
				return "finance_order_add";
			case -5:
				message="订单 "+changeorder.getOrderId()+" 重复";
				model.addAttribute("message",message);
				return "finance_order_add";
			case 0:
				message="位置变更订单 "+changeorder.getOrderId()+" 添加失败";
				model.addAttribute("message",message);
				return "finance_order_add";
			default:
				message="位置变更订单 "+changeorder.getOrderId()+" 添加成功";
				model.addAttribute("message",message);
				return "finance_order_add";
			}
		}
		else {
			String message="订单类型错误 ，请重新输入";
			model.addAttribute("message",message);
			return "finance_order_add";
		}
	}
	
	@GetMapping(value= {"/iws/finance/order/update/{orderId}"})
	public String finance_updateorder_html(@PathVariable("orderId") String orderId,Model model) {
			System.out.println(orderId);
			//orderDao中定义的方法，按orderID查找订单（不区分订单类型）
			List<changeOrder> changeOrderlist=changeorderservice.findorder(orderId);
			changeOrder changeorder=changeOrderlist.get(0);
			String message="";
			if(!"未执行".equals(changeorder.getState())) {
				message="订单 "+orderId+" 已执行，不允许编辑";
				model.addAttribute("message",message);
				List<outOrder> outorderlist=outorderservice.alloutorder();
				List<inOrder> inorderlist=inorderservice.allinorder();
				List<changeOrder> changeorderlist=changeorderservice.allchangeorder();
				
				model.addAttribute("outorders",outorderlist);
				model.addAttribute("inorders",inorderlist);
				model.addAttribute("changeorders",changeorderlist);
				return "finance_order";
				
			}
			List<goods> goodslist=goodsservice.findbyorder(orderId);

			model.addAttribute("changeorder",changeorder);
			model.addAttribute("goodslist",goodslist);

			return "finance_order_update";

	}
	
	@GetMapping(value= {"/iws/finance/order/deletegoods/{orderId}/{goodId}"})
	public String finance_deleteordergoods(@PathVariable("orderId") String orderId,@PathVariable("goodId") String goodId,Model model) {
		int result=changeorderservice.deleteordergoods(orderId,goodId);
		String message="";
		switch(result) {
		case -1:
			message="订单 "+orderId+"不存在";
			break;
		case -2:
			message="订单 "+orderId+"中 没有货物 "+goodId;
			break;
		case 0:
			message="删除订单 "+orderId+"中 的货物 "+goodId+"失败";
			break;
		default:
			message="删除订单 "+orderId+"中 的货物 "+goodId;
			break;
			
		}
		List<changeOrder> changeOrderlist=changeorderservice.findorder(orderId);
		changeOrder changeorder=changeOrderlist.get(0);
		List<goods> goodslist=goodsservice.findbyorder(orderId);
		model.addAttribute("message",message);
		model.addAttribute("changeorder",changeorder);
		model.addAttribute("goodslist",goodslist);
		return "finance_order_update";
	}
	
     
}

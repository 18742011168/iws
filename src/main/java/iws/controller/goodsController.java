package iws.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import iws.beans.goods;
import iws.service.goodsService;

@Controller
public class goodsController {
	@Autowired
	private goodsService goodsservice;
	
	@RequestMapping("/iws/manager/goods")
	public String allgoods(Model model) {
		List<goods> goodslist=goodsservice.allgoods();
		model.addAttribute("goodslist",goodslist);
		return "manager_goods";
		
	}
	
	@GetMapping(value="/iws/manager/goods/delete/{goodId}")
	public String deletegoods(@PathVariable("goodId") String goodId,Model model) {
		int result=goodsservice.deletegoods(goodId);
		List<goods> goodslist=new ArrayList<goods>();
		String message="";
		switch(result) {
		case -1:
			message="货物 "+goodId+"不存在";
			goodslist=goodsservice.allgoods();
			model.addAttribute("goodslist",goodslist);
			model.addAttribute("message",message);
			return "manager_goods";
		case -2:
			message="货物 "+goodId+"运输中，不可删除";
			goodslist=goodsservice.allgoods();
			model.addAttribute("goodslist",goodslist);
			model.addAttribute("message",message);
			return "manager_goods";
		case 0:
			message="货物 "+goodId+"删除失败";
			goodslist=goodsservice.allgoods();
			model.addAttribute("goodslist",goodslist);
			model.addAttribute("message",message);
			return "manager_goods";
		default :
			message="货物 "+goodId+"删除成功";
			goodslist=goodsservice.allgoods();
			model.addAttribute("goodslist",goodslist);
			model.addAttribute("message",message);
			return "manager_goods";
		}
		
	}
	
	@GetMapping(value="/iws/manager/goods/update/{goodId}")
	public String update_html(@PathVariable("goodId") String goodId,Model model) {
		List<goods> goodslist=goodsservice.findgoods(goodId);
		goods goods=goodslist.get(0);
		model.addAttribute("goods",goods);
		return "manager_goods_update";
	}
	
	@RequestMapping(value= {"/iws/manager/goods/update"})
	public String updategoods(@ModelAttribute("goods") goods goods,Model model) {
		String message="";
		if(goodsservice.updategoods(goods.getGoodId(),goods.getCategory(),goods.getWeight(),goods.getState()))
			message="货物 "+goods.getGoodId()+"更新成功";
		else
			message="货物 "+goods.getGoodId()+"更新失败";
		List<goods> goodslist=goodsservice.allgoods();
		model.addAttribute("goodslist",goodslist);
		model.addAttribute("message",message);
		return "manager_goods";
	}
	
	@RequestMapping(value= {"/iws/manager/goods/add_html"})
	public String add_html() {	
		return "manager_goods_add";
	}
	
	@RequestMapping(value= {"/iws/manager/goods/add"})
	public String addgoods(@ModelAttribute("goods") goods goods,Model model) {
		String message="";
		int result=goodsservice.addgoods(goods);
		switch(result) {
		case -1:
			message="货物编号 "+goods.getGoodId()+"重复";
			model.addAttribute("message",message);
			return "manager_goods_add";
		case 0:
			message="货物"+goods.getGoodId()+"添加失败";
			model.addAttribute("message",message);
			return "manager_goods_add";
		default :
			message="货物"+goods.getGoodId()+"添加成功";
			List<goods> goodslist=goodsservice.allgoods();
			model.addAttribute("message",message);
			model.addAttribute("goodslist",goodslist);
			return "manager_goods";
				
		}
	}
	
	@GetMapping(value="/iws/godownner/goods/update/{goodId}/{orderId}")
	public String godownner_update_html(@PathVariable("goodId") String goodId,@PathVariable("orderId") String orderId,Model model) {
		List<goods> goodslist=goodsservice.findgoods(goodId);
		
		
		goods goods=goodslist.get(0);
		model.addAttribute("goods",goods);
		model.addAttribute("orderId",orderId);
		return "godownner_order_goods_update";
	}
	
	@RequestMapping(value="/iws/godownner/goods/update")
	public String godownner_updategoods(@ModelAttribute("goods") goods goods,String orderId,Model model) {
		String updatemessage="";
		
		if(goodsservice.updategoods(goods.getGoodId(),goods.getWeight(),goods.getWarehouseId(),goods.getState(),orderId)==1) {
			updatemessage="货物 "+goods.getGoodId()+"更新成功";
		}
		else {
			updatemessage="货物 "+goods.getGoodId()+"更新失败";
			
		}
		List<goods> goodslist=goodsservice.findbyorder(orderId);
		
		model.addAttribute("updatemessage",updatemessage);
		String message="订单 "+orderId+"包含的货物";
		model.addAttribute("message",message);
		model.addAttribute("goodslist",goodslist);
		model.addAttribute("orderId",orderId);
		return "godownner_order_goods";	
		
	}
	
	@RequestMapping("/iws/finance/goods")
	public String finance_allgoods(Model model) {
		List<goods> goodslist=goodsservice.allgoods();
		model.addAttribute("goodslist",goodslist);
		return "finance_goods";
		
	}
	

}

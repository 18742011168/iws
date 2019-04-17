package iws.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
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
	
	@RequestMapping("/iws/goods")
	@RequiresPermissions("querygoods")
	public String allgoods(Model model) {
		List<goods> goodslist=goodsservice.allgoods();
		model.addAttribute("goodslist",goodslist);
		return "goods";
		
	}
	
	@GetMapping(value="/iws/goods/delete/{goodId}")
	@RequiresPermissions("deletegoods")
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
			return "goods";
		case -2:
			message="货物 "+goodId+"运输中，不可删除";
			goodslist=goodsservice.allgoods();
			model.addAttribute("goodslist",goodslist);
			model.addAttribute("message",message);
			return "goods";
		case 0:
			message="货物 "+goodId+"删除失败";
			goodslist=goodsservice.allgoods();
			model.addAttribute("goodslist",goodslist);
			model.addAttribute("message",message);
			return "goods";
		default :
			message="货物 "+goodId+"删除成功";
			goodslist=goodsservice.allgoods();
			model.addAttribute("goodslist",goodslist);
			model.addAttribute("message",message);
			return "goods";
		}
		
	}
	
	@GetMapping(value="/iws/goods/update/{goodId}")
	@RequiresPermissions("updategoods")
	public String update_html(@PathVariable("goodId") String goodId,Model model) {
		List<goods> goodslist=goodsservice.findgoods(goodId);
		goods goods=goodslist.get(0);
		model.addAttribute("goods",goods);
		return "goods_update";
	}
	
	@RequestMapping(value= {"/iws/goods/update"})
	@RequiresPermissions("updategoods")
	public String updategoods(@ModelAttribute("goods") goods goods,Model model) {
		String message="";
		if(goodsservice.updategoods(goods.getGoodId(),goods.getCategory(),goods.getWeight(),goods.getState()))
			message="货物 "+goods.getGoodId()+"更新成功";
		else
			message="货物 "+goods.getGoodId()+"更新失败";
		List<goods> goodslist=goodsservice.allgoods();
		model.addAttribute("goodslist",goodslist);
		model.addAttribute("message",message);
		return "goods";
	}
	
	@RequestMapping(value= {"/iws/goods/add_html"})
	@RequiresPermissions("addgoods")
	public String add_html() {	
		return "goods_add";
	}
	
	@RequestMapping(value= {"/iws/goods/add"})
	@RequiresPermissions("addgoods")
	public String addgoods(@ModelAttribute("goods") goods goods,Model model) {
		String message="";
		int result=goodsservice.addgoods(goods);
		switch(result) {
		case -1:
			message="货物编号 "+goods.getGoodId()+"重复";
			model.addAttribute("message",message);
			return "goods_add";
		case 0:
			message="货物"+goods.getGoodId()+"添加失败";
			model.addAttribute("message",message);
			return "goods_add";
		default :
			message="货物"+goods.getGoodId()+"添加成功";
			List<goods> goodslist=goodsservice.allgoods();
			model.addAttribute("message",message);
			model.addAttribute("goodslist",goodslist);
			return "goods";
				
		}
	}
	
	@GetMapping(value="/iws/order/goods/update/{goodId}/{orderId}")
	@RequiresPermissions("updateordergoods")
	public String godownner_update_html(@PathVariable("goodId") String goodId,@PathVariable("orderId") String orderId,Model model) {
		List<goods> goodslist=goodsservice.findgoods(goodId);
		
		
		goods goods=goodslist.get(0);
		model.addAttribute("goods",goods);
		model.addAttribute("orderId",orderId);
		return "order_goods_update";
	}
	
	@RequestMapping(value="/iws/order/goods/update")
	@RequiresPermissions("updateordergoods")
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
		return "order_goods";	
		
	}
	
	
	

}

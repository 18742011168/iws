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
import iws.beans.wareHouse;
import iws.service.goodsService;
import iws.service.wareHouseService;

@Controller
public class wareHouseController {
	@Autowired
	private wareHouseService warehouseservice;
	
	@Autowired
	private goodsService goodsservice;
	
	@RequestMapping(value="/iws/warehouse")
	@RequiresPermissions("querywarehouse")
	public String manager_allwarehouse(Model model) {
		model.addAttribute("warehouselist",warehouseservice.allwarehouse());
		return "warehouse";		
	}
	
	@GetMapping(value= {"/iws/warehouse/goods/{wareHouseId}"})
	@RequiresPermissions("querywarehousegoods")
	public String manager_warehouse_goods(@PathVariable("wareHouseId")String warehouseId,Model model) {
		List<goods> goodslist=goodsservice.findbywarehouse(warehouseId);
		model.addAttribute("goodslist",goodslist);
		String message="仓库 "+warehouseId+"中的货物";
		model.addAttribute("message",message);
		return "warehouse_goods";
	}
	
	@GetMapping(value="/iws/warehouse/delete/{wareHouseId}")
	@RequiresPermissions("deletewarehouse")
	public String deletewarehouse(@PathVariable("wareHouseId")String warehouseId,Model model) {
		int result=warehouseservice.deletewarehouse(warehouseId);
		String message="";
		List<wareHouse> warehouselist=new ArrayList<wareHouse>();
		switch(result) {
		case -1:
			message="仓库 "+warehouseId+" 不存在";
			model.addAttribute("message",message);
			warehouselist=warehouseservice.allwarehouse();
			model.addAttribute("warehouselist",warehouselist);
			return "warehouse";
		case -2:
			message="仓库 "+warehouseId+" 有货物，不允许删除";
			model.addAttribute("message",message);
			warehouselist=warehouseservice.allwarehouse();
			model.addAttribute("warehouselist",warehouselist);
			return "warehouse";
		case 0:
			message="仓库 "+warehouseId+"删除失败";
			model.addAttribute("message",message);
			warehouselist=warehouseservice.allwarehouse();
			model.addAttribute("warehouselist",warehouselist);
			return "warehouse";
		default:
			message="仓库 "+warehouseId+"删除成功";
			model.addAttribute("message",message);
			warehouselist=warehouseservice.allwarehouse();
			model.addAttribute("warehouselist",warehouselist);
			return "warehouse";
		}
	}
	
	@RequestMapping(value="/iws/warehouse/add_html")
	@RequiresPermissions("addwarehouse")
	public String add_html() {
		return "warehouse_add";
	}
	
	@RequestMapping(value="/iws/warehouse/add")
	@RequiresPermissions("addwarehouse")
	public String addwarehouse(@ModelAttribute("wareHouse") wareHouse warehouse,Model model) {
		String message="";
		int result=warehouseservice.addwarehouse(warehouse);
		switch(result) {
		case -1:
			message="仓库 "+warehouse.getWareHouseId()+"已存在";
			model.addAttribute("message",message);
			return "warehouse_add";
		case 0:
			message="仓库 "+warehouse.getWareHouseId()+"添加失败";
			model.addAttribute("message",message);
			return "warehouse_add";
		default:
			message="仓库 "+warehouse.getWareHouseId()+"添加成功";
			List<wareHouse> warehouselist=warehouseservice.allwarehouse();
			model.addAttribute("message",message);
			model.addAttribute("warehouselist",warehouselist);
			return "warehouse";
			
		}
	}
	
	@RequestMapping(value="/iws/finance/warehouse")
	public String finance_allwarehouse(Model model) {
		model.addAttribute("warehouselist",warehouseservice.allwarehouse());
		return "finance_warehouse";		
	}
	
	@GetMapping(value= {"/iws/finance/warehouse/goods/{wareHouseId}"})
	public String finance_warehouse_goods(@PathVariable("wareHouseId")String warehouseId,Model model) {
		List<goods> goodslist=goodsservice.findbywarehouse(warehouseId);
		model.addAttribute("goodslist",goodslist);
		String message="仓库 "+warehouseId+"中的货物";
		model.addAttribute("message",message);
		return "finance_order_warehouse_goods";
	}
	
	

}

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
import iws.beans.wareHouse;
import iws.service.goodsService;
import iws.service.wareHouseService;

@Controller
public class wareHouseController {
	@Autowired
	private wareHouseService warehouseservice;
	
	@Autowired
	private goodsService goodsservice;
	
	@RequestMapping(value="/iws/manager/warehouse")
	public String allwarehouse(Model model) {
		model.addAttribute("warehouselist",warehouseservice.allwarehouse());
		return "manager_warehouse";		
	}
	
	@GetMapping(value="/iws/manager/warehouse/goods/{wareHouseId}")
	public String warehouse_goods(@PathVariable("wareHouseId")String warehouseId,Model model) {
		List<goods> goodslist=goodsservice.findbywarehouse(warehouseId);
		model.addAttribute("goodslist",goodslist);
		String message="仓库 "+warehouseId+"中的货物";
		model.addAttribute("message",message);
		return "order_warehouse_goods";
	}
	
	@GetMapping(value="/iws/manager/warehouse/delete/{wareHouseId}")
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
			return "manager_warehouse";
		case -2:
			message="仓库 "+warehouseId+" 有货物，不允许删除";
			model.addAttribute("message",message);
			warehouselist=warehouseservice.allwarehouse();
			model.addAttribute("warehouselist",warehouselist);
			return "manager_warehouse";
		case 0:
			message="仓库 "+warehouseId+"删除失败";
			model.addAttribute("message",message);
			warehouselist=warehouseservice.allwarehouse();
			model.addAttribute("warehouselist",warehouselist);
			return "manager_warehouse";
		default:
			message="仓库 "+warehouseId+"删除成功";
			model.addAttribute("message",message);
			warehouselist=warehouseservice.allwarehouse();
			model.addAttribute("warehouselist",warehouselist);
			return "manager_warehouse";
		}
	}
	
	@RequestMapping(value="/iws/manager/warehouse/add_html")
	public String add_html() {
		return "manager_warehouse_add";
	}
	
	@RequestMapping(value="/iws/manager/warehouse/add")
	public String addwarehouse(@ModelAttribute("wareHouse") wareHouse warehouse,Model model) {
		String message="";
		int result=warehouseservice.addwarehouse(warehouse);
		switch(result) {
		case -1:
			message="仓库 "+warehouse.getWareHouseId()+"已存在";
			model.addAttribute("message",message);
			return "manager_warehouse_add";
		case 0:
			message="仓库 "+warehouse.getWareHouseId()+"添加失败";
			model.addAttribute("message",message);
			return "manager_warehouse_add";
		default:
			message="仓库 "+warehouse.getWareHouseId()+"添加成功";
			List<wareHouse> warehouselist=warehouseservice.allwarehouse();
			model.addAttribute("message",message);
			model.addAttribute("warehouselist",warehouselist);
			return "manager_warehouse";
			
		}
	}

}

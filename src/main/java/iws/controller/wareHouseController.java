package iws.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import iws.service.wareHouseService;

@Controller
public class wareHouseController {
	@Autowired
	private wareHouseService warehousesevice;
	@RequestMapping(value="/allwarehouse")
	public String allwarehouse(Model model) {
		model.addAttribute("warehousees",warehousesevice.allwarehouse());
		return "allwarehouse";
		
	}

}

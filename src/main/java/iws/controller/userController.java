package iws.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import iws.service.userService;

@Controller
public class userController {
	
	@Autowired
	private userService userservice;
	
	@RequestMapping(value="/allusers")
	public String findAllUsers(Model model) {
		 model.addAttribute("users",userservice.allUser());
		return "allusers";
	}
}

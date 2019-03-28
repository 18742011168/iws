package iws.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import iws.beans.user;
import iws.service.loginService;

@Controller
public class loginController {
	@RequestMapping("/")
	public String home() {
		return "index";
	}
	
	@Autowired
	private loginService loginservice;
	
	
	
	@RequestMapping("/login")
	public String login(@ModelAttribute("user") user user,Model model){
			if(loginservice.CanLogin(user.getUsername(), user.getPassword(),user.getPosition())){
				model.addAttribute("user", user);
				System.out.println("登录成功");
				return "success";
			}		
			else{
				System.out.println("登录失败");
				return "error";
			}
	}
	
	
	
}

package iws.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import iws.service.userService;

@Controller
public class resetpassword {
	
	@Autowired
	private userService userservice;
	
	@RequestMapping("forgetpassword")
	public String forget(String username,String email) {
		if(userservice.forgetpassword(username,email)==1) {
			System.out.println("邮件发送成功");
			return "reset";
		}
		else
			return "hello";
		
	}
	
	@RequestMapping("forgetpassword/resetpassword")
	public String reset(String username,String verification,String password) {
		userservice.resetpassword(username,verification,password);
		return "hello";
	}

}

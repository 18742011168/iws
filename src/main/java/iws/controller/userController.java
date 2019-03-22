package iws.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import iws.beans.user;
import iws.service.userService;

@Controller

public class userController {
	
	@Autowired
	private userService userservice;
	
	@RequestMapping(value="/login/users")
	public String findAllUsers(Model model) {
		 model.addAttribute("users",userservice.allUser());
		return "allusers";
	}
	
	@RequestMapping(value="/login/users/adduser")
	public String addUser(String username,String password,String position) {
		user user=new user();
		user.setUsername(username);
		user.setPassword(password);
		user.setPosition(position);
		if(userservice.adduser(user))
			System.out.println("添加成功");
		return "hello";
	}
	
}

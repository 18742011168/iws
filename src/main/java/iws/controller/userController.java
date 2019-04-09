package iws.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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
	/*
	@RequestMapping(value="/login/users/adduser")
	public String addUser(String username,String password,String position,String email) {
		user user=new user();
		user.setUsername(username);
		user.setPassword(password);
		user.setPosition(position);
		user.setEmail(email);
		if(userservice.adduser(user)==1)
			System.out.println("添加成功");
		return "hello";
	}
	*/
	@RequestMapping(value="/iws/manager/user")
	public String alluser(Model model) {
		List<user> userlist=userservice.allUser();
		model.addAttribute("users",userlist);
		return "manager_user";
	}
	
	@GetMapping(value="/iws/manager/user/delete/{username}")
	public String deleteuser(@PathVariable("username") String username,Model model) {
		int result=userservice.deleteuser(username);
		String message="";
		if(result==1)
			message="账户"+username+"删除成功";
		else 
			message="账户"+username+"删除失败";
		List<user> userlist=userservice.allUser();
		model.addAttribute("users",userlist);
		model.addAttribute("message",message);
		return "manager_user";
	}
	
	@GetMapping(value="/iws/manager/user/update/{username}")
	public String update_html(@PathVariable("username") String username,Model model) {
		List<user> userlist=userservice.FindUserByName(username);
		user user=userlist.get(0);
		model.addAttribute("user",user);
		return "manager_user_update";
	}
	
	@RequestMapping(value= {"/iws/manager/user/update"})
	public String update(@ModelAttribute("user") user user,Model model) {
		String message="";
		if(userservice.updateuser(user)) {
			message="账号"+user.getUsername()+"更新成功";
			
		}
		else
			message="账号"+user.getUsername()+"更新失败";
		List<user> userlist=userservice.allUser();
		model.addAttribute("users",userlist);
		model.addAttribute("message",message);
		return "manager_user";
	}
	@RequestMapping(value="/iws/manager/user/add_html")
	public String add_html() {
		return "manager_user_add";
	}
	
	@RequestMapping(value="/iws/manager/user/add")
	public String addUser(String username,String password,String position,String email,Model model) {
		user user=new user();
		user.setUsername(username);
		user.setPassword(password);
		user.setPosition(position);
		user.setEmail(email);
		String message="";
		int result=userservice.adduser(user);
		switch(result) {
		   case -1:
			    message="用户名 "+username+" 已存在";
				model.addAttribute("message",message);
				return "manager_user_add";
				
		   case 0:
			    message="用户 "+username+" 添加失败";
				model.addAttribute("message",message);
				return "manager_user_add";
		   
		   default:
			    message="用户"+username+" 添加成功";
			    List<user> userlist=userservice.allUser();
				model.addAttribute("users",userlist);
				model.addAttribute("message",message);
				
				return "manager_user";
		}
		
	}
	
}

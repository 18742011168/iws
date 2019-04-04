package iws.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import iws.beans.user;
import iws.service.loginService;
import iws.service.userService;

@Controller
public class loginController {
	@RequestMapping("/")
	public String home() {
		return "index1";
	}
	
	@Autowired
	private loginService loginservice;
	
	@Autowired
	private userService userservice;
	
	@RequestMapping("/login")
	public String login(@ModelAttribute("user") user user,Model model){
		int result=loginservice.CanLogin(user.getUsername(), user.getPassword(),user.getPosition());
		String message="";
		switch(result) {
		   case -1:
			    message="用户名错误";
				model.addAttribute("message",message);
				return "index1";
				
		   case -2:
			    message="密码错误";
				model.addAttribute("message",message);
				return "index1";
		   case -3:
			    message="职务错误";
				model.addAttribute("message",message);				
				return "index1";
		   default:			   
			    model.addAttribute("user", user);
				System.out.println("登录成功");
				return "manager";
		}
			
	}
	
	@RequestMapping("/login/lostpassword")
	public String lost() {
		
		return "sendemail";
	}
	/*
	@RequestMapping("/login/forgetpassword")
	public String forget(String username,String email) {
		userservice.forgetpassword(username,email);
		return "hello";
	}
	*/
	
	@RequestMapping("/login/forgetpassword")
	public String forget(String username,String email,Model model) {
		int result=userservice.forgetpassword(username,email);
		String message="";
		switch(result) {
		 case -1:
			 message="用户名错误";
			 model.addAttribute("message",message);
			 return "sendemail";
		 case -2:
			 message="邮箱错误";
			 model.addAttribute("message",message);
			 return "sendemail";
		 case 1:
	    	 message="邮件已发送";
	    	 model.addAttribute("message",message);
	    	 return "reset";
		 default :
			 
			 return "sendemail";
		}
		
	}
	
	@RequestMapping("/login/resetpassword")
	public String reset(String username,String verification,String password,Model model) {
		int result=userservice.resetpassword(username,verification,password);
		String message="";
		switch(result) {
		 case -1:
			 message="用户名错误";
			 model.addAttribute("message",message);
			 return "reset";
		 case -2:
			 message="验证码为空";
			 model.addAttribute("message",message);
			 return "reset";
		 case -3:
			 message="验证码错误";
			 model.addAttribute("message",message);
			 return "reset";
		 case 1:
	    	// message="密码修改成功";
	    	// model.addAttribute("message",message);
	    	 return "hello";
		 default:
			 message="密码修改失败";
	    	 model.addAttribute("message",message);
	    	 return "reset";
			
		}
	}
	
	
	
	
	
}

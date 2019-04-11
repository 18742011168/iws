package iws.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import iws.beans.changeOrder;
import iws.beans.inOrder;
import iws.beans.outOrder;
import iws.beans.user;
import iws.service.changeOrderService;
import iws.service.goodsService;
import iws.service.inOrderService;
import iws.service.loginService;
import iws.service.outOrderService;
import iws.service.userService;
import iws.service.wareHouseService;
import iws.service.warnningService;

@Controller
public class loginController {
	@RequestMapping("/")
	public String home() {
		return "home";
	}
	
	@Autowired
	private loginService loginservice;
	
	@Autowired
	private userService userservice;
	
	@Autowired
	private goodsService goodsservice;
	
	@Autowired
	private outOrderService outorderservice;
	
	@Autowired
	private inOrderService inorderservice;
	
	@Autowired
	private changeOrderService changeorderservice;
	
	@Autowired
	private wareHouseService warehouseservice;
	
	@Autowired
	private warnningService warnningservice;
	
	
	@RequestMapping("/login")
	public String login(@ModelAttribute("user") user user,Model model){
		int result=loginservice.CanLogin(user.getUsername(), user.getPassword(),user.getPosition());
		String message="";
		switch(result) {
		   case -1:
			    message="用户名错误";
				model.addAttribute("message",message);
				return "home";
				
		   case -2:
			    message="密码错误";
				model.addAttribute("message",message);
				return "home";
		   case -3:
			    message="职务错误";
				model.addAttribute("message",message);				
				return "home";
		   default:			   
			    model.addAttribute("user", user);
				System.out.println("登录成功");
				return "welcome";
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
	    	 message="密码修改成功";
	    	 model.addAttribute("message",message);
	    	 return "home";
		 default:
			 message="密码修改失败";
	    	 model.addAttribute("message",message);
	    	 return "reset";
			
		}
	}
	
	@GetMapping(value="/iws/{position}")
	public String position_html(@PathVariable("position") String position,Model model) {
		if("manager".equals(position)) {
			int usernumber=userservice.usernumber();
			int managernumber=userservice.managernumber();
			int financenumber=userservice.financenumber();
			int godownnernumber=userservice.godownnernumber();
			int goodsnumber=goodsservice.goodsnumber();
			int warehousenumber=warehouseservice.warehousenumber();
			int warnningnumber=warnningservice.warnningnumber();
			int outordernumber=outorderservice.outordernumber();
			int inordernumber=inorderservice.inordernumber();
			int changeordernumber=changeorderservice.changeordernumber();
			int ordernumber=outordernumber+inordernumber+changeordernumber;
			model.addAttribute("usernumber",usernumber);
			model.addAttribute("managernumber",managernumber);
			model.addAttribute("financenumber",financenumber);
			model.addAttribute("godownnernumber",godownnernumber);
			model.addAttribute("goodsnumber",goodsnumber);
			model.addAttribute("warehousenumber",warehousenumber);
			model.addAttribute("warnningnumber",warnningnumber);
			model.addAttribute("outordernumber",outordernumber);
			model.addAttribute("inordernumber",inordernumber);
			model.addAttribute("changeordernumber",changeordernumber);
			model.addAttribute("ordernumber",ordernumber);
			
			return "manager_total";
		}
		else if("finance".equals(position)) {
			return "finance_total";
		}
		else {
			List<outOrder> outorderlist=outorderservice.alloutorder();
			List<inOrder> inorderlist=inorderservice.allinorder();
			List<changeOrder> changeorderlist=changeorderservice.allchangeorder();
			
			model.addAttribute("outorders",outorderlist);
			model.addAttribute("inorders",inorderlist);
			model.addAttribute("changeorders",changeorderlist);
			return "godownner_order";
			
		}
		
	}
	
	
	
}

package iws.service;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import iws.DAO.userDao;
import iws.beans.user;
@Service
public class userService {
   @Autowired
   private userDao userdao;
  
   @Autowired
   private emailService emailservice;
   public List<user> allUser(){
	   return userdao.queryAll();
   }
   public List<user> FindUserByName (String username){
	   
	   return userdao.FindUserByName(username);
   }
   public int adduser(user user) {
	   List<user> userlist=userdao.FindUserByName(user.getUsername());
	   if(!userlist.isEmpty()) {
		   System.out.println("用户名已存在");
		   return -1;	   
	   }
	   
	   if(userdao.adduser(user)) {
		   System.out.println("用户添加成功");
		   return 1;
	   }
	   System.out.println("用户添加失败");
	   return 0;	
   }
   public int deleteuser(String username) {
	   List<user> userlist=userdao.FindUserByName(username);
	   if(userlist.isEmpty()) {
		   System.out.println("用户不存在");
		   return -1;	   
	   }
	   if(userdao.deleteuser(username)) {
		   System.out.println("用户删除成功");
		   return 1;
	   }
	   System.out.println("用户删除失败");
	   return 0;
   }
   public boolean updateuser(user user) {
	   return userdao.updateuser(user);
   }
   
   public int forgetpassword(String username,String email) {
	   List<user> userlist=userdao.FindUserByName(username);
	   if(userlist.isEmpty()) {
		   System.out.println("用户不存在");
		   return -1;	   
	   }

	   user user=userlist.get(0);
	   if(!email.equals(user.getEmail())) {
		   System.out.println("用户名与邮箱不匹配");
		   return -2;
	   }
	   String str="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	   Random random=new Random();
	   StringBuffer Sb=new StringBuffer();
	   for(int i=0;i<6;i++){
		   
	       int number=random.nextInt(62);
	       Sb.append(str.charAt(number));
	   }
	   String verification=Sb.toString();
	   System.out.println("验证码为"+verification);
	   userdao.resetverification(username, verification);
	   emailservice.sendEmail(email, verification);
	   System.out.println("邮件发送成功");
	   return 1;
	   
   }
   public int resetpassword(String username,String verification,String password) {
	   List<user> userlist=userdao.FindUserByName(username);
	   if(userlist.isEmpty()) {
		   System.out.println("用户不存在");
		   return -1;	   
	   }
	   user user=userlist.get(0);
	   if(!verification.equals(user.getVerification())) {
		   System.out.println("验证码错误");
		   return -2;
	   }
	   if(userdao.resetpassword(username, password)) {
		   System.out.println("密码修改成功");
		   return 1;
	   }
	   System.out.println("密码修改失败");
	   return 0;
   }
   
   /*
    * 
    */
}

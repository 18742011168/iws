package iws.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import iws.DAO.userDao;
import iws.beans.user;
@Service
public class userService {
   @Autowired
   private userDao userdao;
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
   /*
    * 
    */
}

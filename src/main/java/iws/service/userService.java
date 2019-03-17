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
   public boolean adduser(user user) {
	   return userdao.adduser(user);
   }
   
   /*
    * 
    */
}

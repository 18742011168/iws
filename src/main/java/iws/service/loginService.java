package iws.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import iws.DAO.userDao;
import iws.beans.user;
@Service
public class loginService {
	@Autowired
    private userDao userdao;
	
	public void setUserdao(userDao userdao){
		this.userdao=userdao;
	}
	
	public int CanLogin(String username,String password,String position){
		List<user> userlist=userdao.FindUserByName(username);
		
		if(userlist.isEmpty())
			return -1;
		user user=userlist.get(0);
		if(!user.getPassword().equals(password))
			return -2;
		if(!user.getPosition().equals(position))
			return -3;
		
		return 1;
	}
}

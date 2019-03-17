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
	
	public boolean CanLogin(String username,String password,String position){
		List<user> userlist=userdao.FindUserByName(username);
		for(int i=0;i<userlist.size();i++){
			if(userlist.get(i).getPassword().equals(password)&&userlist.get(i).getPosition().equals(position)){
				return true;	
			}		
		}
		return false;
	}
}

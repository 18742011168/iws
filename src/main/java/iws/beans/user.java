package iws.beans;

import javax.persistence.Entity;

@Entity
public class user {
	private String username;
	   private String password;
	   private String position;
	   
	   public String getUsername(){
		   return username;
	   }
	   public void setUsername(String username) {
	       this.username = username;
	   }
	   public String getPassword() {
	       return password;
	   }
	   public void setPassword(String password) {
	       this.password = password;
	   }
	   public void setPosition(String position){
		   this.position=position;
	   }
	   public String getPosition(){
		   return position;
	   }
}

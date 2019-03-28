package iws.beans;

import javax.persistence.Entity;

@Entity
public class user {
	private String username;
	   private String password;
	   private String position;
	   private String email;
	   private String verification;
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
	   public void setEmail(String email) {
		   this.email=email;
	   }
	   public String getEmail() {
		   return this.email;
	   }
	   public void setVerification(String verification) {
		   this.verification=verification;
	   }
	   public String getVerification() {
		  return this.verification;
	   }
	  
}

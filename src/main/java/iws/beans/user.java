package iws.beans;

import java.util.List;

import javax.persistence.Entity;

@Entity
public class user {
	private String username;
	   private String password;
	   private String position;
	   private String email;
	   private String verification;
	   private List<role> roles;
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
	   
	   public List<role> getRoles(){
		   return roles;
	   }
	   
	   public void setRoles(List<role> roles) {
		   this.roles=roles;
	   }
	  
}

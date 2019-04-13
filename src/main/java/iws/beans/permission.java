package iws.beans;

import java.util.List;

import javax.persistence.Entity;

@Entity
public class permission {
	private String Id;
	private String name;
	private List<role> roles;
	
	public void setId(String Id) {
		this.Id=Id;
	}
	public String getId() {
		return this.Id;
	}
	
	public void setName(String Name) {
		this.name=Name;
	}
	public String getName() {
		return this.name;
	}
	
	public List<role> getRoles(){
		   return roles;
	}
	   
	public void setRoles(List<role> roles) {
	   this.roles=roles;
	}

}

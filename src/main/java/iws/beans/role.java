package iws.beans;

import java.util.List;

import javax.persistence.Entity;

@Entity
public class role {
	//private String Id;
	private String roleName;
	//private List<user> users;
	private List<permission> permissions;
	/*
	public void setId(String Id) {
		this.Id=Id;
	}
	public String getId() {
		return this.Id;
	}
	*/
	public void setRoleName(String roleName) {
		this.roleName=roleName;
	}
	public String getRoleName() {
		return this.roleName;
	}
	/*
	public void setUsers(List<user> users) {
		this.users=users;
	}
	public List<user> getUsers(){
		return this.users;
	}
	*/
	public void setPermissions(List<permission> permissions) {
		this.permissions=permissions;
	}
	public List<permission> getPermissions(){
		return permissions;
	}
	
}

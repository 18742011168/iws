package iws.beans;



import javax.persistence.Entity;

@Entity
public class permission {
	//private String Id;
	private String permissionName;
	
	//private List<role> roles;
	/*
	public void setId(String Id) {
		this.Id=Id;
	}
	public String getId() {
		return this.Id;
	}
	*/
	public void setPermissionName(String Name) {
		this.permissionName=Name;
	}
	public String getPermissionName() {
		return this.permissionName;
	}
	/*
	public List<role> getRoles(){
		   return roles;
	}
	   
	public void setRoles(List<role> roles) {
	   this.roles=roles;
	}
    */
}

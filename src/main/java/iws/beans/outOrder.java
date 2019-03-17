package iws.beans;

import javax.persistence.Entity;

@Entity
public class outOrder extends order{
	
	
	private String preWarehouseId;
	
	
	/*
    public void set(String ){
		this =
	}
	public String get (){
		return 
	}
	*/
	
	
    public void setPreWarehouseId(String preWarehouseId){
   	 this.preWarehouseId =preWarehouseId;
    }
    public String getPreWarehouseId(){
   	 return preWarehouseId;
    }
    
	
}

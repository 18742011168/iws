package iws.beans;

import javax.persistence.Entity;

@Entity
public class inOrder extends order{

	
    private String nextWarehouseId;
	
	public void setNextWarehouseId(String nextWarehouseId){
   	 this.nextWarehouseId =nextWarehouseId;
    }
    public String getNextWarehouseId(){
   	 return nextWarehouseId;
    }
}   
    
    
	

package iws.beans;

import javax.persistence.Entity;

@Entity
public class changeOrder extends order{
	
	
	private String preWarehouseId;
    private String nextWarehouseId;
	
	
	public void setPreWarehouseId(String preWarehouseId){
	   	 this.preWarehouseId =preWarehouseId;
	}
    public String getPreWarehouseId(){
	   	 return preWarehouseId;
	}
	public void setNextWarehouseId(String nextWarehouseId){
   	 this.nextWarehouseId =nextWarehouseId;
    }
    public String getNextWarehouseId(){
   	 return nextWarehouseId;
    }
}
	


package iws.beans;

import javax.persistence.Entity;

@Entity
public class goods {
	private String goodId;
    private String category;
    private double weight;
    private String warehouseId;
    private String state;
    private String inPlaceTime;
    
    public void setGoodId(String goodId){
   	 this.goodId =goodId;
    }
    public String getGoodId(){
   	 return goodId;
    }
    public void setCategory(String category){
   	 this.category =category;
    }
    public String getCategory(){
   	 return category;
    }
    public void setWeight(double weight){
   	 this.weight=weight;
    }
    public double getWeight(){
   	 return weight;
    }
    public void setWarehouseId(String warehouseId){
   	 this.warehouseId =warehouseId;
    }
    public String getWarehouseId(){
   	 return warehouseId;
    }
    public void setState(String state){
   	 this.state =state;
    }
    public String getState(){
   	 return state;
    }
    
    public void setInPlaceTime(String time) {
    	this.inPlaceTime=time;
    }
    public String getInPlaceTime() {
    	return this.inPlaceTime;
    }
}
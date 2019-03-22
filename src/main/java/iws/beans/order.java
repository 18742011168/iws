package iws.beans;

//import java.util.List;

import javax.persistence.Entity;
@Entity
public class order {
	protected String orderId;
	//private List<goods> goods;
	protected String goodId;
	protected String state;
	protected String type;
	
	public void setOrderId(String orderId){
		this.orderId =orderId;
	}
	public String getOrderId (){
		return orderId;
	}
	
	public void setGoodId(String goodId) {
		this.goodId=goodId;
	}
	public String getGoodId() {
		return goodId;
	}
	/*
	public void setGoods(List<goods> goods){
	   	 this.goods =goods;
	}
	public List<goods> getGood(){
	   	 return goods;
	}
	*/
	
	public void setState(String state){
		this.state =state;
	}
	public String getState (){
		return state;
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type=type;
	}
}


package iws.beans;

import java.util.List;

import javax.persistence.Entity;
@Entity
public class order {
	private String orderId;
	private List<goods> goods;
	private String state;
	
	public void setOrderId(String orderId){
		this.orderId =orderId;
	}
	public String getOrderId (){
		return orderId;
	}
	
	public void setGoods(List<goods> goods){
	   	 this.goods =goods;
	}
	public List<goods> getGood(){
	   	 return goods;
	}
	
	public void setState(String state){
		this.state =state;
	}
	public String getState (){
		return state;
	}
}


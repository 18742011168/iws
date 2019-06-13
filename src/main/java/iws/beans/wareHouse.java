package iws.beans;

import java.util.List;

import javax.persistence.Entity;

@Entity
public class wareHouse {
	
	private String wareHouseId;
	private List<goods> goods;
	private int volume;
	private int inventory;
	private String kind;
	
	public void setWareHouseId(String wareHouseId) {
		this.wareHouseId=wareHouseId;
	}
	public String getWareHouseId() {
		return this.wareHouseId;
	}
	
	public void setGoods(List<goods> goods) {
		this.goods=goods;
	}
	public List<goods> getGoods(){
		return this.goods;
	}
	
	public void setVolume(int volume) {
		this.volume=volume;
	}
	public int getVolume() {
		return this.volume;
	}
	
	public void setInventory(int inventory) {
		this.inventory=inventory;
	}
	public int getInventory() {
		return this.inventory;
	}
	
	public String getKind() {
		return this.kind;
	}
	public void setKind(String kind) {
		this.kind=kind;
	}

}

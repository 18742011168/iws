package iws.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import iws.DAO.wareHouseDao;
import iws.beans.goods;
import iws.beans.wareHouse;

@Service
public class wareHouseService {
	@Autowired
	private wareHouseDao warehousedao;
	
	public List<wareHouse> allwarehouse(){
		return warehousedao.allwarehouse();
	}
	
	public List<wareHouse> findbyId(String wareHouseId){
		return warehousedao.findbyId(wareHouseId);
	}
	
	public int addwarehouse(wareHouse warehouse) {
		List<wareHouse> warehouselist=warehousedao.findbyId(warehouse.getWareHouseId());
		if(!warehouselist.isEmpty()) {
			System.out.println("仓库已存在");
			   return -1;	
		}
		if(warehousedao.addwarehouse(warehouse)) {
			System.out.println("仓库添加成功");
			   return 1;
		}
		System.out.println("仓库添加失败");
		   return 0;
	}
	
	public int deletewarehouse(String wareHouseId) {
		List<wareHouse> warehouselist=warehousedao.findbyId(wareHouseId);
		if(warehouselist.isEmpty()) {
			System.out.println("仓库不存在");
			   return -1;	
		}
		
		List<goods> goodslist=warehousedao.findgoods(wareHouseId);
		if(!goodslist.isEmpty()) {
			System.out.println("仓库中有货物，不可删除");
			   return -2;	
		}
		if(warehousedao.deletewarehouse(wareHouseId)) {
			System.out.println("仓库删除成功");
			   return 1;
		}
		System.out.println("仓库删除失败");
		   return 0;
	}
	
	
    
}

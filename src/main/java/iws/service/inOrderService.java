package iws.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import iws.DAO.goodsDao;
import iws.DAO.inOrderDao;
import iws.DAO.wareHouseDao;
import iws.beans.goods;
import iws.beans.inOrder;
import iws.beans.wareHouse;

@Service
public class inOrderService {
	@Autowired
	private inOrderDao inorderdao;
	
	@Autowired
	private wareHouseDao warehousedao;
	
	@Autowired
	private goodsDao goodsdao;
	
	//更改订单状态时不用更改货物状态，货物状态在更新货物重量等时更改
	 public boolean updateinorder(String orderId) {
		 
		 return inorderdao.updateorder(orderId);
	 }
	 
	 public boolean deleteinorder(String orderId) {
		 return inorderdao.deleteorder(orderId);
	 }
	 
	 public int addinorder(inOrder inorder) {
		 
		 String wareHouseId=inorder.getNextWarehouseId();
		 String goodId=inorder.getGoodId();
		 String orderId=inorder.getOrderId();
		 
		 
		 List<wareHouse> warehouselist=warehousedao.findbyId(wareHouseId);
		 
		 if(warehouselist.isEmpty()) {
			 System.out.println("库房号错误,请重新选择库房");
			 return -1;
		 }
		 wareHouse warehouse=warehouselist.get(0);
		 if ((warehouse.getVolume()-warehouse.getInventory())<1){
			 System.out.println(warehouse.getWareHouseId()+"库房已满,请重新选择库房");
			 return -1;
		 }
		 
		 
		 List<goods> goodslist=goodsdao.findgoods(goodId);
		 if(goodslist.isEmpty()) {
			 System.out.println("货物不存在");
			 return -2;
		 }
		 goods good=goodslist.get(0);
		if(!(good.getWarehouseId()==null)||good.getState().equals("运输中")) {
			 System.out.println("货物已入库，或在运输中");
			 return -3;
		 }
		
		
		 if(inorderdao.hasorder(orderId)) {
			 System.out.println("订单重复");
			 return -4;
		 }
		 
		 
		 if(inorderdao.addinorder(inorder)) {
			 System.out.println("添加成功");
			goodsdao.updategoods(goodId, "运输中");
			       return 1;
		 }	 
		 
		 System.out.println("添加失败");
		 return 0;				 
     }
	 
	 public List<inOrder> allinorder(){
		 return inorderdao.allinorder();
	 }

}
package iws.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import iws.DAO.changeOrderDao;
import iws.DAO.goodsDao;
import iws.DAO.wareHouseDao;
import iws.beans.changeOrder;
import iws.beans.goods;
import iws.beans.wareHouse;


@Service
public class changeOrderService {
	@Autowired
	private changeOrderDao changeorderdao;
	
	@Autowired
	private goodsDao goodsdao;
	
	@Autowired
	private wareHouseDao warehousedao;
	
	public int updatechangeorder(String orderId) {
		List<changeOrder> orderlist=changeorderdao.findById(orderId);
		if(orderlist.isEmpty()) {
			 System.out.println("订单不存在");
			 return -1;
		}
		
		if(changeorderdao.updateorder(orderId)){
			 System.out.println("订单更新成功");
			 return 1;
		}
		System.out.println("订单更新失败");
		 return 0;
	}
	
	public int deletechangeorder(String orderId) {
		List<changeOrder> orderlist=changeorderdao.findById(orderId);
		if(orderlist.isEmpty()) {
			 System.out.println("订单不存在");
			 return -1;
		}
		
		changeOrder changeorder=orderlist.get(0);
		if(changeorder.getState().equals("未完成")) {
			System.out.println("订单进行中，不可删除");
			 return -2;
		}
		if(changeorderdao.deleteorder(orderId)) {
			System.out.println("订单删除成功");
			 return 1;
		}
		System.out.println("订单删除失败");
		 return 0;
	 }
	
	public int addchangeorder(changeOrder changeorder ) {
		String preWareHouseId=changeorder.getPreWarehouseId();
		String nextWareHouseId=changeorder.getNextWarehouseId();
		String goodId=changeorder.getGoodId();
		String orderId=changeorder.getOrderId();
		
		List<goods> goodslist=goodsdao.findgoods(goodId);
		if(goodslist.isEmpty()) {
			System.out.println("货物不存在");
			 return -1;
		}
		
		goods good=goodslist.get(0);
		if(good.getState().equals("运输中")) {
			System.out.println("货物在运输中");
			return -2;
		}
		
		if(!preWareHouseId.equals(good.getWarehouseId())) {
			System.out.println("货物与原仓库不匹配");
			return -3;
		}
		
		List<wareHouse> warehouselist=warehousedao.findbyId(nextWareHouseId);
		if(warehouselist.isEmpty()) {
			System.out.println("新库房库房号错误,请重新选择库房");
			return -4;
		}
		
		wareHouse warehouse=warehouselist.get(0);
		if ((warehouse.getVolume()-warehouse.getInventory())<1){
			 System.out.println(nextWareHouseId+"库房已满,请重新选择库房");
			 return -4;
		 }
		
		if(changeorderdao.hasorder(orderId)) {
			System.out.println("订单号重复");
			 return -5;
		}
		
		if(changeorderdao.addchangeorder(changeorder)) {
			System.out.println("订单添加成功");
			goodsdao.updategoods(goodId, "运输中");
			return 1;
		}
		System.out.println("订单添加失败");
		 return 0;
	}
	
	 public List<changeOrder> allchangeorder(){
		 return changeorderdao.allchangeorder();
	 }

}

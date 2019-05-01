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
	
	public int updatechangeorder(String orderId,String state) {
		//List<changeOrder> orderlist=changeorderdao.findById(orderId);
		if(!changeorderdao.hasorder(orderId)) {
			 System.out.println("订单不存在");
			 return -1;
		}
		List<changeOrder> orderlist=changeorderdao.findOrder(orderId);
		changeOrder changeorder=orderlist.get(0);
		if("执行中".equals(state)) {
			if(changeorder.getState().equals("已完成")) {
				System.out.println("订单已执行完成");
				return -2;
			}
		}
		if("已完成".equals(state)) {
			List<goods> goodslist=goodsdao.findbyorder(orderId);
			if(!goodslist.isEmpty()) {
				for(int i=0;i<goodslist.size();i++) {
					if("运输中".equals(goodslist.get(i).getState())) {
						System.out.println("有货物在运输中，订单未完成");
						return -3;
					}
				}
			}
		}
		
		if(changeorderdao.updateorder(orderId,state)){
			 System.out.println("订单更新成功");
			 return 1;
		}
		System.out.println("订单更新失败");
		 return 0;
	}
	
	public int deletechangeorder(String orderId) {
		//orderDao中定义的方法，按orderID查找订单（不区分订单类型）
		List<changeOrder> orderlist=changeorderdao.findOrder(orderId);
		List<goods> goodslist=goodsdao.findbyorder(orderId);
		if(!changeorderdao.hasorder(orderId)) {
			 System.out.println("订单不存在");
			 return -1;
		}
		
		changeOrder changeorder=orderlist.get(0);
		if(changeorder.getState().equals("执行中")||"已完成".equals(changeorder.getState())) {
			System.out.println("订单已执行，不可删除");
			 return -2;
		}
		if(changeorderdao.deleteorder(orderId)) {
			System.out.println("订单删除成功");
			
			if(!goodslist.isEmpty()) {
				for(int i=0;i<goodslist.size();i++) {
					goods goods=goodslist.get(i);
					goodsdao.updategoods(goods.getGoodId(), "可移动");
				}
			}
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
		
		List<wareHouse> nextwarehouselist=warehousedao.findbyId(nextWareHouseId);
		List<wareHouse> prewarehouselist=warehousedao.findbyId(preWareHouseId);
		if(nextwarehouselist.isEmpty()) {
			System.out.println("新库房库房号错误,请重新选择库房");
			return -4;
		}
		
		wareHouse nextwarehouse=nextwarehouselist.get(0);
		if ((nextwarehouse.getVolume()-nextwarehouse.getInventory())<1){
			 System.out.println(nextWareHouseId+"库房已满,请重新选择库房");
			 return -4;
		 }
		
		if(changeorderdao.hasorder(orderId)) {
			if(!changeorderdao.findOrder(orderId).get(0).getType().equals("位置变更")) {
				System.out.println("订单重复");
				 return -4;
			}
			if(changeorderdao.hasorder_goods(orderId, goodId)) {
				System.out.println("订单重复");
				return -5;
			}
			if(changeorderdao.addorder_goods(changeorder)) {
				wareHouse prewarehouse=prewarehouselist.get(0);
				int inventory2=nextwarehouse.getInventory()+1;
				int inventroy1=prewarehouse.getInventory()-1;
				warehousedao.updatewarehouse(preWareHouseId, inventroy1);
				warehousedao.updatewarehouse(nextWareHouseId, inventory2);
				goodsdao.updategoods(goodId, "运输中");
				System.out.println("订单添加成功");
				return 1;
			}
			
			
		}
		
		if(changeorderdao.addchangeorder(changeorder)) {
			System.out.println("订单添加成功");
			wareHouse prewarehouse=prewarehouselist.get(0);
			int inventory2=nextwarehouse.getInventory()+1;
			int inventroy1=prewarehouse.getInventory()-1;
			warehousedao.updatewarehouse(preWareHouseId, inventroy1);
			warehousedao.updatewarehouse(nextWareHouseId, inventory2);
			goodsdao.updategoods(goodId, "运输中");
			System.out.println("订单添加成功");
			return 1;
		}
		System.out.println("订单添加失败");
		 return 0;
	}
	
	 public List<changeOrder> allchangeorder(){
		 return changeorderdao.allchangeorder();
	 }
	 
	 public int changeordernumber() {
		 return changeorderdao.changeordernumber();
	 }
	 
	 public List<changeOrder> findorder(String orderId){
		 return changeorderdao.findOrder(orderId);
	 }
	 
	 public int deleteordergoods(String orderId,String goodId) {
		 if(!changeorderdao.hasorder(orderId)) {
			 System.out.println("订单不存在");
			 return -1;
		}
		if(!changeorderdao.hasorder_goods(orderId, goodId)) {
			System.out.println("订单 "+orderId+"中 没有货物 "+goodId);
			return -2;
		}
		if(changeorderdao.deleteordergoods(orderId, goodId)) {
			System.out.println("删除订单 "+orderId+"中 的货物 "+goodId);
			goodsdao.updategoods(goodId, "可移动");
			return 1;
		}
		System.out.println("删除订单 "+orderId+"中 的货物 "+goodId+"失败");
		return 0;
		
	 }

}

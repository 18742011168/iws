package iws.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import iws.DAO.changeOrderDao;
import iws.DAO.goodsDao;
import iws.DAO.wareHouseDao;
import iws.beans.changeOrder;
import iws.beans.goods;
import iws.beans.inOrder;
import iws.beans.outOrder;
import iws.beans.wareHouse;


@Service
public class changeOrderService {
	@Autowired
	private changeOrderDao changeorderdao;
	
	@Autowired
	private goodsDao goodsdao;
	
	@Autowired
	private wareHouseDao warehousedao;
	
	@Autowired
	private inOrderService inorderservice;
	
	@Autowired
	private outOrderService outorderservice;
	
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
			String type=changeorder.getType();
			int size=goodslist.size();
			if(!"入库".equals(type)) {
				String prewarehouseId=changeorder.getPreWarehouseId();
				List<wareHouse> prewarehouselist=warehousedao.findbyId(prewarehouseId);
				wareHouse prewarehouse=prewarehouselist.get(0);
				int inventory=prewarehouse.getInventory()+size;
				warehousedao.updatewarehouse(prewarehouseId, inventory);
				
			}
			if(!"出库".equals(type)) {
				String nextwarehouseId=changeorder.getNextWarehouseId();
				List<wareHouse> nextwarehouselist=warehousedao.findbyId(nextwarehouseId);
				wareHouse nextwarehouse=nextwarehouselist.get(0);
				int inventory=nextwarehouse.getInventory()-size;
				warehousedao.updatewarehouse(nextwarehouseId, inventory);
			}
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
	public String recommendwarehouse(changeOrder changeorder) {
		String goodId=changeorder.getGoodId();
		List<goods> goodslist=goodsdao.findgoods(goodId);
		String category=goodslist.get(0).getCategory();
		List<wareHouse> warehouselist=warehousedao.findbyKind1(category);
		
		for(int i=0;i<warehouselist.size();i++) {
			wareHouse warehouse=warehouselist.get(i);
			if(warehouse.getVolume()-warehouse.getInventory()>1){
				return warehouse.getWareHouseId();
			}

		}
		return "无仓库可推荐";
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
			return -6;
		}
		
		wareHouse nextwarehouse=nextwarehouselist.get(0);
		if ((nextwarehouse.getVolume()-nextwarehouse.getInventory())<1){
			 System.out.println(nextWareHouseId+"库房已满,请重新选择库房");
			 return -4;
		 }
		String category=good.getCategory();
		if(!category.equals(nextwarehouse.getKind())) {
			System.out.println("本仓库存储 "+nextwarehouse.getKind());
			 return -6;
		}
		if(changeorderdao.hasorder(orderId)) {
			changeOrder order=changeorderdao.findOrder(orderId).get(0);
			if(!(order.getType().equals("位置变更")&&nextWareHouseId.equals(order.getNextWarehouseId())&&preWareHouseId.equals(order.getPreWarehouseId()))) {
				System.out.println("订单重复");
				 return -5;
			}
			if(changeorderdao.hasorder_goods(orderId, goodId)||!order.getState().equals("未执行")) {
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
		 List<changeOrder> orderlist=changeorderdao.findOrder(orderId);
		 if(!changeorderdao.hasorder(orderId)) {
			 System.out.println("订单不存在");
			 return -1;
		}
		if(!changeorderdao.hasorder_goods(orderId, goodId)) {
			System.out.println("订单 "+orderId+"中 没有货物 "+goodId);
			return -2;
		}
		if(changeorderdao.deleteordergoods(orderId, goodId)) {
			changeOrder changeorder=orderlist.get(0);
			String type=changeorder.getType();
			if(!"入库".equals(type)) {
				String prewarehouseId=changeorder.getPreWarehouseId();
				List<wareHouse> prewarehouselist=warehousedao.findbyId(prewarehouseId);
				wareHouse prewarehouse=prewarehouselist.get(0);
				int inventory=prewarehouse.getInventory()+1;
				warehousedao.updatewarehouse(prewarehouseId, inventory);
				
			}
			if(!"出库".equals(type)) {
				String nextwarehouseId=changeorder.getNextWarehouseId();
				List<wareHouse> nextwarehouselist=warehousedao.findbyId(nextwarehouseId);
				wareHouse nextwarehouse=nextwarehouselist.get(0);
				int inventory=nextwarehouse.getInventory()-1;
				warehousedao.updatewarehouse(nextwarehouseId, inventory);
			}
			System.out.println("删除订单 "+orderId+"中 的货物 "+goodId);
			goodsdao.updategoods(goodId, "可移动");
			
			return 1;
		}
		System.out.println("删除订单 "+orderId+"中 的货物 "+goodId+"失败");
		return 0;
		
	 }
	 public int intelligence_add_order(String category,String time,String type) {
		 if(category==""||category==null) {
			 if(time==""||time==null)
				 return -2;
			 int first=this.add_order_by_category("铁板", time, type);
			 int second=this.add_order_by_category("铜板", time, type);
			 int third=this.add_order_by_category("钢板", time, type);
			if(first==0&&second==0&&third==0) {
				return 0;
			}
			if(type.equals("入库")) {
				return 1;
			}
			if(type.equals("出库"))
				return 2;
			return -2; 
		 }
		 else {
			 return this.add_order_by_category(category, time, type);
		 }
		 
	 }

	 public int add_order_by_category(String category,String time,String type) {
		 if(type.equals("入库")) {
			 List<goods> goodslist=new ArrayList<goods>();
			 
				goodslist=goodsdao.findbycategory1(category,time);
			
			 
			 if(goodslist.isEmpty()) {
				 System.out.println("不存在符合条件的货物 ");
				 return 0;
			 }
			// System.out.println(category);
			 List<wareHouse> warehouselist=warehousedao.findbyKind1(category);
			
			 int goodsnumber=goodslist.size();
			 wareHouse warehouse=new wareHouse();
			 for(int m=warehouselist.size()-1;m>0;m--) {
				 if(warehouselist.get(m).getVolume()-warehouselist.get(m).getInventory()>=goodsnumber) {
					 warehouse=warehouselist.get(m);
					 break;
				 }
	
			 }
			 if(warehouse==null) {
				 for(int i=0;i<warehouselist.size()&&goodsnumber>0;i++) {
					 warehouse=warehouselist.get(i);
					 int space=warehouse.getVolume()-warehouse.getInventory();
					 for(int j=0;j<space&&goodsnumber>0;j++) {
						 inOrder inorder=new inOrder();
						 inorder.setGoodId(goodslist.get(goodsnumber-1).getGoodId());
						 inorder.setOrderId("自动入库单"+warehouse.getWareHouseId());
						 inorder.setNextWarehouseId(warehouse.getWareHouseId());
						 inorder.setState("未执行");
						 inorder.setType("入库");
						 System.out.println(inorder.getGoodId());
						 System.out.println(inorder.getOrderId());
						 System.out.println(inorder.getNextWarehouseId());
						 System.out.println(inorder.getType());
						 inorderservice.addinorder(inorder);
						 
						 goodsnumber--;
						 System.out.println(goodsnumber);
					 }
				 }
			 }
			 else {
				 int space=warehouse.getVolume()-warehouse.getInventory();
				 for(int j=0;j<space&&goodsnumber>0;j++) {
					 inOrder inorder=new inOrder();
					 inorder.setGoodId(goodslist.get(goodsnumber-1).getGoodId());
					 inorder.setOrderId("自动入库单"+warehouse.getWareHouseId());
					 inorder.setNextWarehouseId(warehouse.getWareHouseId());
					 inorder.setState("未执行");
					 inorder.setType("入库");
					 System.out.println(inorder.getGoodId());
					 System.out.println(inorder.getOrderId());
					 System.out.println(inorder.getNextWarehouseId());
					 System.out.println(inorder.getType());
					 inorderservice.addinorder(inorder);
					 
					 goodsnumber--;
					 System.out.println(goodsnumber);
				 }
			 }
			 
			// System.out.println(goodsnumber);
			// System.out.println(warehouselist.size());
			 
			 if(goodsnumber>0)
				 return -1;
			 else
				 return 1;
		 }
		 if(type.equals("出库")) {
			 
			 List<wareHouse> warehouselist=warehousedao.findbyKind2(category);
			 List<goods> goodslist=goodsdao.findbycategory2(category,time);
			 if(goodslist.isEmpty()) {
				 System.out.println("不存在符合条件的货物 ");
				 return 0;
			 }
			 for(int i=0;i<warehouselist.size();i++) {
				 wareHouse warehouse=warehouselist.get(i);
				 List<goods> warehousegoods=new ArrayList<goods>();
				 
				 warehousegoods=goodsdao.findbywarehouse(warehouselist.get(i).getWareHouseId());
				 
				 int warehousegoodsnumber=warehousegoods.size();
				 if(warehousegoodsnumber>0) {
					 for(int j=0;j<warehousegoodsnumber;j++) {
						 if(time!=null&&time!="") {
							 String inplacetime=time+" 23:59:59";
							 if(warehousegoods.get(j).getInPlaceTime().compareTo(inplacetime)<0) {
								 outOrder outorder=new outOrder();
								 outorder.setGoodId(warehousegoods.get(j).getGoodId());
								 outorder.setOrderId("自动出库单"+warehouse.getWareHouseId());
								 outorder.setState("未执行");
								 outorder.setType("出库");
								 outorder.setPreWarehouseId(warehouse.getWareHouseId());
								 outorderservice.addoutorder(outorder);
								 
							 }
						 }
						 else {
							 outOrder outorder=new outOrder();
							 outorder.setGoodId(warehousegoods.get(j).getGoodId());
							 outorder.setOrderId("自动出库单"+warehouse.getWareHouseId());
							 outorder.setState("未执行");
							 outorder.setType("出库");
							 outorder.setPreWarehouseId(warehouse.getWareHouseId());
							 outorderservice.addoutorder(outorder);
						 }
						 
						 
					 }
				 }
				 
			 }
			 return 2;
		 }
		 return 0;
		 
		 
	 }

}

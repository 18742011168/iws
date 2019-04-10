package iws.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import iws.DAO.goodsDao;
import iws.DAO.outOrderDao;
import iws.DAO.wareHouseDao;
import iws.beans.goods;
import iws.beans.outOrder;
import iws.beans.wareHouse;

@Service
public class outOrderService {
	
	@Autowired
	private outOrderDao outorderdao;
	
	@Autowired
	private goodsDao goodsdao;
	
	@Autowired
	private wareHouseDao warehousedao;
	
	public int updateoutorder(String orderId,String state) {
		List<outOrder> outorderlist=outorderdao.findById(orderId);
		if(outorderlist.isEmpty()) {
			System.out.println("订单不存在");
			return -1;
		}
		
		 if(outorderdao.updateorder(orderId,state)) {
			 System.out.println("订单更新成功");
			 return 1;
		 }
		 System.out.println("订单更新失败");
		 return 0;
	}
	
	public int deleteoutorder(String orderId) {
		List<outOrder> outorderlist=outorderdao.findById(orderId);
		if(outorderlist.isEmpty()) {
			System.out.println("订单不存在");
			return -1;
		}
		outOrder outorder=outorderlist.get(0);
		if(outorder.getState().equals("执行中")||"已完成".equals(outorder.getState())) {
			System.out.println("订单已执行，不可删除");
			 return -2;
		}
		if(outorderdao.deleteorder(orderId)) {
			 System.out.println("订单删除成功");
			 return 1;
		}
		System.out.println("订单删除失败");
		 return 0;
	 }
	
	public int addoutorder (outOrder outorder ) {
		String goodId=outorder.getGoodId();
		String preWareHouseId=outorder.getPreWarehouseId();
		String orderId=outorder.getOrderId();
		List<goods> goodslist=goodsdao.findgoods(goodId);
		List<wareHouse> warehouselist=warehousedao.findbyId(preWareHouseId);
		if(goodslist.isEmpty()) {
			System.out.println("货物不存在");
			 return -1;
		}
		
		goods good=goodslist.get(0);
		if(good.getState().equals("运输中")||good.getWarehouseId()==null) {
			System.out.println("货物 已出库，或在运输中");
			return -2;
		}
		
		if(!preWareHouseId.equals(good.getWarehouseId())) {
			System.out.println("货物与仓库不匹配");
			return -3;
		}
		
		if(outorderdao.hasorder(orderId)) {
			if(outorderdao.hasorder_goods(orderId, goodId)) {
				System.out.println("订单重复");
				 return -4;
			}
			if(outorderdao.addorder_goods(outorder)) {
				System.out.println("订单添加成功");
				goodsdao.updategoods(goodId, "运输中");
				wareHouse warehouse=warehouselist.get(0);
				int inventory=warehouse.getInventory()-1;
				warehousedao.updatewarehouse(preWareHouseId, inventory);
				return 1;
			}
			 
		 }
		
		
		if(outorderdao.addoutorder(outorder)) {
			System.out.println("订单添加成功");
			goodsdao.updategoods(goodId, "运输中");
			wareHouse warehouse=warehouselist.get(0);
			int inventory=warehouse.getInventory()-1;
			warehousedao.updatewarehouse(preWareHouseId, inventory);
			return 1;
		}
		System.out.println("订单添加失败");
		 return 0;	
	}
	
	public List<outOrder> alloutorder(){
		return outorderdao.alloutorder();
	}
	
	public int outordernumber() {
		return outorderdao.outordernumber();
	}
	

	
	

}

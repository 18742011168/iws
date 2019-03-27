package iws.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import iws.DAO.goodsDao;
import iws.DAO.outOrderDao;
import iws.beans.goods;
import iws.beans.outOrder;

@Service
public class outOrderService {
	
	@Autowired
	private outOrderDao outorderdao;
	
	@Autowired
	private goodsDao goodsdao;
	
	public int updateoutorder(String orderId) {
		List<outOrder> outorderlist=outorderdao.findById(orderId);
		if(outorderlist.isEmpty()) {
			System.out.println("订单不存在");
			return -1;
		}
		
		 if(outorderdao.updateorder(orderId)) {
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
		if(outorder.getState().equals("未完成")) {
			System.out.println("订单进行中，不可删除");
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
			 System.out.println("订单重复");
			 return -4;
		 }
		
		
		if(outorderdao.addoutorder(outorder)) {
			System.out.println("订单添加成功");
			goodsdao.updategoods(goodId, "运输中");
			return 1;
		}
		System.out.println("订单添加失败");
		 return 0;	
	}
	
	public List<outOrder> alloutorder(){
		return outorderdao.alloutorder();
	}
	

	
	

}

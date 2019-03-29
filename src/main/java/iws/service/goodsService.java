package iws.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import iws.DAO.goodsDao;
import iws.DAO.warnningDao;
import iws.beans.goods;
import iws.beans.warnning;
import iws.mqtt.MqttGateway;

@Service
public class goodsService {
	
	@Autowired
	private goodsDao goodsdao;
	
	@Autowired
    private MqttGateway mqttGateway;
	
	@Autowired
	private warnningDao warnningdao;
	
	
	public List<goods> allgoods(){
		return goodsdao.allgoods();
	}
	
	public List<goods> findgoods(String goodId){
		return goodsdao.findgoods(goodId);
	}
	
	public int addgoods(goods goods) {
		List<goods> goodslist=goodsdao.findgoods(goods.getGoodId());
		if(!goodslist.isEmpty()) {
			System.out.println("货物编号重复");
			return -1;
		}
		if(goodsdao.addgoods(goods)) {
			System.out.println("货物添加成功");
			return 1;
		}
		System.out.println("货物添加失败");
		return 0;
	}
	
	public int deletegoods(String goodId) {
		List<goods> goodslist=goodsdao.findgoods(goodId);
		if(goodslist.isEmpty()) {
			System.out.println("货物不存在");
			return -1;
		}
		goods good=goodslist.get(0);
		if(good.getState().equals("运输中")) {
			System.out.println("货物运输中，不可删除");
			return -2;
		}
		if(goodsdao.deletegoods(goodId)) {
			System.out.println("货物删除成功");
			return 1;
		}
		System.out.println("货物删除失败");
		return 0;
		
	}
	//下发订单时使用，保证同一个货物不会同时出现在不同订单中
	public int updategoods(String goodId,String state) {
		List<goods> goodslist=goodsdao.findgoods(goodId);
		if(goodslist.isEmpty()) {
			System.out.println("货物不存在");
			return -1;
		}
		
		if(goodsdao.updategoods(goodId, state)) {
			System.out.println("货物状态更改成功");
			return 1;
		}
		System.out.println("货物状态更新失败");
		return 0;
	}
	
	//货物出库或入库完后使用，填入货物信息，并更改货物状态
	public int updategoods(String goodId,Double weight,String warehousId,String state,String orderId) {
		List<goods> ordergoods=goodsdao.findbyorder(orderId);
		List<goods> goodslist=goodsdao.findgoods(goodId);
		if(goodslist.isEmpty()) {
			System.out.println("货物不存在");
			return -1;
		}
		goods good=goodslist.get(0);
		if(!ordergoods.contains(good)) {
			warnning warnning=new warnning();
			warnning.setGoodId(goodId);
			warnning.setMessage("非法出库/入库");
			warnningdao.addwarnning(warnning);
			String message=goodId+"非法出库或入库";
			String topic="warnning";
			mqttGateway.sendToMqtt(message, topic);
		}
		if(goodsdao.updategoods(goodId, weight, warehousId, state)) {
			System.out.println("货物信息更新成功");
			return 1;
		}
		System.out.println("货物信息更新失败");
		return 0;
	}
	
	//货物位置变更后使用，只改变货物位置、状态
	public int updategoods(String goodId,String warehousId,String state,String orderId) {
		List<goods> ordergoods=goodsdao.findbyorder(orderId);
		List<goods> goodslist=goodsdao.findgoods(goodId);
		if(goodslist.isEmpty()) {
			System.out.println("货物不存在");
			return -1;
		}
		goods good=goodslist.get(0);
		if(!ordergoods.contains(good)) {
			warnning warnning=new warnning();
			warnning.setGoodId(goodId);
			warnning.setMessage("非法位置变更");
			warnningdao.addwarnning(warnning);
			String message=goodId+"非法移动";
			String topic="warnning";
			mqttGateway.sendToMqtt(message, topic);
		}
		if(goodsdao.updategoods(goodId, warehousId, state)) {
			System.out.println("货物信息更新成功");
			return 1;
		}
		System.out.println("货物信息更新失败");
		return 0;
	}
	
	 public List<goods> findbyorder(String orderId){
		 return goodsdao.findbyorder(orderId);
	 }
	 
	 public List<goods> findbywarehouse(String warehouseid){
		 return goodsdao.findbywarehouse(warehouseid);
	 }

}

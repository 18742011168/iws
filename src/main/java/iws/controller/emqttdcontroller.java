package iws.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import iws.mqtt.MqttGateway;
import iws.service.goodsService;
@RestController

public class emqttdcontroller {

	@Autowired
    private MqttGateway mqttGateway;
	 @RequestMapping("/emqttd")
	    public String sendMqtt(){
	        mqttGateway.sendToMqtt("12367","hello");
	   	        return "OK";
	    }
	
	 @Autowired
	 private goodsService goodsservice;
	 @RequestMapping("/updategoods")
	 public String updategoods(String goodId,Double weight,String warehouseId,String state,String orderId) {
		 if(goodsservice.updategoods(goodId, weight, warehouseId, state, orderId)==1) {
			 System.out.println("更新成功");
			 return "hello";
		 }
		 System.out.println("更新失败");
		 return "hello";
	 }
}

package iws.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import iws.mqtt.MqttGateway;

@RestController

public class emqttdcontroller {

	@Autowired
    private MqttGateway mqttGateway;
	 @RequestMapping("/emqttd")
	    public String sendMqtt(){
	        mqttGateway.sendToMqtt("12367","hello");
	   	        return "OK";
	    }
	
}

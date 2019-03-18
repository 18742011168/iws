package iws.mqtt;

import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;

@Configuration
@IntegrationComponentScan
public class MqttReceiveConfig {
	
	@Value("${spring.mqtt.username}")
    private String username;
 
    @Value("${spring.mqtt.password}")
    private String password;
 
    @Value("${spring.mqtt.url}")
    private String hostUrl;
 
    @Value("${spring.mqtt.client.id}")
    private String clientId;
 
    @Value("${spring.mqtt.default.topic}")
    private String defaultTopic;
 
    @Value("${spring.mqtt.completionTimeout}")
    private int completionTimeout ;
    
    @Bean
    public MqttConnectOptions getReceiveMqttConnectOptions(){
        MqttConnectOptions mqttConnectOptions=new MqttConnectOptions();
        mqttConnectOptions.setUserName(username);
        mqttConnectOptions.setPassword(password.toCharArray());
        mqttConnectOptions.setServerURIs(new String[]{hostUrl});
        mqttConnectOptions.setKeepAliveInterval(2);
        return mqttConnectOptions;
    }
    
    @Bean
    public MqttPahoClientFactory mqttReceiveClientFactory() {
        DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
        factory.setConnectionOptions(getReceiveMqttConnectOptions());
        return factory;
    }
    
    @Bean
    public MessageChannel mqttInputChannel() {
        return new DirectChannel();
    }
    
    @Bean
    public MessageProducer inbound() {
        MqttPahoMessageDrivenChannelAdapter adapter =
                new MqttPahoMessageDrivenChannelAdapter(clientId+"receive", mqttReceiveClientFactory(),
                        "hello","hello1");
        adapter.setCompletionTimeout(completionTimeout);
        adapter.setConverter(new DefaultPahoMessageConverter());
        adapter.setQos(2);
        adapter.setOutputChannel(mqttInputChannel());
        return adapter;
    }
    
    @Bean
    @ServiceActivator(inputChannel = "mqttInputChannel")
    public MessageHandler ReceiveHandler() {
        return new MessageHandler() {
            @Override
            public void handleMessage(Message<?> message) throws MessagingException {
            	
            	System.out.println(message.getPayload().toString());
                /*String topic = message.getHeaders().get("mqtt_receivedTopic").toString();
            
				String type = topic.substring(topic.lastIndexOf("/")+1, topic.length());
                if("hello".equalsIgnoreCase(topic)){
                    System.out.println("hello,fuckXX,"+message.getPayload().toString());
                }else if("hello1".equalsIgnoreCase(topic)){
                    System.out.println("hello1,fuckXX,"+message.getPayload().toString());
                }*/
            }
        };
    }












}

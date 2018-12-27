package fr.seblaporte.assistantdomoticz.mqtt;

import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.stereotype.Component;

@Component
@MessagingGateway(defaultRequestChannel = "mqttOutboundChannel")
public interface DomoticzGateway {

    void sendToMqtt(String data);
}

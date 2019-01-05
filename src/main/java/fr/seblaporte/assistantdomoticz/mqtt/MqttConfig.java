package fr.seblaporte.assistantdomoticz.mqtt;

import fr.seblaporte.assistantdomoticz.service.ReportState;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

import java.io.IOException;

@Configuration
@IntegrationComponentScan
public class MqttConfig {

    private Logger logger = LoggerFactory.getLogger(MqttConfig.class);

    private final ReportState reportStateService;
    private final String mqttAddress;
    private final String mqttPort;
    private final String mqttUsername;
    private final String mqttPassword;

    public MqttConfig(
            @Value("${mqtt.address}") String mqttAddress,
            @Value("${mqtt.port}") String mqttPort,
            @Value("${mqtt.username}") String mqttUsername,
            @Value("${mqtt.password}") String mqttPassword,
            ReportState reportStateService) {
        this.reportStateService = reportStateService;
        this.mqttAddress = mqttAddress;
        this.mqttPort = mqttPort;
        this.mqttUsername = mqttUsername;
        this.mqttPassword = mqttPassword;
    }

    @Bean
    public MqttPahoClientFactory mqttClientFactory() {
        DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
        MqttConnectOptions options = new MqttConnectOptions();
        options.setServerURIs(new String[] { String.format("tcp://%s:%s", mqttAddress, mqttPort) });
        options.setAutomaticReconnect(true);
        options.setUserName(mqttUsername);
        options.setPassword(mqttPassword.toCharArray());
        options.setCleanSession(true);
        options.setConnectionTimeout(10);
        options.setMaxInflight(20);
        factory.setConnectionOptions(options);
        return factory;
    }

    @Bean
    @ServiceActivator(inputChannel = "mqttOutboundChannel")
    public MessageHandler mqttOutbound() {
        MqttPahoMessageHandler messageHandler = new MqttPahoMessageHandler("assistant-domoticz-report-state", mqttClientFactory());
        messageHandler.setAsync(true);
        messageHandler.setDefaultTopic("domoticz/in");
        messageHandler.setDefaultQos(1);
        return messageHandler;
    }

    @Bean
    public MessageChannel mqttOutboundChannel() {
        return new DirectChannel();
    }

    @Bean
    public MessageProducer inbound() {
        MqttPahoMessageDrivenChannelAdapter adapter =
                new MqttPahoMessageDrivenChannelAdapter(
                        "assistant-domoticz-execute",
                        mqttClientFactory(),
                        "domoticz/out");
        adapter.setCompletionTimeout(5000);
        adapter.setConverter(new DefaultPahoMessageConverter());
        adapter.setQos(1);
        adapter.setOutputChannel(mqttInputChannel());
        return adapter;
    }

    @Bean
    public MessageChannel mqttInputChannel() {
        return new DirectChannel();
    }

    @Bean
    @ServiceActivator(inputChannel = "mqttInputChannel")
    public MessageHandler handler() {

        return message -> {

            logger.info("MQTT Domoticz input : " + message.getPayload().toString());

                try {
                    reportStateService.call(message.getPayload().toString());
                } catch (IOException e) {
                    logger.error("Error during report state for device", e);
                }

        };
    }



}

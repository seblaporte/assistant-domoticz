package fr.seblaporte.assistantdomoticz.service;

import fr.seblaporte.assistantdomoticz.mqtt.DomoticzGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MqttDomoticzService {

    private final DomoticzGateway domoticzGateway;

    @Autowired
    public MqttDomoticzService(DomoticzGateway domoticzGateway) {
        this.domoticzGateway = domoticzGateway;
    }

    public void controlOnOff(String deviceId, boolean on) {
        final String onOff = on ? "On" : "Off";
        final String json = String.format("{ \"command\": \"switchlight\", \"idx\" : %s, \"switchcmd\": \"%s\"}",
                deviceId, onOff);
        domoticzGateway.sendToMqtt(json);
    }

    public void controlBrightness(String deviceId, int brightness) {
        final String json = String.format("{ \"command\": \"switchlight\", \"idx\" : %s, \"switchcmd\": \"Set Level\", \"level\": %d}",
                deviceId, brightness);
        domoticzGateway.sendToMqtt(json);
    }
}

package fr.seblaporte.assistantdomoticz.service;

import fr.seblaporte.assistantdomoticz.util.HttpUtils;
import org.apache.http.HttpEntity;
import org.apache.http.entity.StringEntity;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.UUID;

@Service
public class ReportState {

    private Logger logger = LoggerFactory.getLogger(ReportState.class);

    private final GoogleAccountService googleAccountService;
    private final String agentUserId;

    public ReportState(@Value("${assistant.agent.userid}") String agentUserId,
                       GoogleAccountService googleAccountService) {
        this.googleAccountService = googleAccountService;
        this.agentUserId = agentUserId;
    }

    public void call(String payloadFromDomoticz) throws IOException {

        final String jwt = googleAccountService.getJwt();
        final String accessToken = googleAccountService.getAccessToken(jwt);

        final JSONObject json = new JSONObject(payloadFromDomoticz);

        String deviceId = String.valueOf(json.getLong("idx"));
        Boolean on = json.getInt("nvalue") == 1;
        Integer brightness = json.has("Level") ? json.getInt("Level") : null;

        JSONObject reportStateJson = prepareState(deviceId, on, brightness);
        StringEntity params = new StringEntity(reportStateJson.toString());

        HttpEntity httpEntity = HttpUtils.httpRequest("https://homegraph.googleapis.com/v1/devices:reportStateAndNotification",
                accessToken, params, "application/json");

        logger.info("Response : " + HttpUtils.readResponse(httpEntity).toString());
    }

    private JSONObject prepareState(String deviceId, Boolean on, Integer brightness) {

        JSONObject json = new JSONObject();
        json.put("requestId", UUID.randomUUID());
        json.put("agentUserId", agentUserId);

        JSONObject payload = new JSONObject();
        JSONObject devices = new JSONObject();
        JSONObject states = new JSONObject();
        JSONObject device = new JSONObject();

        device.put("on", on);
        if (brightness != null) {
            device.put("brightness", brightness);
        }

        states.put(deviceId, device);

        devices.put("states", states);
        payload.put("devices", devices);
        json.put("payload", payload);

        logger.info("Report state generated : " + json.toString());

        return json;
    }


}

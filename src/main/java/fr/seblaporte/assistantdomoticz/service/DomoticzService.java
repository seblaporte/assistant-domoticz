package fr.seblaporte.assistantdomoticz.service;

import fr.seblaporte.assistantdomoticz.DTO.domoticz.*;
import fr.seblaporte.assistantdomoticz.DTO.google.DeviceCommandEnum;
import fr.seblaporte.assistantdomoticz.DTO.google.DeviceDTO;
import fr.seblaporte.assistantdomoticz.DTO.google.StateEnum;
import fr.seblaporte.assistantdomoticz.exception.DomoticzApiCallException;
import fr.seblaporte.assistantdomoticz.util.DomoticzApiCall;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DomoticzService {

    @Value("${domoticz.url}")
    private String domoticzUrl;

    Logger logger = LoggerFactory.getLogger(DomoticzService.class);

    private RestTemplate restTemplate;

    public DomoticzService() {
        restTemplate = new RestTemplate();
    }

    public List<DomoticzDeviceDTO> getDevices() throws DomoticzApiCallException {

        List<DomoticzDeviceDTO> domoticzDevices = getDevicesFromDomoticz();

        final Map<String, String> roomNames = getRoomNames();

        domoticzDevices.forEach(domoticzDeviceDTO -> {
            if (!domoticzDeviceDTO.getRoom().isEmpty()) {
                final String roomId = domoticzDeviceDTO.getRoom();
                final String roomName = roomNames.get(roomId);
                domoticzDeviceDTO.setRoom(roomName);
            }
        });

        return domoticzDevices;
    }

    private Map<String, String> getRoomNames() throws DomoticzApiCallException {

        Map<String, String> roomNames = new HashMap<>();

        final List<DomoticzPlanDTO> plansFromDomoticz = getPlansFromDomoticz();

        plansFromDomoticz.forEach(domoticzPlanDTO -> {
            roomNames.put(domoticzPlanDTO.getId(), domoticzPlanDTO.getName());
        });

        return roomNames;
    }

    private List<DomoticzDeviceDTO> getDevicesFromDomoticz() throws DomoticzApiCallException {

        final String url = domoticzUrl + "/json.htm?type=devices&used=true&filter=all&favorite=1";

        final ResponseEntity<DomoticzDevicesResponseDTO> response = restTemplate.getForEntity(url, DomoticzDevicesResponseDTO.class);

        if (response.getStatusCode().equals(HttpStatus.OK)) {

            logger.info("Getting devices list from " + domoticzUrl);

            if (response.getBody() == null) {
                throw new DomoticzApiCallException("Body is empty", response.getStatusCode());
            }

            return response.getBody().getResult();
        }

        throw new DomoticzApiCallException("Error during devices listing", response.getStatusCode());
    }

    private List<DomoticzPlanDTO> getPlansFromDomoticz() throws DomoticzApiCallException {

        final String url = domoticzUrl + "/json.htm?type=plans&order=name&used=true";

        final ResponseEntity<DomoticzPlanResponseDTO> response = restTemplate.getForEntity(url, DomoticzPlanResponseDTO.class);

        if (response.getBody() == null) {
            throw new DomoticzApiCallException("Body is empty", response.getStatusCode());
        }

        return response.getBody().getResult();
    }

    public boolean executeCommand(DeviceDTO device, DeviceCommandEnum command, Map<StateEnum, Object> params) throws DomoticzApiCallException {

        URI apiUrl;
        ResponseEntity<DomoticzCommandResponseDTO> response;

        switch (command) {

            case ON_OFF:

                final boolean on = (boolean) params.get(StateEnum.ON);

                apiUrl = DomoticzApiCall.createUrlOnOff(domoticzUrl, device.getId(), on);
                response = restTemplate.getForEntity(apiUrl, DomoticzCommandResponseDTO.class);

                return DomoticzApiCall.checkResponseFromDomoticzApi(response);

            case BRIGHTNESS:

                final int brightness = (int) params.get(StateEnum.BRIGHTNESS);

                if (device.getDeviceInfo().getModel().equals(DomoticzHardwareEnum.RFXCOM.toString())) {
                    apiUrl = DomoticzApiCall.createUrlDimmable(domoticzUrl, device.getId(), brightness);
                } else if (device.getDeviceInfo().getModel().equals(DomoticzHardwareEnum.MI_LIGHT.toString())) {
                    apiUrl = DomoticzApiCall.createUrlFullyDimmable(domoticzUrl, device.getId(), brightness);
                } else {
                    apiUrl = DomoticzApiCall.createUrlFullyDimmable(domoticzUrl, device.getId(), brightness);
                }

                response = restTemplate.getForEntity(apiUrl, DomoticzCommandResponseDTO.class);

                return DomoticzApiCall.checkResponseFromDomoticzApi(response);

            case COLOR:

                final Map<String, Object> color = (Map<String, Object>) params.get(StateEnum.COLOR);
                final int colorSpectrumRgb = (int) color.get("spectrumRGB");

                apiUrl = DomoticzApiCall.createUrlColor(domoticzUrl, device.getId(), colorSpectrumRgb);
                response = restTemplate.getForEntity(apiUrl, DomoticzCommandResponseDTO.class);

                return DomoticzApiCall.checkResponseFromDomoticzApi(response);
        }

        return false;
    }

}

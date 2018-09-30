package fr.seblaporte.assistantdomoticz.service;

import fr.seblaporte.assistantdomoticz.DTO.domoticz.DeviceDomoticzDTO;
import fr.seblaporte.assistantdomoticz.DTO.domoticz.ResponseDTO;
import fr.seblaporte.assistantdomoticz.exception.DomoticzApiCallException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class DomoticzService {

    @Value("${domoticz.url}")
    private String domoticzUrl;

    Logger logger = LoggerFactory.getLogger(DomoticzService.class);

    private RestTemplate restTemplate;

    public DomoticzService() {
        restTemplate = new RestTemplate();
    }

    public List<DeviceDomoticzDTO> getDevices() throws DomoticzApiCallException {

        final String url = domoticzUrl + "/json.htm?type=devices&used=true&filter=all&favorite=1";

        ResponseEntity<ResponseDTO> response = restTemplate.getForEntity(url, ResponseDTO.class);

        if (response.getStatusCode().equals(HttpStatus.OK)) {

            logger.info("Getting devices list from " + domoticzUrl);

            if (response.getBody() == null) {
                throw new DomoticzApiCallException("Body is empty", response.getStatusCode());
            }

            return response.getBody().getResult();
        }

        throw new DomoticzApiCallException("Error during devices listing", response.getStatusCode());
    }
}

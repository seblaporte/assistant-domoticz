package fr.seblaporte.assistantdomoticz.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import fr.seblaporte.assistantdomoticz.DTO.google.request.RequestDTO;
import fr.seblaporte.assistantdomoticz.DTO.google.request.RequestInputsDTO;
import fr.seblaporte.assistantdomoticz.DTO.google.response.ResponseDTO;
import fr.seblaporte.assistantdomoticz.DTO.google.response.ResponsePayloadSyncDTO;
import fr.seblaporte.assistantdomoticz.exception.DomoticzApiCallException;
import fr.seblaporte.assistantdomoticz.service.DomoticzService;
import fr.seblaporte.assistantdomoticz.util.DomoticzApiConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController()
public class Smarthome {

    Logger logger = LoggerFactory.getLogger(Smarthome.class);

    final DomoticzService domoticzService;

    @Autowired
    public Smarthome(DomoticzService domoticzService) {
        this.domoticzService = domoticzService;
    }

    @PostMapping(path = "/smarthome")
    public ResponseDTO smarthome(@RequestBody RequestDTO request) throws DomoticzApiCallException {

        for (RequestInputsDTO input : request.getInputs()) {

            switch (input.getIntent()) {

                case "action.devices.SYNC":

                    logger.info("Intent: SYNC");

                    ResponsePayloadSyncDTO syncPayloadDTO = new ResponsePayloadSyncDTO();
                    syncPayloadDTO.setAgentUserId("1836.15267389");
                    syncPayloadDTO.setDevices(DomoticzApiConverter.convertDevices(domoticzService.getDevices()));

                    ResponseDTO response = new ResponseDTO();
                    response.setRequestId(request.getRequestId());
                    response.setPayload(syncPayloadDTO);

                    logger.debug(getJsonPrettyPrintedFromObject(response));

                    return response;

                case "action.devices.QUERY":
                    logger.info("Intent: QUERY");

                    break;
                case "action.devices.EXECUTE":
                    logger.info("Intent: EXECUTE");

                    break;
            }
        }

        return null;
    }

    private String getJsonPrettyPrintedFromObject(Object object) {

        String json = "";

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);

        try {
            json = mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            logger.error("Object serialization error", e);
        }

        return json;
    }

}

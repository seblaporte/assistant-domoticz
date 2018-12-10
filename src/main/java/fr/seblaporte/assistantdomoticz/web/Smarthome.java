package fr.seblaporte.assistantdomoticz.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import fr.seblaporte.assistantdomoticz.DTO.domoticz.DomoticzDeviceDTO;
import fr.seblaporte.assistantdomoticz.DTO.google.DeviceCommandEnum;
import fr.seblaporte.assistantdomoticz.DTO.google.DeviceDTO;
import fr.seblaporte.assistantdomoticz.DTO.google.StateEnum;
import fr.seblaporte.assistantdomoticz.DTO.google.request.RequestDTO;
import fr.seblaporte.assistantdomoticz.DTO.google.request.RequestInputsDTO;
import fr.seblaporte.assistantdomoticz.DTO.google.request.RequestPayloadExecuteCommandsDTO;
import fr.seblaporte.assistantdomoticz.DTO.google.response.*;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController()
public class Smarthome {

    private Logger logger = LoggerFactory.getLogger(Smarthome.class);

    private final DomoticzService domoticzService;

    @Autowired
    public Smarthome(DomoticzService domoticzService) {
        this.domoticzService = domoticzService;
    }

    @GetMapping(path = "/smarthome")
    public String hello() {
        return "Hello";
    }

    @PostMapping(path = "/smarthome")
    public ResponseDTO smarthome(@RequestBody RequestDTO request) throws DomoticzApiCallException {

        for (RequestInputsDTO input : request.getInputs()) {

            switch (input.getIntent()) {

                case "action.devices.SYNC":

                    logger.info("Intent: SYNC");

                    return sync(request.getRequestId());

                case "action.devices.QUERY":

                    logger.info("Intent: QUERY");

                    return query(request.getRequestId(), input);

                case "action.devices.EXECUTE":

                    logger.info("Intent: EXECUTE");

                    return execute(request.getRequestId(), input);
            }
        }

        return null;
    }

    private ResponseDTO sync(String requestId) throws DomoticzApiCallException {

        ResponsePayloadSyncDTO syncPayloadDTO = new ResponsePayloadSyncDTO();
        syncPayloadDTO.setAgentUserId("1836.15267389");

        List<DeviceDTO> devices = DomoticzApiConverter.convertDevices(domoticzService.getDevices());

        syncPayloadDTO.setDevices(devices);

        ResponseDTO response = new ResponseDTO();
        response.setRequestId(requestId);
        response.setPayload(syncPayloadDTO);

        logger.info(getJsonPrettyPrintedFromObject(response));

        return response;
    }

    private ResponseDTO query(String requestId, RequestInputsDTO input) throws DomoticzApiCallException {

        ResponsePayloadQueryDTO queryPayloadDTO = new ResponsePayloadQueryDTO();
        Map<String, Map<String, Object>> deviceStatus = new HashMap<>();

        for (DeviceDTO device : input.getPayload().getDevices()) {
            DomoticzDeviceDTO deviceFromDomoticz = domoticzService.getDeviceFromDomoticzById(device.getId());
            deviceStatus.put(deviceFromDomoticz.getDeviceId(), DomoticzApiConverter.convertDeviceStatus(deviceFromDomoticz));
        }

        queryPayloadDTO.setDevices(deviceStatus);

        ResponseDTO response = new ResponseDTO();
        response.setRequestId(requestId);
        response.setPayload(queryPayloadDTO);

        logger.info(getJsonPrettyPrintedFromObject(response));

        return response;
    }

    private ResponseDTO execute(String requestId, RequestInputsDTO input) throws DomoticzApiCallException {

        List<Command> commands = new ArrayList<>();

        for (RequestPayloadExecuteCommandsDTO executeCommand : input.getPayload().getCommands()) {

            final DeviceCommandEnum requestedCommand = executeCommand.getExecution().get(0).getCommand();
            final Map<StateEnum, Object> params = executeCommand.getExecution().get(0).getParams();

            for (DeviceDTO device : executeCommand.getDevices()) {

                final boolean sucess = domoticzService.executeCommand(device, requestedCommand, params);

                if (sucess) {
                    commands.add(new Command(device.getId(), DeviceStatusEnum.SUCCESS));
                } else {
                    commands.add(new Command(device.getId(), DeviceStatusEnum.ERROR));
                }

            }
        }

        ResponsePayloadExecuteDTO responsePayloadExecute = new ResponsePayloadExecuteDTO();
        responsePayloadExecute.setCommands(commands);

        ResponseDTO response = new ResponseDTO();
        response.setRequestId(requestId);
        response.setPayload(responsePayloadExecute);

        logger.info(getJsonPrettyPrintedFromObject(response));

        return response;
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

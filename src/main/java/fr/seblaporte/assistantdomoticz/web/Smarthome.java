package fr.seblaporte.assistantdomoticz.web;

import fr.seblaporte.assistantdomoticz.DTO.Inputs;
import fr.seblaporte.assistantdomoticz.DTO.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController()
public class Smarthome {

    Logger logger = LoggerFactory.getLogger(Smarthome.class);

    @GetMapping
    public String hello() {

        return "Hello !";
    }

    @PostMapping(path = "/smarthome")
    public String smarthome(@RequestBody Request request) {

        for (Inputs input : request.getInputs()) {

            switch (input.getIntent()) {
                case "action.devices.SYNC":
                    logger.info("Intent: SYNC");

                    break;
                case "action.devices.QUERY":
                    logger.info("Intent: QUERY");

                    break;
                case "action.devices.EXECUTE":
                    logger.info("Intent: EXECUTE");

                    break;
            }
        }

        return request.getRequestId();
    }
}

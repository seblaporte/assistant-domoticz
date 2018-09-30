package fr.seblaporte.assistantdomoticz.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

public class DomoticzApiCallException extends Exception {

    Logger logger = LoggerFactory.getLogger(DomoticzApiCallException.class);

    public DomoticzApiCallException(String message, HttpStatus httpStatus) {
        logger.error("Erreur lors de l'appel de l'API Domoticz : " + message);
    }
}

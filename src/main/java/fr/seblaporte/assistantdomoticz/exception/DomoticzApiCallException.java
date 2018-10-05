package fr.seblaporte.assistantdomoticz.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

public class DomoticzApiCallException extends Exception {

    Logger logger = LoggerFactory.getLogger(DomoticzApiCallException.class);

    private static final String ERREUR_APPEL_API = "Erreur lors de l'appel de l'API Domoticz : ";

    public DomoticzApiCallException(String message, HttpStatus httpStatus) {
        logger.error(ERREUR_APPEL_API + message);
    }

    public DomoticzApiCallException(String message, Exception e) {
        logger.error(ERREUR_APPEL_API + message, e);
    }
}

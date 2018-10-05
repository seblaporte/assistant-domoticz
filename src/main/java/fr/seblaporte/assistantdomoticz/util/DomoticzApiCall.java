package fr.seblaporte.assistantdomoticz.util;

import fr.seblaporte.assistantdomoticz.DTO.domoticz.DomoticzCommandResponseDTO;
import fr.seblaporte.assistantdomoticz.exception.DomoticzApiCallException;
import org.springframework.http.ResponseEntity;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class DomoticzApiCall {

    public static final String ERROR_CREATE_URI = "Error during URI creation for Domoticz API.";

    public static boolean checkResponseFromDomoticzApi(ResponseEntity<DomoticzCommandResponseDTO> response) throws DomoticzApiCallException {

        if (response.getBody() == null) {
            throw new DomoticzApiCallException("Body is empty", response.getStatusCode());
        }

        return response.getBody().getStatus().equals("OK");
    }

    public static URI createUrlOnOff(String domoticzUrl, String deviceId, boolean on) throws DomoticzApiCallException {

        final String switchCmd = on ? "On" : "Off";

        try {
            return new URI(domoticzUrl + String.format("/json.htm?type=command&param=switchlight&idx=%s&switchcmd=%s", deviceId, switchCmd));
        } catch (URISyntaxException e) {
            throw new DomoticzApiCallException(ERROR_CREATE_URI, e);
        }
    }

    public static URI createUrlFullyDimmable(String domoticzUrl, String deviceId, int level) throws DomoticzApiCallException {

        try {
            return new URI(domoticzUrl + String.format("/json.htm?type=command&param=switchlight&idx=%s&switchcmd=Set%%20Level&level=%d", deviceId, level));
        } catch (URISyntaxException e) {
            throw new DomoticzApiCallException(ERROR_CREATE_URI, e);
        }
    }

    public static URI createUrlDimmable(String domoticzUrl, String deviceId, int brightness) throws DomoticzApiCallException {

        final long level = Math.round(brightness * 0.16);

        return createUrlFullyDimmable(domoticzUrl, deviceId, Math.toIntExact(level));
    }

    public static URI createUrlColor(String domoticzUrl, String deviceId, int spectrum) throws DomoticzApiCallException {

        final String hexaSpectrum = Integer.toHexString(spectrum);

        try {
            return new URI(domoticzUrl + String.format("/json.htm?type=command&param=setcolbrightnessvalue&idx=%s&hex=%s", deviceId, hexaSpectrum));
        } catch (URISyntaxException e) {
            throw new DomoticzApiCallException(ERROR_CREATE_URI, e);
        }
    }
}

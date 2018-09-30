package fr.seblaporte.assistantdomoticz.DTO.domoticz;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum HardwareEnum {

    @JsonProperty("Dummy (Does nothing, use for virtual switches only)")
    DUMMY,
    @JsonProperty("RFXCOM - RFXtrx433 USB 433.92MHz Transceiver")
    RFXCOM,
    @JsonProperty("Limitless/AppLamp/Mi Light with LAN/WiFi interface")
    MI_LIGHT
}

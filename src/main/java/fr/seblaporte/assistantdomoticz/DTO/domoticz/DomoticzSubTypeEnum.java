package fr.seblaporte.assistantdomoticz.DTO.domoticz;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum DomoticzSubTypeEnum {

    @JsonProperty("RGBW")
    RGBW,
    @JsonProperty("RGB")
    RGB,
    @JsonProperty("AC")
    AC,

}

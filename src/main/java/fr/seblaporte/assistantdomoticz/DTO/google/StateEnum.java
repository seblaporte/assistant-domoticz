package fr.seblaporte.assistantdomoticz.DTO.google;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum StateEnum {

    @JsonProperty("on")
    ON,
    @JsonProperty("brightness")
    BRIGHTNESS,
    @JsonProperty("color")
    COLOR,

}

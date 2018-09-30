package fr.seblaporte.assistantdomoticz.DTO.google;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum DeviceTraitEnum {

    @JsonProperty("action.devices.traits.OnOff")
    ON_OFF,
    @JsonProperty("action.devices.traits.Brightness")
    BRIGHTNESS,
    @JsonProperty("action.devices.traits.ColorTemperature")
    COLOR_TEMPERATURE,
    @JsonProperty("action.devices.traits.ColorSpectrum")
    COLOR_SPECTRUM
}

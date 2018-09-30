package fr.seblaporte.assistantdomoticz.DTO.domoticz;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class DeviceDomoticzDTO {

    @JsonProperty("ID")
    private String id;

    @JsonProperty("idx")
    private String deviceId;

    @JsonProperty("Name")
    private String name;

    @JsonProperty("SwitchType")
    private SwitchTypeEnum switchType;

    @JsonProperty("HardwareName")
    private String hardwareName;

    @JsonProperty("HardwareType")
    private HardwareEnum hardwareType;

    @JsonProperty("Image")
    private DeviceTypeDomoticzEnum deviceCategory;

}

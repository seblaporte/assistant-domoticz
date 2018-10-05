package fr.seblaporte.assistantdomoticz.DTO.domoticz;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class DomoticzDeviceDTO {

    @JsonProperty("ID")
    private String id;

    @JsonProperty("idx")
    private String deviceId;

    @JsonProperty("Name")
    private String name;

    @JsonProperty("Description")
    private String description;

    @JsonProperty("SwitchType")
    private DomoticzSwitchTypeEnum switchType;

    @JsonProperty("HardwareName")
    private String hardwareName;

    @JsonProperty("HardwareType")
    private DomoticzHardwareEnum hardwareType;

    @JsonProperty("Image")
    private DomoticzDeviceTypeEnum deviceCategory;

    @JsonProperty("PlanID")
    private String room;
}

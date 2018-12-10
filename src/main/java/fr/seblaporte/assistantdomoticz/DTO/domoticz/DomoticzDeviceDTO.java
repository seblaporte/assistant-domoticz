package fr.seblaporte.assistantdomoticz.DTO.domoticz;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import fr.seblaporte.assistantdomoticz.util.DomoticzDeviceStatusDeserializer;
import lombok.Data;

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

    @JsonProperty("Status")
    @JsonDeserialize(using = DomoticzDeviceStatusDeserializer.class)
    private DomoticzDeviceStatusDTO status;
}

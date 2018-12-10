package fr.seblaporte.assistantdomoticz.DTO.domoticz;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DomoticzDeviceStatusDTO {

    /* Device status */
    DomoticzDeviceStatusEnum status;

    /* Level for devices with dimmer capability */
    Integer level = 100;

    public DomoticzDeviceStatusDTO(DomoticzDeviceStatusEnum status) {
        this.status = status;
    }

}

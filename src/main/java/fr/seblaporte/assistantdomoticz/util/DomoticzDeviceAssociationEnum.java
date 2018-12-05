package fr.seblaporte.assistantdomoticz.util;

import fr.seblaporte.assistantdomoticz.DTO.domoticz.DomoticzDeviceTypeEnum;
import fr.seblaporte.assistantdomoticz.DTO.google.DeviceTypeEnum;
import lombok.Getter;

@Getter
public enum DomoticzDeviceAssociationEnum {

    LIGHT(DomoticzDeviceTypeEnum.LIGHT, DeviceTypeEnum.LIGHT),
    SWITCH(DomoticzDeviceTypeEnum.SWITCH, DeviceTypeEnum.SWITCH),
    AMFLIFIER(DomoticzDeviceTypeEnum.AMPLIFIER, DeviceTypeEnum.SWITCH),
    SPEAKER(DomoticzDeviceTypeEnum.SPEAKER, DeviceTypeEnum.SWITCH),
    TV(DomoticzDeviceTypeEnum.TV, DeviceTypeEnum.SWITCH);

    private DomoticzDeviceTypeEnum deviceTypeDomoticz;
    private DeviceTypeEnum deviceType;

    DomoticzDeviceAssociationEnum(DomoticzDeviceTypeEnum deviceTypeDomoticz, DeviceTypeEnum deviceType) {
        this.deviceTypeDomoticz = deviceTypeDomoticz;
        this.deviceType = deviceType;
    }

}

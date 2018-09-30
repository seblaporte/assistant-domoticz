package fr.seblaporte.assistantdomoticz.util;

import fr.seblaporte.assistantdomoticz.DTO.domoticz.DeviceTypeDomoticzEnum;
import fr.seblaporte.assistantdomoticz.DTO.google.DeviceTypeEnum;
import lombok.Getter;

@Getter
public enum DomoticzDeviceAssociationEnum {

    LIGHT(DeviceTypeDomoticzEnum.LIGHT, DeviceTypeEnum.LIGHT),
    SWITCH(DeviceTypeDomoticzEnum.SWITCH, DeviceTypeEnum.SWITCH),
    AMFLIFIER(DeviceTypeDomoticzEnum.AMPLIFIER, DeviceTypeEnum.SWITCH),
    SPEAKER(DeviceTypeDomoticzEnum.SPEAKER, DeviceTypeEnum.SWITCH),
    TV(DeviceTypeDomoticzEnum.TV, DeviceTypeEnum.SWITCH);

    private DeviceTypeDomoticzEnum deviceTypeDomoticz;
    private DeviceTypeEnum deviceType;

    DomoticzDeviceAssociationEnum(DeviceTypeDomoticzEnum deviceTypeDomoticz, DeviceTypeEnum deviceType) {
        this.deviceTypeDomoticz = deviceTypeDomoticz;
        this.deviceType = deviceType;
    }

}

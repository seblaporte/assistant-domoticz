package fr.seblaporte.assistantdomoticz.util;

import fr.seblaporte.assistantdomoticz.DTO.domoticz.DomoticzSwitchTypeEnum;
import fr.seblaporte.assistantdomoticz.DTO.google.DeviceTraitEnum;
import lombok.Getter;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Getter
public enum DomoticzTraitAssociationEnum {

    ON_OFF(DomoticzSwitchTypeEnum.ON_OFF, Collections.singletonList(DeviceTraitEnum.ON_OFF)),
    DIMMER(DomoticzSwitchTypeEnum.DIMMER, Arrays.asList(DeviceTraitEnum.ON_OFF, DeviceTraitEnum.BRIGHTNESS));

    private DomoticzSwitchTypeEnum domoticzSwitchType;
    private List<DeviceTraitEnum> deviceTraits;

    DomoticzTraitAssociationEnum(DomoticzSwitchTypeEnum domoticzSwitchType, List<DeviceTraitEnum> deviceTrait) {
        this.domoticzSwitchType = domoticzSwitchType;
        this.deviceTraits = deviceTrait;
    }
}

package fr.seblaporte.assistantdomoticz.util;

import fr.seblaporte.assistantdomoticz.DTO.domoticz.*;
import fr.seblaporte.assistantdomoticz.DTO.google.*;
import fr.seblaporte.assistantdomoticz.DTO.google.response.DeviceStatusDTO;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DomoticzApiConverter {

    public static List<DeviceDTO> convertDevices(List<DomoticzDeviceDTO> domoticzDevices) {

        List<DeviceDTO> devices = new ArrayList<>();

        for (DomoticzDeviceDTO device : domoticzDevices) {
            devices.add(convertDevice(device));
        }

        return devices;
    }

    public static DeviceDTO convertDevice(DomoticzDeviceDTO domoticzDevice) {

        final Capability capability = getCapability(domoticzDevice);

        /* Mandatory information */
        DeviceDTO device = new DeviceDTO();
        device.setId(domoticzDevice.getDeviceId());
        device.setName(new DeviceNameDTO(domoticzDevice.getName()));
        device.setType(capability.getDeviceType());
        device.setTraits(capability.getDeviceTraits());

        /* Additional information */
        if (!StringUtils.isEmpty(domoticzDevice.getDescription())) {
            device.getName().setNicknames(Arrays.asList(domoticzDevice.getDescription().split(",")));
        }
        if (!StringUtils.isEmpty(domoticzDevice.getRoom())) {
            device.setRoomHint(domoticzDevice.getRoom());
        }
        device.getDeviceInfo().setModel(domoticzDevice.getHardwareType().toString());

        return device;
    }

    private static Capability getCapability(DomoticzDeviceDTO domoticzDevice) {

        Capability capability = new Capability();

        switch (domoticzDevice.getHardwareType()) {

            case DUMMY:
                capability.setDeviceType(DeviceTypeEnum.SWITCH);
                capability.setDeviceTraits(Collections.singletonList(DeviceTraitEnum.ON_OFF));
                break;
            case RFXCOM:
                capability.setDeviceType(getDeviceTypeFromDomoticz(domoticzDevice.getDeviceCategory()));
                capability.setDeviceTraits(getDeviceTraitsFromDomoticz(domoticzDevice.getSwitchType()));
                break;
            case MI_LIGHT:
                capability.setDeviceType(DeviceTypeEnum.LIGHT);
                capability.setDeviceTraits(Arrays.asList(
                        DeviceTraitEnum.ON_OFF,
                        DeviceTraitEnum.BRIGHTNESS,
                        DeviceTraitEnum.COLOR_TEMPERATURE,
                        DeviceTraitEnum.COLOR_SPECTRUM));
                break;
            default:
                capability.setDeviceType(DeviceTypeEnum.SWITCH);
                capability.setDeviceTraits(Collections.singletonList(DeviceTraitEnum.ON_OFF));
                break;
        }

        return capability;
    }

    private static DeviceTypeEnum getDeviceTypeFromDomoticz(DomoticzDeviceTypeEnum deviceTypeDomoticz) {

        for (DomoticzDeviceAssociationEnum domoticzDevice : DomoticzDeviceAssociationEnum.values()) {
            if (domoticzDevice.getDeviceTypeDomoticz() == deviceTypeDomoticz) {
                return domoticzDevice.getDeviceType();
            }
        }

        return DeviceTypeEnum.SWITCH;
    }

    private static List<DeviceTraitEnum> getDeviceTraitsFromDomoticz(DomoticzSwitchTypeEnum switchType) {

        for (DomoticzTraitAssociationEnum domoticzSwitchType : DomoticzTraitAssociationEnum.values()) {
            if (domoticzSwitchType.getDomoticzSwitchType() == switchType) {
                return domoticzSwitchType.getDeviceTraits();
            }
        }

        return Collections.singletonList(DeviceTraitEnum.ON_OFF);
    }


    public static DeviceStatusDTO convertDeviceStatus(DomoticzDeviceStatusDTO status) {

        final Boolean deviceStatus = status.getStatus().equals(DomoticzDeviceStatusEnum.ON);
        final Integer brightnessLevel = status.getLevel();

        return new DeviceStatusDTO(deviceStatus, true, brightnessLevel);
    }
}

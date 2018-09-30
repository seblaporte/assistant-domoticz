package fr.seblaporte.assistantdomoticz.util;

import fr.seblaporte.assistantdomoticz.DTO.domoticz.DeviceDomoticzDTO;
import fr.seblaporte.assistantdomoticz.DTO.domoticz.DeviceTypeDomoticzEnum;
import fr.seblaporte.assistantdomoticz.DTO.domoticz.SwitchTypeEnum;
import fr.seblaporte.assistantdomoticz.DTO.google.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DomoticzApiConverter {

    public static List<DeviceDTO> convertDevices(List<DeviceDomoticzDTO> domoticzDevices) {

        List<DeviceDTO> devices = new ArrayList<>();

        for (DeviceDomoticzDTO device : domoticzDevices) {
            devices.add(convertDevice(device));
        }

        return devices;
    }

    public static DeviceDTO convertDevice(DeviceDomoticzDTO domoticzDevice) {

        final Capability capability = getCapability(domoticzDevice);

        DeviceDTO device = new DeviceDTO();
        device.setId(domoticzDevice.getDeviceId());
        device.setName(new DeviceNameDTO(domoticzDevice.getName()));
        device.setType(capability.getDeviceType());
        device.setTraits(capability.getDeviceTraits());

        return device;
    }

    private static Capability getCapability(DeviceDomoticzDTO domoticzDevice) {

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
            default:
                capability.setDeviceType(DeviceTypeEnum.SWITCH);
                capability.setDeviceTraits(Collections.singletonList(DeviceTraitEnum.ON_OFF));
                break;
        }

        return capability;
    }

    private static DeviceTypeEnum getDeviceTypeFromDomoticz(DeviceTypeDomoticzEnum deviceTypeDomoticz) {

        for (DomoticzDeviceAssociationEnum domoticzDevice : DomoticzDeviceAssociationEnum.values()) {
            if (domoticzDevice.getDeviceTypeDomoticz() == deviceTypeDomoticz) {
                return domoticzDevice.getDeviceType();
            }
        }

        return DeviceTypeEnum.SWITCH;
    }

    private static List<DeviceTraitEnum> getDeviceTraitsFromDomoticz(SwitchTypeEnum switchType) {

        for (DomoticzTraitAssociationEnum domoticzSwitchType : DomoticzTraitAssociationEnum.values()) {
            if (domoticzSwitchType.getDomoticzSwitchType() == switchType) {
                return domoticzSwitchType.getDeviceTraits();
            }
        }

        return Collections.singletonList(DeviceTraitEnum.ON_OFF);
    }


}

package fr.seblaporte.assistantdomoticz.util;

import fr.seblaporte.assistantdomoticz.DTO.domoticz.*;
import fr.seblaporte.assistantdomoticz.DTO.google.*;
import org.springframework.util.StringUtils;

import java.util.*;

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

        switch (domoticzDevice.getSwitchType()) {

            case DIMMER:

                switch (domoticzDevice.getSubType()) {

                    case RGBW:
                    case RGB:
                        capability.setDeviceType(DeviceTypeEnum.LIGHT);
                        capability.setDeviceTraits(Arrays.asList(
                                DeviceTraitEnum.ON_OFF,
                                DeviceTraitEnum.BRIGHTNESS,
                                DeviceTraitEnum.COLOR_TEMPERATURE,
                                DeviceTraitEnum.COLOR_SPECTRUM));
                        break;
                    case AC:
                    default:
                        capability.setDeviceType(DeviceTypeEnum.LIGHT);
                        capability.setDeviceTraits(Arrays.asList(
                                DeviceTraitEnum.ON_OFF,
                                DeviceTraitEnum.BRIGHTNESS
                        ));
                        break;
                }
                break;

            case ON_OFF:
            default:
                if (domoticzDevice.getDeviceCategory().equals(DomoticzDeviceTypeEnum.LIGHT)) {
                    capability.setDeviceType(DeviceTypeEnum.LIGHT);
                } else {
                    capability.setDeviceType(DeviceTypeEnum.SWITCH);
                }
                capability.setDeviceTraits(Collections.singletonList(DeviceTraitEnum.ON_OFF));
                break;

        }

        return capability;
    }

    public static Map<String, Object> convertDeviceStatus(DomoticzDeviceDTO domoticzDevice) {

        Map<String, Object> deviceStatusInfos = new HashMap<>();
        deviceStatusInfos.put("online", true);

        final DomoticzDeviceStatusDTO status = domoticzDevice.getStatus();

        switch (domoticzDevice.getSwitchType()) {
            case ON_OFF:
                deviceStatusInfos.put("on", status.getStatus().equals(DomoticzDeviceStatusEnum.ON));
                break;
            case DIMMER:
                deviceStatusInfos.put("on", status.getStatus().equals(DomoticzDeviceStatusEnum.ON));
                deviceStatusInfos.put("brightness", status.getLevel());
        }

        return deviceStatusInfos;
    }
}

package fr.seblaporte.assistantdomoticz.util;

import fr.seblaporte.assistantdomoticz.DTO.domoticz.DomoticzDeviceDTO;
import fr.seblaporte.assistantdomoticz.DTO.domoticz.DomoticzDeviceTypeEnum;
import fr.seblaporte.assistantdomoticz.DTO.domoticz.DomoticzHardwareEnum;
import fr.seblaporte.assistantdomoticz.DTO.domoticz.DomoticzSwitchTypeEnum;
import fr.seblaporte.assistantdomoticz.DTO.google.DeviceDTO;
import fr.seblaporte.assistantdomoticz.DTO.google.DeviceTraitEnum;
import fr.seblaporte.assistantdomoticz.DTO.google.DeviceTypeEnum;
import org.assertj.core.api.Assertions;
import org.junit.Test;

public class DomoticzApiConverterTest {

    @Test
    public void should_create_light_with_dimmer() {

        DomoticzDeviceDTO domoticzDevice = new DomoticzDeviceDTO();
        domoticzDevice.setDeviceId("1");
        domoticzDevice.setName("Lampe test");
        domoticzDevice.setHardwareType(DomoticzHardwareEnum.RFXCOM);
        domoticzDevice.setDeviceCategory(DomoticzDeviceTypeEnum.LIGHT);
        domoticzDevice.setSwitchType(DomoticzSwitchTypeEnum.DIMMER);

        final DeviceDTO device = DomoticzApiConverter.convertDevice(domoticzDevice);

        Assertions.assertThat("1").isEqualTo(device.getId());
        Assertions.assertThat("Lampe test").isEqualTo(device.getName().getName());
        Assertions.assertThat(DeviceTypeEnum.LIGHT).isEqualTo(device.getType());
        Assertions.assertThat(device.getTraits()).contains(DeviceTraitEnum.ON_OFF);
        Assertions.assertThat(device.getTraits()).contains(DeviceTraitEnum.BRIGHTNESS);
    }

    @Test
    public void should_create_light_without_dimmer() {

        DomoticzDeviceDTO domoticzDevice = new DomoticzDeviceDTO();
        domoticzDevice.setDeviceId("1");
        domoticzDevice.setName("Lampe test");
        domoticzDevice.setHardwareType(DomoticzHardwareEnum.RFXCOM);
        domoticzDevice.setDeviceCategory(DomoticzDeviceTypeEnum.LIGHT);
        domoticzDevice.setSwitchType(DomoticzSwitchTypeEnum.ON_OFF);

        final DeviceDTO device = DomoticzApiConverter.convertDevice(domoticzDevice);

        Assertions.assertThat("1").isEqualTo(device.getId());
        Assertions.assertThat("Lampe test").isEqualTo(device.getName().getName());
        Assertions.assertThat(DeviceTypeEnum.LIGHT).isEqualTo(device.getType());
        Assertions.assertThat(device.getTraits()).contains(DeviceTraitEnum.ON_OFF);
    }
}

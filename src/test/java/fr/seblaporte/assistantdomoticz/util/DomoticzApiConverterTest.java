package fr.seblaporte.assistantdomoticz.util;

import fr.seblaporte.assistantdomoticz.DTO.domoticz.DeviceDomoticzDTO;
import fr.seblaporte.assistantdomoticz.DTO.domoticz.DeviceTypeDomoticzEnum;
import fr.seblaporte.assistantdomoticz.DTO.domoticz.HardwareEnum;
import fr.seblaporte.assistantdomoticz.DTO.domoticz.SwitchTypeEnum;
import fr.seblaporte.assistantdomoticz.DTO.google.DeviceDTO;
import fr.seblaporte.assistantdomoticz.DTO.google.DeviceTraitEnum;
import fr.seblaporte.assistantdomoticz.DTO.google.DeviceTypeEnum;
import org.assertj.core.api.Assertions;
import org.junit.Test;

public class DomoticzApiConverterTest {

    @Test
    public void should_create_light_with_dimmer() {

        DeviceDomoticzDTO domoticzDevice = new DeviceDomoticzDTO();
        domoticzDevice.setDeviceId("1");
        domoticzDevice.setName("Lampe test");
        domoticzDevice.setHardwareType(HardwareEnum.RFXCOM);
        domoticzDevice.setDeviceCategory(DeviceTypeDomoticzEnum.LIGHT);
        domoticzDevice.setSwitchType(SwitchTypeEnum.DIMMER);

        final DeviceDTO device = DomoticzApiConverter.convertDevice(domoticzDevice);

        Assertions.assertThat("1").isEqualTo(device.getId());
        Assertions.assertThat("Lampe test").isEqualTo(device.getName().getName());
        Assertions.assertThat(DeviceTypeEnum.LIGHT).isEqualTo(device.getType());
        Assertions.assertThat(device.getTraits()).contains(DeviceTraitEnum.ON_OFF);
        Assertions.assertThat(device.getTraits()).contains(DeviceTraitEnum.BRIGHTNESS);
    }

    @Test
    public void should_create_light_without_dimmer() {

        DeviceDomoticzDTO domoticzDevice = new DeviceDomoticzDTO();
        domoticzDevice.setDeviceId("1");
        domoticzDevice.setName("Lampe test");
        domoticzDevice.setHardwareType(HardwareEnum.RFXCOM);
        domoticzDevice.setDeviceCategory(DeviceTypeDomoticzEnum.LIGHT);
        domoticzDevice.setSwitchType(SwitchTypeEnum.ON_OFF);

        final DeviceDTO device = DomoticzApiConverter.convertDevice(domoticzDevice);

        Assertions.assertThat("1").isEqualTo(device.getId());
        Assertions.assertThat("Lampe test").isEqualTo(device.getName().getName());
        Assertions.assertThat(DeviceTypeEnum.LIGHT).isEqualTo(device.getType());
        Assertions.assertThat(device.getTraits()).contains(DeviceTraitEnum.ON_OFF);
    }
}

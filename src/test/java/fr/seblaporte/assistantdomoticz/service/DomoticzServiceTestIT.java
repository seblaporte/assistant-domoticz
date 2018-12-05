package fr.seblaporte.assistantdomoticz.service;

import fr.seblaporte.assistantdomoticz.DTO.domoticz.DomoticzDeviceDTO;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DomoticzServiceTestIT {

    @Autowired
    private DomoticzService domoticzService;

    @Test
    public void should_return_devices_from_domoticz() throws Exception {

        final List<DomoticzDeviceDTO> devices = domoticzService.getDevices();
        Assertions.assertThat(devices).isNotEmpty();
    }
}

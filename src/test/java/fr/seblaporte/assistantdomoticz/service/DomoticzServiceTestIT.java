package fr.seblaporte.assistantdomoticz.service;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class DomoticzServiceTestIT {

    @Autowired
    private DomoticzService domoticzService;

    @Test
    public void should_return_devices_from_domoticz() throws Exception {

        Assertions.assertThat(domoticzService.getDevices()).isNotEmpty();
    }
}

package fr.seblaporte.assistantdomoticz.service;

import org.assertj.core.api.Assertions;
import org.junit.Test;

public class DomoticzServiceTestIT {

    @Test
    public void should_return_devices_from_domoticz() throws Exception {

        DomoticzService domoticzService = new DomoticzService();

        Assertions.assertThat(domoticzService.getDevices()).isNotEmpty();
    }
}

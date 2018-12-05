package fr.seblaporte.assistantdomoticz.DTO.domoticz;

import lombok.Data;

import java.util.List;

@Data
public class DomoticzDevicesResponseDTO {

    /** Devices informations */
    private List<DomoticzDeviceDTO> result;
}

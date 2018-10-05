package fr.seblaporte.assistantdomoticz.DTO.domoticz;

import lombok.Data;

import java.util.List;

@Data
public class DomoticzResponseDTO {

    /** Devices informations */
    private List<DomoticzDeviceDTO> result;
}

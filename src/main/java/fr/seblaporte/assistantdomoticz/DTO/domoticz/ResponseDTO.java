package fr.seblaporte.assistantdomoticz.DTO.domoticz;

import lombok.Data;

import java.util.List;

@Data
public class ResponseDTO {

    /** Devices informations */
    private List<DeviceDomoticzDTO> result;
}

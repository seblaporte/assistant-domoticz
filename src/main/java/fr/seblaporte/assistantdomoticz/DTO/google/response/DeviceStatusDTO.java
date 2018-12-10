package fr.seblaporte.assistantdomoticz.DTO.google.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DeviceStatusDTO {

    Boolean on;
    Boolean online;
    Integer brightness;
}

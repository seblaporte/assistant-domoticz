package fr.seblaporte.assistantdomoticz.DTO.google.request;

import fr.seblaporte.assistantdomoticz.DTO.google.DeviceCommandEnum;
import fr.seblaporte.assistantdomoticz.DTO.google.DeviceTraitEnum;
import fr.seblaporte.assistantdomoticz.DTO.google.StateEnum;
import lombok.Data;

import java.util.Map;

@Data
public class ExecutionCommandDTO {

    /** The command to execute, with (usually) accompanying parameters. */
    private DeviceCommandEnum command;

    private Map<StateEnum, Object> params;
}

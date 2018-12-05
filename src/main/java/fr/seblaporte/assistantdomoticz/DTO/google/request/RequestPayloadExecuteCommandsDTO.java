package fr.seblaporte.assistantdomoticz.DTO.google.request;

import fr.seblaporte.assistantdomoticz.DTO.google.DeviceDTO;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
public class RequestPayloadExecuteCommandsDTO {

    @NotEmpty
    private List<DeviceDTO> devices;

    @NotEmpty
    private List<ExecutionCommandDTO> execution;
}

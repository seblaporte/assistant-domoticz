package fr.seblaporte.assistantdomoticz.DTO;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
public class PayloadExecuteCommands {

    @NotEmpty
    private List<ExecuteDevice> devices;

    @NotEmpty
    private List<ExecutionCommand> execution;
}

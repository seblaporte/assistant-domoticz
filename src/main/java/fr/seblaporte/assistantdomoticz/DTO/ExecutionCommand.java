package fr.seblaporte.assistantdomoticz.DTO;

import lombok.Data;

import java.util.Map;

@Data
public class ExecutionCommand {

    private String command;

    private Map<String, Object> params;
}

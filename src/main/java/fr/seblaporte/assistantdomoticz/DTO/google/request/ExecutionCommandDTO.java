package fr.seblaporte.assistantdomoticz.DTO.google.request;

import lombok.Data;

import java.util.Map;

@Data
public class ExecutionCommandDTO {

    /** The command to execute, with (usually) accompanying parameters. */
    private String command;

    private Map<String, Object> params;
}

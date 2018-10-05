package fr.seblaporte.assistantdomoticz.DTO.google.response;

import lombok.Data;

import java.util.List;

@Data
public class ResponsePayloadExecuteDTO extends ResponsePayloadDTO {

    private List<Command> commands;
}

package fr.seblaporte.assistantdomoticz.DTO;

import lombok.Data;

import java.util.List;

@Data
public class Payload {

    private List<PayloadExecuteCommands> commands;

    private List<PayloadQueryDevices> devices;
}

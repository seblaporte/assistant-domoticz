package fr.seblaporte.assistantdomoticz.DTO.google.response;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class Commands {

    @NotBlank
    private String id;

    @NotBlank
    private DeviceStatusEnum status;

    private String errorCode;

    private String debugString;

    public Commands() {
        this.errorCode = "";
        this.debugString = "";
    }
}

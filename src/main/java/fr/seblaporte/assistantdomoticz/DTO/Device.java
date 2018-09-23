package fr.seblaporte.assistantdomoticz.DTO;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class Device {

    /** Device id */
    @NotBlank
    private String id;
}

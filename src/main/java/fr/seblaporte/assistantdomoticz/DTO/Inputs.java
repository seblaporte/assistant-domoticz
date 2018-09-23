package fr.seblaporte.assistantdomoticz.DTO;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class Inputs {

    /** Request type */
    @NotBlank
    private String intent;

    @NotNull
    private Payload payload;
}

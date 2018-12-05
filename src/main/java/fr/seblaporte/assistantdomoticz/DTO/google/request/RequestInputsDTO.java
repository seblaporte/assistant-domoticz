package fr.seblaporte.assistantdomoticz.DTO.google.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class RequestInputsDTO {

    /** RequestDTO type */
    @NotBlank
    private String intent;

    @NotNull
    private RequestPayloadDTO payload;
}

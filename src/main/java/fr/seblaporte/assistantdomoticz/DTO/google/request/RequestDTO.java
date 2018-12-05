package fr.seblaporte.assistantdomoticz.DTO.google.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Request object.
 */
@Data
public class RequestDTO {

    /** Id of RequestDTO for ease of tracing */
    @NotBlank
    private String requestId;

    @NotNull
    private List<RequestInputsDTO> inputs;
}

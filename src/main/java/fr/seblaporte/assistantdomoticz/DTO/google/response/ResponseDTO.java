package fr.seblaporte.assistantdomoticz.DTO.google.response;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * Response object.
 */
@Data
public class ResponseDTO {

    /** Id of RequestDTO for ease of tracing */
    @NotBlank
    private String requestId;

    /** Response content */
    private ResponsePayloadDTO payload;
}

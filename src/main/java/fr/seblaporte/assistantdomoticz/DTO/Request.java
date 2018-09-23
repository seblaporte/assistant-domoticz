package fr.seblaporte.assistantdomoticz.DTO;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class Request {

    /** Id of Request for ease of tracing */
    @NotBlank
    private String requestId;

    @NotNull
    private List<Inputs> inputs;
}

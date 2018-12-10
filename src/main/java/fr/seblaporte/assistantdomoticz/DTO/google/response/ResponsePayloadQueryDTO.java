package fr.seblaporte.assistantdomoticz.DTO.google.response;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
public class ResponsePayloadQueryDTO extends ResponsePayloadDTO {

    Map<String, Map<String, Object>> devices;
}

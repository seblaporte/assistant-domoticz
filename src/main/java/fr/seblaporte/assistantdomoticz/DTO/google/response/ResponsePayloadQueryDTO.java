package fr.seblaporte.assistantdomoticz.DTO.google.response;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
public class ResponsePayloadQueryDTO extends ResponsePayloadDTO {

    List<Map<String, DeviceStatusDTO>> devices;

    public ResponsePayloadQueryDTO() {
        this.devices = new ArrayList<>();
    }
}

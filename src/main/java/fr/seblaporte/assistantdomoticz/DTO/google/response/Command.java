package fr.seblaporte.assistantdomoticz.DTO.google.response;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Data
public class Command {

    @NotBlank
    private List<String> ids;

    @NotBlank
    private DeviceStatusEnum status;

    private String errorCode;

    private String debugString;

    public Command() {
        this.errorCode = "";
        this.debugString = "";
        this.ids = new ArrayList<>();
    }

    public Command(String deviceId, DeviceStatusEnum deviceStatus) {

        this.ids = new ArrayList<>();
        this.ids.add(deviceId);
        this.status = deviceStatus;
        this.errorCode = "";
        this.debugString = "";
    }
}

package fr.seblaporte.assistantdomoticz.DTO.google;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class DeviceDTO {

    /** Device id */
    @NotBlank
    private String id;

    /** The hardware type of device. */
    private DeviceTypeEnum type;

    /** List of traits this device supports. */
    private List<DeviceTraitEnum> traits;

    /** Names of this device. */
    private DeviceNameDTO name;

    /** Indicates whether this device will have its states updated by the Real Time Feed. */
    private Boolean willReportState;

    /** Provides the current room of the device in the user's home to simplify setup. */
    private String roomHint;

    /** Contains fields describing the device for use in one-off logic if needed */
    private DeviceInfoDTO deviceInfo;

    public DeviceDTO() {
        this.setWillReportState(true);
        this.setDeviceInfo(new DeviceInfoDTO());
        this.setRoomHint("");
    }

}

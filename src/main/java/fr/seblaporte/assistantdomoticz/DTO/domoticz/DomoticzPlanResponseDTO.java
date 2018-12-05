package fr.seblaporte.assistantdomoticz.DTO.domoticz;

import lombok.Data;

import java.util.List;

@Data
public class DomoticzPlanResponseDTO {

    private List<DomoticzPlanDTO> result;

    private String status;

    private String title;
}

package fr.seblaporte.assistantdomoticz.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import fr.seblaporte.assistantdomoticz.DTO.domoticz.DomoticzDeviceStatusDTO;
import fr.seblaporte.assistantdomoticz.DTO.domoticz.DomoticzDeviceStatusEnum;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DomoticzDeviceStatusDeserializer extends JsonDeserializer<DomoticzDeviceStatusDTO> {


    @Override
    public DomoticzDeviceStatusDTO deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        ObjectCodec codec = jsonParser.getCodec();
        JsonNode node = codec.readTree(jsonParser);

        final String domoticzStatus = node.asText();

        if (domoticzStatus.equals("On") || domoticzStatus.equals("Off")) {
            DomoticzDeviceStatusEnum status = domoticzStatus.equals("On") ? DomoticzDeviceStatusEnum.ON : DomoticzDeviceStatusEnum.OFF;
            return new DomoticzDeviceStatusDTO(status);
        } else {

            Pattern pattern = Pattern.compile("(.*?)(\\d+)(.*)");
            Matcher matcher = pattern.matcher(domoticzStatus);

            if (matcher.matches()) {
                String levelValue = matcher.group(2);
                Integer level = Integer.parseInt(levelValue);
                return new DomoticzDeviceStatusDTO(DomoticzDeviceStatusEnum.ON, level);
            }
        }

        return new DomoticzDeviceStatusDTO(DomoticzDeviceStatusEnum.OFF);
    }
}

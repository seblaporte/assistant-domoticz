package fr.seblaporte.assistantdomoticz.util;

import fr.seblaporte.assistantdomoticz.DTO.domoticz.DomoticzDeviceStatusDTO;
import fr.seblaporte.assistantdomoticz.DTO.domoticz.DomoticzDeviceStatusEnum;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.ObjectCodec;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DomoticzDeviceStatusDeserializer extends JsonDeserializer<DomoticzDeviceStatusDTO> {

    @Override
    public DomoticzDeviceStatusDTO deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {

        ObjectCodec oc = jp.getCodec();
        JsonNode node = oc.readTree(jp);

        final String domoticzStatus = node.asText();

        if (domoticzStatus.equals("On") || domoticzStatus.equals("Off")) {
            DomoticzDeviceStatusEnum status = domoticzStatus.equals("On") ? DomoticzDeviceStatusEnum.ON : DomoticzDeviceStatusEnum.OFF;
            return new DomoticzDeviceStatusDTO(status);
        } else {

            Pattern pattern = Pattern.compile("(.*?)(\\d+)%");
            Matcher matcher = pattern.matcher(domoticzStatus);

            if (matcher.matches()) {
                String levelValue = matcher.group(1);
                Integer level = Integer.parseInt(levelValue);
                return new DomoticzDeviceStatusDTO(DomoticzDeviceStatusEnum.ON, level);
            }
        }

        return new DomoticzDeviceStatusDTO(DomoticzDeviceStatusEnum.OFF);
    }

}

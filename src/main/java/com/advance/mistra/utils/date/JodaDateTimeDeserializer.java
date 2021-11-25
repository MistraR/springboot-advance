package com.advance.mistra.utils.date;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * joda时间反序列化
 */
public class JodaDateTimeDeserializer extends JsonDeserializer<DateTime> {

    @Override
    public DateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZZ");
        String s = jsonParser.getValueAsString();
        if (s != null) {
            try {
                return formatter.parseDateTime(s);
            } catch (Exception e) {
                formatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ssZ");
                return formatter.parseDateTime(s);
            }
        }
        return null;
    }


}

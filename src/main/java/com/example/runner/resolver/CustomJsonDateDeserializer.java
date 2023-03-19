package com.example.runner.resolver;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.Map;

public class CustomJsonDateDeserializer extends JsonDeserializer<Map> {
    @Override
    public Map deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        String text = jsonParser.getText();
        if (StringUtils.hasText(text)){
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(text, Map.class);
        }
        return null;
    }
}

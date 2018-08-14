package com.airline.rest.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class CustomListDateDeserealize extends JsonDeserializer<List<Date>> {

    private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public List<Date> deserialize(JsonParser jsonParser,
                                  DeserializationContext deserializationContext)
            throws IOException, JsonProcessingException {
        List<Date> dates = new ArrayList<>();
        if (jsonParser.getCurrentToken() == JsonToken.START_ARRAY) {
            while(jsonParser.nextToken() != JsonToken.END_ARRAY) {
                try {
                    dates.add(formatter.parse(jsonParser.getValueAsString()));
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        return dates;
    }
}

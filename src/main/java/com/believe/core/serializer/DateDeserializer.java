package com.believe.core.serializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.joda.time.format.DateTimeFormat;

import java.io.IOException;
import java.util.Date;

public class DateDeserializer extends JsonDeserializer<Date> {

  @Override
  public Date deserialize(JsonParser parser, DeserializationContext context)
    throws IOException {
    return toDate(parser.getValueAsString(), "yyyyMMddHHmmss");
  }

  public static Date toDate(String dateStr, String pattern) {
    return DateTimeFormat.forPattern(pattern).parseDateTime(dateStr).toDate();
  }
}
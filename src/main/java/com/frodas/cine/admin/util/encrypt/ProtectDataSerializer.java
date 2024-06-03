package com.frodas.cine.admin.util.encrypt;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class ProtectDataSerializer extends JsonSerializer {

    @Override
    public void serialize(Object value, JsonGenerator gen, SerializerProvider serializerProvider) throws IOException {
        String valueString = value.toString();
        String newValue = valueString.substring(0, valueString.length() - 4);
        String pii = valueString.replace(newValue, "x");
        //String pii = value.toString().replaceAll("\\w(?=\\w{4})", "x"); // escapar los ultimos 4 caracteres
        gen.writeString(pii);
    }

}

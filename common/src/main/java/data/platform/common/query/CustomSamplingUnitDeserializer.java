package data.platform.common.query;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

public class CustomSamplingUnitDeserializer extends StdDeserializer<QuerySamplingUnit> {

    public CustomSamplingUnitDeserializer() {
        this(null);
    }

    public CustomSamplingUnitDeserializer(Class<?> c) {
        super(c);
    }

    @Override
    public QuerySamplingUnit deserialize(JsonParser jsonParser, DeserializationContext ctxt)
            throws IOException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        String unit = node.asText();
        for (QuerySamplingUnit querySamplingUnit : QuerySamplingUnit.values()) {
            if (querySamplingUnit.getDescription().equalsIgnoreCase(unit)) {
                return querySamplingUnit;
            }
        }
        return null;
    }
}
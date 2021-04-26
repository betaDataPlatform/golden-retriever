package data.platform.common.query;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

public class CustomAggregatorUnitDeserializer extends StdDeserializer<QueryAggregatorUnit> {

    public CustomAggregatorUnitDeserializer() {
        this(null);
    }

    public CustomAggregatorUnitDeserializer(Class<?> c) {
        super(c);
    }

    @Override
    public QueryAggregatorUnit deserialize(JsonParser jsonParser, DeserializationContext ctx)
            throws IOException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        String unit = node.asText();
        for (QueryAggregatorUnit queryAggregatorUnit : QueryAggregatorUnit.values()) {
            if (queryAggregatorUnit.getDescription().equalsIgnoreCase(unit)) {
                return queryAggregatorUnit;
            }
        }
        return null;
    }
}
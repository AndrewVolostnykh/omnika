package io.omnika.common.utils.json;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.json.JsonMapper;
import java.io.IOException;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JacksonUtils {

    private static final JsonMapper mapper = new JsonMapper();

    static {
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }

    public static boolean isNodeBlank(JsonNode jsonNode) {
        return jsonNode == null || jsonNode.isNull();
    }

    public static <T> Optional<T> convertOptional(JsonNode node, Class<T> mapTo) {
        if (isNodeBlank(node)) {
            return Optional.empty();
        }

        return Optional.of(convert(node, mapTo));
    }

    public static <T> T convert(JsonNode node, Class<T> mapTo) {
        return mapper.convertValue(node, mapTo);
    }

    public static boolean isNodeNotBlank(JsonNode jsonNode) {
        return !isNodeBlank(jsonNode);
    }

    public static Optional<JsonNode> getOptional(JsonNode node, String key) {
        if (isNodeBlank(node)) {
            return Optional.empty();
        }
        return Optional.ofNullable(node.get(key)).filter(JacksonUtils::isNodeNotBlank);
    }

    public static <T> Optional<T> readValueOptional(String json, Class<T> convertTo) {
        T result = null;
        try {
            result = mapper.readValue(json, convertTo);
        } catch (IOException e) {
            log.warn("Conversion [{}] to [{}] failed. Message: {}", json, convertTo, e.getMessage());
        }

        return Optional.ofNullable(result);
    }

    public static JsonNode valueToTree(Object value) {
        return mapper.valueToTree(value);
    }

}

package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * JSON format of this record:
 * <p>
 * <code>
 * {
 *   "method-name": "method name",
 *   "arguments": {}
 * }
 * </code>
 * </p>
 *
 * @param methodName the name of the method
 *
 * @param arguments arguments of the method
 */
public record MessageJson(
    @JsonProperty("method-name") String methodName,
    @JsonProperty("arguments") JsonNode arguments){
}
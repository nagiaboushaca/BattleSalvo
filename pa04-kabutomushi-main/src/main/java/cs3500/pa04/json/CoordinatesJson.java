package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;


/**
 * JSON format of this record:
 * <p>
 * <code>
 * {
 *   "coordinates": {}
 * }
 * </code>
 * </p>
 *
 * @param coordinates the list of coordinates
 */
public record CoordinatesJson(
    @JsonProperty("coordinates") CoordJson[] coordinates) {
}

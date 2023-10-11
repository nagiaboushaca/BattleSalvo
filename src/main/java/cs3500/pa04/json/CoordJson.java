package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;


/**
 * JSON format of this record:
 * <p>
 * <code>
 * {
 *   "x": valueX,
 *   "y": valueY
 * }
 * </code>
 * </p>
 *
 * @param valueX the x coordinate of the coord
 *
 * @param valueY the y coordinate of the coord
 */
public record CoordJson(
    @JsonProperty("x") int valueX,
    @JsonProperty("y") int valueY) {
}

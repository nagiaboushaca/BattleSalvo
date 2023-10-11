package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa04.model.Direction;

/**
 * JSON format of this record:
 * <p>
 * <code>
 * {
 *   "coord": coord
 *   "length": length
 *   "direction": "direction"
 * }
 * </code>
 * </p>
 *
 * @param coord the start coordinate of the ship
 *
 * @param length the length of the ship
 *
 * @param direction the direction of the ship, horizontal or vertical
 */
public record ShipJson(
    @JsonProperty("coord") CoordJson coord,
    @JsonProperty("length") int length,
    @JsonProperty("direction") Direction direction) {
}

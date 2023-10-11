package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;


/**
 * JSON format of this record:
 * <p>
 * <code>
 * {
 *   "ships": {}
 * }
 * </code>
 * </p>
 *
 * @param ships the list of ships that is in this fleet
 */
public record FleetJson(
    @JsonProperty("fleet") ShipJson[] ships) {
}

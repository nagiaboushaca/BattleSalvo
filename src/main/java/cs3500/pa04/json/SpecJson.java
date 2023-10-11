package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * JSON for ship specifications from Server
 *
 * @param numCarrier number of carriers
 *
 * @param numBattleship number of battleships
 *
 * @param numDestroyer number of destroyers
 *
 * @param numSubmarine number of submarines
 */
public record SpecJson(
    @JsonProperty("CARRIER") int numCarrier,
    @JsonProperty("BATTLESHIP") int numBattleship,
    @JsonProperty("DESTROYER") int numDestroyer,
    @JsonProperty("SUBMARINE") int numSubmarine) {
}

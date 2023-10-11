package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * JSON info for setup sent from server
 *
 * @param width the width of the game
 *
 * @param height the height of the game
 *
 * @param spec the ship specifications for setup
 */
public record SetupJson(
    @JsonProperty("width") int width,
    @JsonProperty("height") int height,
    @JsonProperty("fleet-spec") SpecJson spec) {
}

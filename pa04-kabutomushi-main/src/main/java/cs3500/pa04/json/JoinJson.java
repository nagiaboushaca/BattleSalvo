package cs3500.pa04.json;


import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * the message format to send to the server for joining
 *
 * @param name the name of the player
 *
 * @param gameType single or multi game type
 */
public record JoinJson(
    @JsonProperty("name") String name,
    @JsonProperty("game-type") GameType gameType) {
}

package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa04.model.GameResult;

/**
 * json info for end game
 *
 * @param result the result of the game
 *
 * @param reason the reason for the result
 */
public record EndGameJson(
    @JsonProperty("result") String result,
    @JsonProperty("reason") String reason) {
}

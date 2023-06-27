package cs3500.pa04.controller.json;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The json for the end of the game
 *
 * @param result the result of the game
 * @param reason the reason for the game end
 */
public record EndGameJson(
    @JsonProperty("result") String result,
    @JsonProperty("reason") String reason
) {
}

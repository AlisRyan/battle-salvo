package cs3500.pa04.controller.json;

import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * The json for joining a game
 *
 * @param name the player's name
 * @param gameType the type of game being played
 */
public record JoinJson(
    @JsonProperty("name") String name,
    @JsonProperty("game-type") String gameType
) {
}

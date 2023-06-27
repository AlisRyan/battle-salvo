package cs3500.pa04.controller.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import java.util.List;

/**
 * The json for the fleet of ships on the board
 *
 * @param fleet the list of json nodes containing the ship fleet
 */
public record FleetJson(
    @JsonProperty("fleet") List<JsonNode> fleet
) {
}

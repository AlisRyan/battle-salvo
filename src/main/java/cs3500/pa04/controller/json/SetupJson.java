package cs3500.pa04.controller.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import cs3500.pa04.model.ShipType;
import java.util.HashMap;
import java.util.Map;

/**
 * The json for taking in a setup call
 *
 * @param width the board width to be set
 * @param height the board height to be set
 * @param spec the ship specs to be set
 */
public record SetupJson(
    @JsonProperty("width") int width,
    @JsonProperty("height") int height,
    @JsonProperty("fleet-spec") JsonNode spec
) {

  /**
   *
   * @return returns the specs of the spec jsonnode
   */
  public Map<ShipType, Integer> getSpecs() {
    Map<ShipType, Integer> specs = new HashMap<>();
    int carrier = spec.get(ShipType.CARRIER.toString()).asInt();
    int battleship = spec.get(ShipType.BATTLESHIP.toString()).asInt();
    int destroyer = spec.get(ShipType.DESTROYER.toString()).asInt();
    int submarine = spec.get(ShipType.SUBMARINE.toString()).asInt();
    specs.put(ShipType.CARRIER, carrier);
    specs.put(ShipType.BATTLESHIP, battleship);
    specs.put(ShipType.DESTROYER, destroyer);
    specs.put(ShipType.SUBMARINE, submarine);
    return specs;
  }
}

package cs3500.pa04.controller.json;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * Adapts a ship for json output
 *
 * @param coord the coordinates of this ship as a json node
 * @param length the length of this ship
 * @param direction the direction of this ship
 */
public record ShipAdapter(JsonNode coord, int length, String direction) {

  /**
   * Creates a ship adapter
   *
   * @param coord the coordinates of this ship as a json node
   * @param length the length of this ship
   * @param direction the direction of this ship
   */
  @JsonCreator
  public ShipAdapter(
      @JsonProperty("coord") JsonNode coord,
      @JsonProperty("length") int length,
      @JsonProperty("direction") String direction
  ) {
    this.coord = coord;
    this.length = length;
    this.direction = direction;
  }
}

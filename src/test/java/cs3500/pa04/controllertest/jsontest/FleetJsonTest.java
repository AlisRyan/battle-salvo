package cs3500.pa04.controllertest.jsontest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.pa04.controller.json.CoordAdapter;
import cs3500.pa04.controller.json.FleetJson;
import cs3500.pa04.controller.json.ShipAdapter;
import cs3500.pa04.model.Coord;
import cs3500.pa04.model.Ship;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

/**
 * Tests the fleet json record
 */
public class FleetJsonTest {

  @Test
  public void checkFleetJson() {
    try {
      ObjectMapper mapper = new ObjectMapper();
      Coord first = new Coord(2, 3);
      Coord second = new Coord(2, 4);
      Coord third = new Coord(2, 5);
      List<Coord> coords = new ArrayList<>(Arrays.asList(first, second, third));
      List<Coord> coords2 = new ArrayList<>(Arrays.asList(new Coord(1, 1),
          new Coord(2, 1), new Coord(3, 1), new Coord(4, 1)));
      Ship ship = new Ship(coords);
      Ship ship2 = new Ship(coords2);
      ShipAdapter shipNode = new ShipAdapter(mapper.convertValue(new CoordAdapter(ship.getStart()),
          JsonNode.class), ship.length(), ship.direction());
      ShipAdapter shipNode2 =
          new ShipAdapter(mapper.convertValue(new CoordAdapter(ship2.getStart()),
              JsonNode.class), ship2.length(), ship2.direction());
      List<JsonNode> shipAdapters = new ArrayList<>(Arrays.asList(
          mapper.convertValue(shipNode, JsonNode.class),
          mapper.convertValue(shipNode2, JsonNode.class)));
      FleetJson fleet = new FleetJson(shipAdapters);
      assertEquals(mapper.writeValueAsString(fleet),
          "{\"fleet\":[{\"coord\":{\"x\":3,\"y\":2},\"length\":3,\"direction\":\""
              + "HORIZONTAL\"},{\"coord\":{\"x\":1,\"y\":1},\"length\":4,\"direction\":\""
              + "VERTICAL\"}]}");
    } catch (JsonProcessingException e) {
      System.out.println("Test failed");
    }
  }
}

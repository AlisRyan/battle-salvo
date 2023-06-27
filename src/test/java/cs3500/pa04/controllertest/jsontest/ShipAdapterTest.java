package cs3500.pa04.controllertest.jsontest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.pa04.controller.json.CoordAdapter;
import cs3500.pa04.controller.json.ShipAdapter;
import cs3500.pa04.model.Coord;
import cs3500.pa04.model.Ship;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

/**
 * Tests the ship adapter class
 */
public class ShipAdapterTest {

  @Test
  public void checkShipAdapter() {
    try {
      ObjectMapper mapper = new ObjectMapper();
      Coord first = new Coord(2, 3);
      Coord second = new Coord(2, 4);
      Coord third = new Coord(2, 5);
      List<Coord> coords = new ArrayList<>(Arrays.asList(first, second, third));
      Ship ship = new Ship(coords);
      ShipAdapter shipNode = new ShipAdapter(mapper.convertValue(new CoordAdapter(ship.getStart()),
          JsonNode.class), ship.length(), ship.direction());
      assertEquals(mapper.writeValueAsString(shipNode),
          "{\"coord\":{\"x\":3,\"y\":2},\"length\":3,\"direction\":\"HORIZONTAL\"}");
    } catch (JsonProcessingException e) {
      System.out.println("Test failed");
    }
  }
}

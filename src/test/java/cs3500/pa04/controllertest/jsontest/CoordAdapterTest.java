package cs3500.pa04.controllertest.jsontest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.pa04.controller.json.CoordAdapter;
import cs3500.pa04.model.Coord;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

/**
 * Tests the coord adapter class
 */
public class CoordAdapterTest {

  @Test
  public void checkCoordAdapter() {
    try {
      Coord a = new Coord(3, 7);
      Coord b = new Coord(4, 5);
      CoordAdapter node1 = new CoordAdapter(a);
      CoordAdapter node2 = new CoordAdapter(b);
      assertEquals(node1.getX(), 7);
      assertEquals(node2.getX(), 5);
      assertEquals(node1.getY(), 3);
      assertEquals(node2.getY(), 4);
      List<CoordAdapter> coordList = new ArrayList<>(Arrays.asList(node1, node2));
      ObjectMapper mapper = new ObjectMapper();
      assertEquals(mapper.writeValueAsString(node1), "{\"x\":7,\"y\":3}");
      assertEquals(mapper.writeValueAsString(node2), "{\"x\":5,\"y\":4}");
      assertEquals(mapper.writeValueAsString(coordList), "[{\"x\":7,\"y\":3},{\"x\":5,\"y\":4}]");
    } catch (JsonProcessingException e) {
      System.out.println("Test failed");
    }
  }


}

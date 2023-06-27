package cs3500.pa04.controllertest.jsontest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.pa04.controller.json.CoordAdapter;
import cs3500.pa04.controller.json.CoordinatesJson;
import cs3500.pa04.controller.json.JsonUtils;
import cs3500.pa04.controller.json.MessageJson;
import cs3500.pa04.controller.json.MethodName;
import cs3500.pa04.model.Coord;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

/**
 * Tests for the message json record
 */
public class MessageJsonTest {
  @Test
  public void checkMessageJson() {
    try {
      Coord a = new Coord(3, 7);
      Coord b = new Coord(4, 5);
      CoordAdapter node1 = new CoordAdapter(a);
      CoordAdapter node2 = new CoordAdapter(b);
      List<CoordAdapter> coordList = new ArrayList<>(Arrays.asList(node1, node2));
      ObjectMapper mapper = new ObjectMapper();
      CoordinatesJson coordJson = new CoordinatesJson(coordList);
      assertEquals(mapper.writeValueAsString(coordJson), "{\"coordinates\":[{\"x\":7"
          + ",\"y\":3},{\"x\":5,\"y\":4}]}");
      JsonNode coordNode = JsonUtils.serializeRecord(coordJson);
      MessageJson msg = new MessageJson(MethodName.TAKESHOTS.toString(), coordNode);
      assertEquals(mapper.writeValueAsString(msg), "{\"method-name\":\"take-shots\",\""
          + "arguments\":{\"coordinates\":[{\"x\":7,\"y\":3},{\"x\":5,\"y\":4}]}}");
    } catch (JsonProcessingException e) {
      System.out.println("Test failed");
    }
  }
}

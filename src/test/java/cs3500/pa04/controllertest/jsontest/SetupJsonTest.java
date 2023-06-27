package cs3500.pa04.controllertest.jsontest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.pa04.controller.json.SetupJson;
import cs3500.pa04.model.ShipType;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

/**
 * Tests the setup json
 */
public class SetupJsonTest {
  @Test
  public void checkSetupJson() {
    try {
      ObjectMapper mapper = new ObjectMapper();
      Map<ShipType, Integer> specMap = new HashMap<>();
      specMap.put(ShipType.BATTLESHIP, 4);
      specMap.put(ShipType.SUBMARINE, 3);
      specMap.put(ShipType.DESTROYER, 1);
      specMap.put(ShipType.CARRIER, 2);
      JsonNode specs = mapper.readTree("{\"CARRIER\":2,\"BATTLESHIP\":4,"
          + "\"DESTROYER\":1,\"SUBMARINE\":3}");
      SetupJson setup = new SetupJson(10, 10, specs);
      assertEquals(setup.getSpecs(), specMap);
    } catch (JsonProcessingException e) {
      System.out.println("Test failed");
    }
  }


}

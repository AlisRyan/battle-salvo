package cs3500.pa04.controllertest.jsontest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.pa04.controller.json.JoinJson;
import org.junit.jupiter.api.Test;

/**
 * Tests the join json record
 */
public class JoinJsonTest {
  @Test
  public void checkJoinJson() {
    try {
      String name = "Alison";
      String gameType = "SINGLE";
      JoinJson join = new JoinJson(name, gameType);
      ObjectMapper mapper = new ObjectMapper();
      assertEquals(mapper.writeValueAsString(join), "{\"name\":\""
          + "Alison\",\"game-type\":\"SINGLE\"}");
    } catch (JsonProcessingException e) {
      System.out.println("Test failed");
    }
  }
}

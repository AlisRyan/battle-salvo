package cs3500.pa04.controllertest.jsontest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.pa04.controller.json.JoinJson;
import cs3500.pa04.controller.json.JsonUtils;
import org.junit.jupiter.api.Test;

/**
 * Tests the methods of the json utils class
 */
public class JsonUtilsTest {

  /**
   * Tests the serialize record method
   */
  @Test
  public void checkSerializeRecord() {
    try {
      ObjectMapper mapper = new ObjectMapper();
      JsonNode actual = JsonUtils.serializeRecord(new JoinJson("Alison", "SINGLE"));
      JsonNode expected = mapper.readTree("{\"name\":\"Alison\",\"game-type\":\"SINGLE\""
          + "}\n");
      assertEquals(actual, expected);
    } catch (JsonProcessingException e) {
      System.out.println("Test failed");
    }
  }

}

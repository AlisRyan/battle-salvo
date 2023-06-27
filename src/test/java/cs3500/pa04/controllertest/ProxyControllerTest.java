package cs3500.pa04.controllertest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.pa04.controller.ProxyController;
import cs3500.pa04.controller.SingleBoard;
import cs3500.pa04.controller.json.CoordAdapter;
import cs3500.pa04.controller.json.CoordinatesJson;
import cs3500.pa04.controller.json.EndGameJson;
import cs3500.pa04.controller.json.JsonUtils;
import cs3500.pa04.controller.json.MessageJson;
import cs3500.pa04.controller.json.MethodName;
import cs3500.pa04.controller.json.SetupJson;
import cs3500.pa04.model.Coord;
import cs3500.pa04.model.Player;
import cs3500.pa04.model.ShipType;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


/**
 * Test correct responses for different requests from the socket using a Mock Socket (mocket)
 */
public class ProxyControllerTest {

  private ByteArrayOutputStream testLog;
  private ProxyController controller;
  private Player ai;
  private SingleBoard aiBoards;
  private final ObjectMapper mapper = new ObjectMapper();

  private List<List<Coord>> aiBoard;

  /**
   * Reset the test log before each test is run.
   */
  @BeforeEach
  public void setup() {
    aiBoards = new SingleBoard();
    aiBoard = new ArrayList<>();
    ai = new MockPlayer(aiBoards, aiBoard);
    aiBoards.setPlayerBoard(ai, aiBoard);
    aiBoards.setRandom();
    this.testLog = new ByteArrayOutputStream(2048);
    assertEquals("", logToString());
  }

  /**
   * Check that the server returns a guess when given a hint.
   */
  @Test
  public void testVoidForJoin() {
    // Prepare sample message
    MessageJson joinJson = new MessageJson(MethodName.JOIN.toString(), mapper.createObjectNode());
    JsonNode sampleMessage = JsonUtils.serializeRecord(joinJson);

    // Create the client with all necessary messages
    Mocket socket = new Mocket(this.testLog, List.of(sampleMessage.toString()));

    // Create a Dealer
    try {
      this.controller = new ProxyController(socket, ai, aiBoards);
    } catch (IOException e) {
      fail(); // fail if the dealer can't be created
    }

    // run the dealer and verify the response
    this.controller.run();

    String expected = "{\"method-name\":\"join\",\"arguments\":{"
        + "\"name\":\"AlisRyan\",\"game-type\":\"SINGLE\"}}\n";
    assertEquals(expected, logToString());
  }

  @Test
  public void testVoidForSetup() {
    try {    // Prepare sample message
      JsonNode arguments = mapper.readTree("{\n"
          + "\"width\":6,\"height\":6,\"fleet-spec\":{\"CARRIER\":1,\"BATTLESHIP\":1,"
          + "\"DESTROYER\":1,\"SUBMARINE\":1}}\n");
      SetupJson setup = this.mapper.convertValue(arguments, SetupJson.class);
      JsonNode setupNode = JsonUtils.serializeRecord(setup);
      MessageJson setupJson = new MessageJson(MethodName.SETUP.toString(), setupNode);
      JsonNode sampleMessage = JsonUtils.serializeRecord(setupJson);
      Mocket socket = new Mocket(this.testLog, List.of(sampleMessage.toString()));
      try {
        this.controller = new ProxyController(socket, ai, aiBoards);
      } catch (IOException e) {
        fail(); // fail if the dealer can't be created
      }
      this.controller.run();
      String expected =
          "{\"method-name\":\"setup\",\"arguments\":{\"fleet\":[{\"coord\":{\"x\":0,\"y\":0},\""
              + "length\":3,\"direction\":\"VERTICAL\"},{\"coord\":{\"x\":1,\"y\":0},\"length\":4,"
              + "\"direction\":\"VERTICAL\"},{\"coord\":{\"x\":2,\"y\":0},\"length\":5,\"direction"
              + "\":\"VERTICAL\"},{\"coord\":{\"x\":3,\"y\":0},\"length\":6,\"direction\":\"VERTIC"
              + "AL\"}]}}\n";
      assertEquals(expected, logToString());
    } catch (JsonProcessingException e) {
      System.out.println("Test failed");
    }
  }

  @Test
  public void testVoidForReportDamage() {
    // Prepare sample message
    aiBoards.setUpBoards(6, 6);
    Map<ShipType, Integer> specs = new HashMap<>();
    specs.put(ShipType.CARRIER, 1);
    specs.put(ShipType.BATTLESHIP, 1);
    specs.put(ShipType.DESTROYER, 1);
    specs.put(ShipType.SUBMARINE, 1);
    aiBoards.setup(specs);
    List<CoordAdapter> coordAdapters = new ArrayList<>(Arrays.asList(
        new CoordAdapter(new Coord(2, 3)), new CoordAdapter(new Coord(4, 4))));
    CoordinatesJson coords = new CoordinatesJson(coordAdapters);
    JsonNode coordNode = JsonUtils.serializeRecord(coords);
    MessageJson coordJson = new MessageJson(MethodName.REPORTDAMAGE.toString(), coordNode);
    JsonNode sampleMessage = JsonUtils.serializeRecord(coordJson);
    Mocket socket = new Mocket(this.testLog, List.of(sampleMessage.toString()));
    try {
      this.controller = new ProxyController(socket, ai, aiBoards);
    } catch (IOException e) {
      fail(); // fail if the dealer can't be created
    }
    this.controller.run();
    String expected =
        "{\"method-name\":\"report-damage\",\"arguments\":{\"coordinates\":[{\"x\":3,\"y\":2}]}}\n";
    assertEquals(expected, logToString());
  }

  @Test
  public void testVoidForTakeShots() {
    aiBoards.setUpBoards(6, 6);
    Map<ShipType, Integer> specs = new HashMap<>();
    specs.put(ShipType.CARRIER, 1);
    specs.put(ShipType.BATTLESHIP, 1);
    specs.put(ShipType.DESTROYER, 1);
    specs.put(ShipType.SUBMARINE, 1);
    aiBoards.setup(specs);
    for (List<Coord> coords : aiBoard) {
      for (Coord c : coords) {
        System.out.println(c.getX() + " " + c.getY() + " " + c.getStatus());
      }
    }
    // Prepare sample message
    MessageJson takeShotsJson = new MessageJson(MethodName.TAKESHOTS.toString(),
        mapper.createObjectNode());
    JsonNode sampleMessage = JsonUtils.serializeRecord(takeShotsJson);
    // Create the client with all necessary messages
    Mocket socket = new Mocket(this.testLog, List.of(sampleMessage.toString()));
    // Create a Dealer
    try {
      this.controller = new ProxyController(socket, ai, aiBoards);
    } catch (IOException e) {
      fail(); // fail if the dealer can't be created
    }
    // run the dealer and verify the response
    this.controller.run();
    String expected =
        "{\"method-name\":\"take-shots\",\"arguments\":{\"coordinates\":[{\"x\":0,\"y\":0},"
            + "{\"x\":3,\"y\":2},{\"x\":5,\"y\":4},{\"x\":1,\"y\":1}]}}\n";
    assertEquals(expected, logToString());
  }


  @Test
  public void testVoidForEndGame() {
    // Prepare sample message
    EndGameJson endGame = new EndGameJson("WIN", "Player 1 sank all of "
        + "Player 2's ships");
    JsonNode endGameNode = JsonUtils.serializeRecord(endGame);
    MessageJson endGameJson = new MessageJson(MethodName.ENDGAME.toString(),
        endGameNode);
    JsonNode sampleMessage = JsonUtils.serializeRecord(endGameJson);
    // Create the client with all necessary messages
    Mocket socket = new Mocket(this.testLog, List.of(sampleMessage.toString()));
    // Create a Dealer
    try {
      this.controller = new ProxyController(socket, ai, aiBoards);
    } catch (IOException e) {
      fail(); // fail if the dealer can't be created
    }
    // run the dealer and verify the response
    this.controller.run();
    String expected =
        "{\"method-name\":\"end-game\",\"arguments\":{}}\n"
            + "\"void\"\n";
    assertEquals(expected, logToString());
  }

  @Test
  public void testVoidForSuccessfulHits() {
    // Prepare sample message
    List<CoordAdapter> hits = new ArrayList<>(Arrays.asList(new CoordAdapter(new Coord(2, 2)),
        new CoordAdapter(new Coord(3, 5))));
    CoordinatesJson hitsJson = new CoordinatesJson(hits);
    JsonNode coordNode = JsonUtils.serializeRecord(hitsJson);
    MessageJson coordJson = new MessageJson(MethodName.SUCCESSFULHITS.toString(),
        coordNode);
    JsonNode sampleMessage = JsonUtils.serializeRecord(coordJson);
    // Create the client with all necessary messages
    Mocket socket = new Mocket(this.testLog, List.of(sampleMessage.toString()));
    // Create a Dealer
    try {
      this.controller = new ProxyController(socket, ai, aiBoards);
    } catch (IOException e) {
      fail(); // fail if the dealer can't be created
    }
    // run the dealer and verify the response
    this.controller.run();
    String expected =
        "{\"method-name\":\"successful-hits\",\"arguments\":{}}\n";
    assertEquals(expected, logToString());
  }


  /**
   * Converts the ByteArrayOutputStream log to a string in UTF_8 format
   *
   * @return String representing the current log buffer
   */
  private String logToString() {
    return testLog.toString(StandardCharsets.UTF_8);
  }

}

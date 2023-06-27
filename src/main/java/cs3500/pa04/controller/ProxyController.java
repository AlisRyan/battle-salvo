package cs3500.pa04.controller;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.pa04.controller.json.CoordAdapter;
import cs3500.pa04.controller.json.CoordinatesJson;
import cs3500.pa04.controller.json.FleetJson;
import cs3500.pa04.controller.json.JoinJson;
import cs3500.pa04.controller.json.JsonUtils;
import cs3500.pa04.controller.json.MessageJson;
import cs3500.pa04.controller.json.MethodName;
import cs3500.pa04.controller.json.SetupJson;
import cs3500.pa04.controller.json.ShipAdapter;
import cs3500.pa04.model.Coord;
import cs3500.pa04.model.Player;
import cs3500.pa04.model.Ship;
import cs3500.pa04.model.ShipType;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * The controller for the proxy
 */
public class ProxyController {
  private final Socket server;
  private final InputStream in;
  private final PrintStream out;
  private final Player player;
  private final ObjectMapper mapper = new ObjectMapper();
  private final SingleBoard boards;
  private static final JsonNode VOID_RESPONSE =
      new ObjectMapper().getNodeFactory().textNode("void");

  /**
   * @param server the server of this game
   * @param player the player playing
   * @param boards the boards object of this player
   * @throws IOException an I/O exception
   */
  public ProxyController(Socket server, Player player, SingleBoard boards) throws IOException {
    this.server = server;
    this.in = server.getInputStream();
    this.boards = boards;
    this.out = new PrintStream(server.getOutputStream());
    this.player = player;
  }

  /**
   * Runs the program
   */
  public void run() {
    try {
      JsonParser parser = this.mapper.getFactory().createParser(this.in);

      while (!this.server.isClosed()) {
        MessageJson message = parser.readValueAs(MessageJson.class);
        delegateMessage(message);
      }
    } catch (IOException e) {
      // Disconnected from server or parsing exception
    }
  }


  /**
   * Determines the type of request the server has sent ("guess" or "win") and delegates to the
   * corresponding helper method with the message arguments.
   *
   * @param message the MessageJSON used to determine what the server has sent
   */
  private void delegateMessage(MessageJson message) throws IOException {
    MethodName name = MethodName.whichMethod(message.method());
    JsonNode arguments = message.arguments();

    switch (name) {
      case SETUP -> handleSetup(arguments);
      case TAKESHOTS -> handleTakeShots();
      case REPORTDAMAGE -> handleReportDamage(arguments);
      case SUCCESSFULHITS -> handleSuccessfulHits(arguments);
      case JOIN -> handleJoin();
      default -> handleEndGame();
    }
  }

  /**
   * Handles the end of the game
   */
  private void handleEndGame() throws IOException {
    MessageJson response =
        new MessageJson(MethodName.ENDGAME.toString(), mapper.createObjectNode());
    JsonNode jsonResponse = JsonUtils.serializeRecord(response);
    this.out.println(jsonResponse);
    this.out.println(VOID_RESPONSE);
    server.close();
  }

  /**
   * Handles reporting damage
   *
   * @param arguments the message from the server
   * @throws JsonProcessingException errors if the given coordinates were improperly formatted
   */
  private void handleReportDamage(JsonNode arguments) throws JsonProcessingException {
    CoordinatesJson volley = new CoordinatesJson(
        mapper.readValue(arguments.get("coordinates").toString(),
            new TypeReference<>() {
            }));
    List<CoordAdapter> shots = volley.volley();
    List<Coord> opponentShots = new ArrayList<>();
    for (CoordAdapter shot : shots) {
      opponentShots.add(shot.toCoord());
    }
    List<Coord> hits = player.reportDamage(opponentShots);
    List<CoordAdapter> adaptedHits = new ArrayList<>();
    for (Coord c : hits) {
      adaptedHits.add(new CoordAdapter(c));
    }
    CoordinatesJson hitsJson = new CoordinatesJson(adaptedHits);
    JsonNode volleyNode = JsonUtils.serializeRecord(hitsJson);
    MessageJson response = new MessageJson(MethodName.REPORTDAMAGE.toString(), volleyNode);
    JsonNode jsonResponse = JsonUtils.serializeRecord(response);
    this.out.println(jsonResponse);
  }

  /**
   * Handles successful hits
   */
  private void handleSuccessfulHits(JsonNode arguments) throws JsonProcessingException {
    CoordinatesJson volley = new CoordinatesJson(
        mapper.readValue(arguments.get("coordinates").toString(),
            new TypeReference<>() {
            }));
    List<CoordAdapter> shots = volley.volley();
    List<Coord> opponentShots = new ArrayList<>();
    for (CoordAdapter shot : shots) {
      opponentShots.add(shot.toCoord());
    }
    player.successfulHits(opponentShots);
    MessageJson response =
        new MessageJson(MethodName.SUCCESSFULHITS.toString(), mapper.createObjectNode());
    JsonNode jsonResponse = JsonUtils.serializeRecord(response);
    this.out.println(jsonResponse);
  }

  /**
   * Handles joining
   */
  private void handleJoin() {
    JoinJson join = new JoinJson(player.name(), "SINGLE");
    JsonNode joinNode = JsonUtils.serializeRecord(join);
    MessageJson response = new MessageJson(MethodName.JOIN.toString(), joinNode);
    JsonNode jsonResponse = JsonUtils.serializeRecord(response);
    this.out.println(jsonResponse);
  }

  /**
   * Handles this player taking shots
   */
  private void handleTakeShots() {
    List<Coord> shots = player.takeShots();
    List<CoordAdapter> coordJson = new ArrayList<>();
    for (Coord shot : shots) {
      coordJson.add(new CoordAdapter(shot));
    }
    CoordinatesJson coords = new CoordinatesJson(coordJson);
    JsonNode coordNode = JsonUtils.serializeRecord(coords);
    MessageJson response = new MessageJson(MethodName.TAKESHOTS.toString(), coordNode);
    JsonNode jsonResponse = JsonUtils.serializeRecord(response);
    this.out.println(jsonResponse);
  }

  /**
   * Handles setting up the board
   *
   * @param arguments the json given from the server
   */
  private void handleSetup(JsonNode arguments) {
    SetupJson setup = this.mapper.convertValue(arguments, SetupJson.class);
    Map<ShipType, Integer> specs = setup.getSpecs();
    boards.setUpBoards(setup.height(), setup.width());
    List<Ship> ships = player.setup(setup.height(), setup.width(), specs);
    List<JsonNode> shipJsons = new ArrayList<>();
    for (Ship ship : ships) {
      ShipAdapter shipJson = new ShipAdapter(
          mapper.convertValue(new CoordAdapter(ship.getStart()), JsonNode.class),
          ship.length(), ship.direction());
      shipJsons.add(mapper.convertValue(shipJson, JsonNode.class));
    }
    FleetJson fleet = new FleetJson(shipJsons);
    JsonNode fleetNode = JsonUtils.serializeRecord(fleet);
    MessageJson response = new MessageJson(MethodName.SETUP.toString(), fleetNode);
    JsonNode jsonResponse = JsonUtils.serializeRecord(response);
    this.out.println(jsonResponse);
  }
}

package cs3500.pa04.controllertest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import cs3500.pa04.controller.Controller;
import cs3500.pa04.controller.SingleBoard;
import cs3500.pa04.model.AiPlayer;
import cs3500.pa04.model.Coord;
import cs3500.pa04.model.GameResult;
import cs3500.pa04.model.ManualPlayer;
import cs3500.pa04.model.Player;
import cs3500.pa04.view.View;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for the controller class
 */
public class ControllerTest {
  private Controller controller;

  /**
   * Initializes the fields for testing
   */
  @BeforeEach
  public void initFields() {
    SingleBoard aiBoards = new SingleBoard();
    List<List<Coord>> aiBoard = new ArrayList<>();
    Player ai = new AiPlayer(aiBoards, aiBoard);
    aiBoards.setPlayerBoard(ai, aiBoard);
    SingleBoard manualBoards = new SingleBoard();
    List<List<Coord>> manualBoard = new ArrayList<>();
    Player manual = new ManualPlayer(manualBoards, manualBoard);
    manualBoards.setPlayerBoard(manual, manualBoard);
    View view = new View();
    controller = new Controller(manual, ai, manualBoards, aiBoards, view);
    String input = "9" + System.getProperty("line.separator") + "7";
    InputStream in = new ByteArrayInputStream(input.getBytes());
    System.setIn(in);
    controller.setSize();
  }

  @Test
  public void checkSetSize() {
    String input = "1" + System.getProperty("line.separator") + "2";
    InputStream in = new ByteArrayInputStream(input.getBytes());
    System.setIn(in);
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));
    controller.setSize();
    input = "16" + System.getProperty("line.separator") + "2";
    in = new ByteArrayInputStream(input.getBytes());
    System.setIn(in);
    controller.setSize();
    input = "9" + System.getProperty("line.separator") + "2";
    in = new ByteArrayInputStream(input.getBytes());
    System.setIn(in);
    controller.setSize();
    input = "9" + System.getProperty("line.separator") + "16";
    in = new ByteArrayInputStream(input.getBytes());
    System.setIn(in);
    controller.setSize();
    input = "7" + System.getProperty("line.separator") + "9";
    in = new ByteArrayInputStream(input.getBytes());
    System.setIn(in);
    controller.setSize();
    String error = """
        Uh Oh! You've entered invalid dimensions. Please remember that the height and width
        of the game must be in the range (6, 15), inclusive. Try again!
        ------------------------------------------------------
        """;
    String expected = error + error + error + error
        + "Please enter your fleet in the order [Carrier, Battleship, Destroyer, Submarine].\n"
        + "--------------------------------------------------------------------------------\n";
    assertEquals(expected, outContent.toString());
  }

  @Test
  public void checkSetFleetSpecs() {
    String input = "0" + System.getProperty("line.separator") + "1"
        + System.getProperty("line.separator") + "1" + System.getProperty("line.separator") + "1";
    System.setIn(new ByteArrayInputStream(input.getBytes()));
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));
    controller.setFleetSpecs();
    input = "1" + System.getProperty("line.separator") + "0"
        + System.getProperty("line.separator") + "1" + System.getProperty("line.separator") + "1";
    System.setIn(new ByteArrayInputStream(input.getBytes()));
    controller.setFleetSpecs();
    input = "1" + System.getProperty("line.separator") + "1"
        + System.getProperty("line.separator") + "0" + System.getProperty("line.separator") + "1";
    System.setIn(new ByteArrayInputStream(input.getBytes()));
    controller.setFleetSpecs();
    input = "1" + System.getProperty("line.separator") + "1"
        + System.getProperty("line.separator") + "1" + System.getProperty("line.separator") + "0";
    System.setIn(new ByteArrayInputStream(input.getBytes()));
    controller.setFleetSpecs();
    String error = """
        Uh Oh! You've entered invalid fleet sizes.
        Please enter your fleet in the order [Carrier, Battleship, Destroyer, Submarine].
        --------------------------------------------------------------------------------
        """;
    assertEquals(error + error + error + error, outContent.toString());
    input = "1" + System.getProperty("line.separator") + "1"
        + System.getProperty("line.separator") + "1" + System.getProperty("line.separator") + "1";
    System.setIn(new ByteArrayInputStream(input.getBytes()));
    controller.setFleetSpecs();
    assertEquals(error + error + error + error
            + "Please enter 4 Shots:\n"
            + "------------------------------------------------------------------\n",
        outContent.toString());
  }

  @Test
  public void checkGetShots() {
    String input = "1" + System.getProperty("line.separator") + "2"
        + System.getProperty("line.separator") + "3" + System.getProperty("line.separator") + "4";
    System.setIn(new ByteArrayInputStream(input.getBytes()));
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));
    ArrayList<Coord> coords = new ArrayList<>(Controller.getShots(2));
    assertTrue(coords.get(0).atPosn(new Coord(1, 2)));
    assertTrue(coords.get(1).atPosn(new Coord(3, 4)));
    assertEquals(coords.size(), 2);
    input = "2";
    System.setIn(new ByteArrayInputStream(input.getBytes()));
    outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));
    assertThrows(NoSuchElementException.class, () -> Controller.getShots(4));
  }

  @Test
  public void checkGameOver() {
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));
    controller.gameOver(GameResult.WON, "Hey!");
    assertEquals("Hey!\n", outContent.toString());
    outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));
    controller.gameOver(GameResult.LOST, "Hey!");
    assertEquals("You and your opponent drew!\n", outContent.toString());
  }
}

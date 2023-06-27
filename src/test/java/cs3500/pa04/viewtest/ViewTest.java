package cs3500.pa04.viewtest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cs3500.pa04.controller.Controller;
import cs3500.pa04.controller.PlayerType;
import cs3500.pa04.controller.SingleBoard;
import cs3500.pa04.model.AiPlayer;
import cs3500.pa04.model.Coord;
import cs3500.pa04.model.ManualPlayer;
import cs3500.pa04.model.Player;
import cs3500.pa04.view.View;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for methods of the view class
 */
public class ViewTest {
  View view;

  /**
   * Initiatilizes the fields for testing
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
    view = new View();
    Controller controller = new Controller(manual, ai, manualBoards, aiBoards, view);
  }

  @Test
  public void checkWelcomeUser() {
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));
    view.welcomeUser();
    String expected = """
        Hello! Welcome to the OOD BattleSalvo Game!
        Please enter a valid height and width below:\s
        ------------------------------------------------------
        """;
    assertEquals(expected, outContent.toString());
  }

  @Test
  public void checkWelcomeUserFailed() {
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));
    view.welcomeFailed();
    String expected = """
        Uh Oh! You've entered invalid dimensions. Please remember that the height and width
        of the game must be in the range (6, 15), inclusive. Try again!
        ------------------------------------------------------
        """;
    assertEquals(expected, outContent.toString());
  }

  @Test
  public void checkFleetSpecs() {
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));
    view.fleetSpecs();
    String expected =
        """
            Please enter your fleet in the order [Carrier, Battleship, Destroyer, Submarine].
            --------------------------------------------------------------------------------
            """;
    assertEquals(expected, outContent.toString());
  }

  @Test
  public void checkFleetSpecsFailed() {
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));
    view.fleetSpecsFailed();
    String expected = """
        Uh Oh! You've entered invalid fleet sizes.
        Please enter your fleet in the order [Carrier, Battleship, Destroyer, Submarine].
        --------------------------------------------------------------------------------
        """;
    assertEquals(expected, outContent.toString());
  }

  @Test
  public void checkGetShots() {
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));
    View.getShots(5);
    String expected = """
        Please enter 5 Shots:
        ------------------------------------------------------------------
        """;
    assertEquals(expected, outContent.toString());
  }

  @Test
  public void checkDisplayBoard() {
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));
    List<List<String>> board = new ArrayList<>();
    board.add(new ArrayList<>(Arrays.asList("A", "B", "C")));
    board.add(new ArrayList<>(Arrays.asList("D", "E", "F")));
    board.add(new ArrayList<>(Arrays.asList("G", "H", "I")));
    view.displayBoard(PlayerType.AI, board);
    view.displayBoard(PlayerType.USER, board);
    String expected = """
        Opponent Board Data:
        A B C\s
        D E F\s
        G H I\s
        Your Board:
        A B C\s
        D E F\s
        G H I\s
        """;
    assertEquals(expected, outContent.toString());
  }

  @Test
  public void checkGameEnd() {
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));
    view.gameEnd("its over :(");
    assertEquals("its over :(\n", outContent.toString());
  }

}

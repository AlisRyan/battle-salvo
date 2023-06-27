package cs3500.pa04.controllertest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import cs3500.pa04.controller.Controller;
import cs3500.pa04.controller.PlayerType;
import cs3500.pa04.controller.SingleBoard;
import cs3500.pa04.model.AiPlayer;
import cs3500.pa04.model.Coord;
import cs3500.pa04.model.GameResult;
import cs3500.pa04.model.ManualPlayer;
import cs3500.pa04.model.Player;
import cs3500.pa04.model.Status;
import cs3500.pa04.view.View;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests the methods of the single board class
 */
public class SingleBoardTest {

  private SingleBoard aiBoards;
  private SingleBoard manualBoards;
  List<List<Coord>> aiBoard;
  List<List<Coord>> manualBoard;
  Controller controller;

  /**
   * Initializes the fields for testing
   */
  @BeforeEach
  public void initFields() {
    aiBoards = new SingleBoard();
    aiBoard = new ArrayList<>();
    Player ai = new AiPlayer(aiBoards, aiBoard);
    aiBoards.setPlayerBoard(ai, aiBoard);
    aiBoards.setRandom();
    manualBoards = new SingleBoard();
    manualBoard = new ArrayList<>();
    Player manual = new ManualPlayer(manualBoards, manualBoard);
    manualBoards.setRandom();
    manualBoards.setPlayerBoard(manual, manualBoard);
    View view = new View();
    controller = new Controller(manual, ai, manualBoards, aiBoards, view);
    String input = "6" + System.getProperty("line.separator") + "6";
    InputStream in = new ByteArrayInputStream(input.getBytes());
    System.setIn(in);
    controller.setSize();
  }

  @Test
  public void checkUpdateBoards() {
    manualBoards.makeShip(3);
    manualBoards.updateBoards(new ArrayList<>(Arrays.asList(manualBoard.get(2).get(2),
        manualBoard.get(4).get(3))));
    assertEquals(manualBoard.get(2).get(2).getStatus(), Status.MISSED);
    assertEquals(manualBoard.get(4).get(3).getStatus(), Status.HIT);
    aiBoards.makeShip(4);
    aiBoards.updateBoards(new ArrayList<>(Arrays.asList(aiBoard.get(2).get(2),
        aiBoard.get(4).get(3))));
    assertEquals(aiBoard.get(2).get(2).getStatus(), Status.HIT);
    assertEquals(aiBoard.get(4).get(3).getStatus(), Status.MISSED);
  }

  @Test
  public void checkSendBoard() {
    manualBoards.makeShip(3);
    aiBoards.makeShip(4);
    manualBoards.updateBoards(new ArrayList<>(Arrays.asList(manualBoard.get(2).get(2),
        manualBoard.get(4).get(3))));
    aiBoards.updateBoards(new ArrayList<>(Arrays.asList(aiBoard.get(2).get(2),
        aiBoard.get(4).get(3))));
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));
    manualBoards.sendBoard(PlayerType.USER);
    assertEquals("""
        Your Board:
        0 0 0 0 0 0\s
        0 0 0 0 0 0\s
        0 0 M 0 0 0\s
        0 0 0 S 0 0\s
        0 0 0 H 0 0\s
        0 0 0 S 0 0\s
        """, outContent.toString());
    outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));
    aiBoards.sendBoard(PlayerType.AI);
    assertEquals("""
        Opponent Board Data:
        0 0 0 0 0 0\s
        0 0 0 0 0 0\s
        0 0 H 0 0 0\s
        0 0 0 0 0 0\s
        0 0 0 M 0 0\s
        0 0 0 0 0 0\s
        """, outContent.toString());
  }

  @Test
  public void checkMakeShip() {
    manualBoards.makeShip(3);
    assertEquals(manualBoard.get(3).get(3).getStatus(), Status.OCCUPIED);
    assertEquals(manualBoard.get(4).get(3).getStatus(), Status.OCCUPIED);
    assertEquals(manualBoard.get(5).get(3).getStatus(), Status.OCCUPIED);
  }

  @Test
  public void checkGameOver() {
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));
    manualBoards.gameOver(GameResult.WON, "Hey!");
    assertEquals("Hey!\n", outContent.toString());
    outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));
    aiBoards.gameOver(GameResult.LOST, "Hey!");
    assertEquals("You and your opponent drew!\n", outContent.toString());
  }

  @Test
  public void checkSetUpBoards() {
    assertEquals(manualBoard.size(), 6);
    for (int i = 0; i < manualBoard.size(); i++) {
      assertEquals(manualBoard.get(i).size(), 6);
      for (int j = 0; j < manualBoard.size(); j++) {
        assertTrue(manualBoard.get(i).get(j).atPosn(new Coord(i, j)));
      }
    }
  }

}

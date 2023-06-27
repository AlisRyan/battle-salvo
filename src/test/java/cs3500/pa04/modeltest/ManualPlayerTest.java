package cs3500.pa04.modeltest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import cs3500.pa04.controller.Controller;
import cs3500.pa04.controller.SingleBoard;
import cs3500.pa04.model.AiPlayer;
import cs3500.pa04.model.Coord;
import cs3500.pa04.model.ManualPlayer;
import cs3500.pa04.model.Player;
import cs3500.pa04.model.Ship;
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
 * Tests for methods of the ManualPlayer class
 */
public class ManualPlayerTest {
  private ManualPlayer player;
  private List<Coord> acoords;
  private List<Coord> bcoords;
  private List<List<Coord>> board;

  /**
   * Initializes the fields for testing
   */
  @BeforeEach
  public void initFields() {
    board = new ArrayList<>();
    for (int i = 0; i < 6; i++) {
      board.add(new ArrayList<>());
      for (int j = 0; j < 6; j++) {
        board.get(i).add(new Coord(i, j));
      }
    }
    acoords = new ArrayList<>(Arrays.asList(board.get(0).get(0),
        board.get(0).get(1), board.get(0).get(2)));
    bcoords = new ArrayList<>(Arrays.asList(board.get(2).get(3),
        board.get(2).get(4), board.get(2).get(5)));
    List<Ship> ships = new ArrayList<>(Arrays.asList(new Ship(acoords), new Ship(bcoords)));
    SingleBoard boards = new SingleBoard();
    player = new ManualPlayer(boards, board, ships);
    boards.setPlayerBoard(player, board);
    SingleBoard aiBoards = new SingleBoard();
    List<List<Coord>> aiBoard = new ArrayList<>();
    Player ai = new AiPlayer(aiBoards, aiBoard);
    aiBoards.setPlayerBoard(ai, aiBoard);
    View view = new View();
    Controller controller = new Controller(player, ai, boards, aiBoards, view);
  }

  @Test
  public void checkName() {
    assertEquals(player.name(), "Player");
  }

  @Test
  public void checkReportDamage() {
    player.reportDamage(new ArrayList<>(Arrays.asList(board.get(2).get(4), board.get(5).get(5))));
    assertEquals(board.get(2).get(4).getStatus(), Status.HIT);
    assertEquals(board.get(5).get(5).getStatus(), Status.MISSED);
  }

  @Test
  public void checkTakeShots() {
    String input = "1" + System.getProperty("line.separator") + "2"
        + System.getProperty("line.separator") + "3" + System.getProperty("line.separator") + "4";
    InputStream in = new ByteArrayInputStream(input.getBytes());
    System.setIn(in);
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));
    List<Coord> shots = player.takeShots();
    assertTrue(shots.get(0).atPosn(new Coord(1, 2)));
    assertTrue(shots.get(1).atPosn(new Coord(3, 4)));
    String expected = """
        Please enter 2 Shots:
        ------------------------------------------------------------------
        """;
    assertEquals(expected, outContent.toString());
    for (Coord c : acoords) {
      c.updateStatus(Status.HIT);
    }
    input = "3" + System.getProperty("line.separator") + "4";
    in = new ByteArrayInputStream(input.getBytes());
    System.setIn(in);
    shots = player.takeShots();
    assertTrue(shots.get(0).atPosn(new Coord(3, 4)));
  }

  @Test
  public void checkSuccessfulHits() {
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));
    for (Coord c : acoords) {
      c.updateStatus(Status.HIT);
    }
    player.successfulHits(new ArrayList<>());
    assertEquals("", outContent.toString());
    for (Coord c : bcoords) {
      c.updateStatus(Status.HIT);
    }
    player.successfulHits(new ArrayList<>());
    assertEquals("The opponent sunk all your ships :(\n", outContent.toString());
  }

}

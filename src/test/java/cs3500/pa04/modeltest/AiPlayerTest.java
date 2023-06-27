package cs3500.pa04.modeltest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import cs3500.pa04.controller.Controller;
import cs3500.pa04.controller.SingleBoard;
import cs3500.pa04.model.AiPlayer;
import cs3500.pa04.model.Coord;
import cs3500.pa04.model.ManualPlayer;
import cs3500.pa04.model.Player;
import cs3500.pa04.model.Ship;
import cs3500.pa04.model.ShipType;
import cs3500.pa04.model.Status;
import cs3500.pa04.view.View;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for the AIPlayer class
 */
public class AiPlayerTest {
  private AiPlayer player;
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
    player = new AiPlayer(boards, board, ships);
    player.setup(6, 6, new HashMap<>());
    boards.setPlayerBoard(player, board);
    SingleBoard manualBoards = new SingleBoard();
    List<List<Coord>> manualBoard = new ArrayList<>();
    Player manual = new ManualPlayer(manualBoards, manualBoard);
    manualBoards.setPlayerBoard(manual, manualBoard);
    View view = new View();
    Controller controller = new Controller(manual, player, manualBoards, boards, view);
  }

  @Test
  public void checkName() {
    assertEquals(player.name(), "AlisRyan");
  }

  @Test
  public void checkReportDamage() {
    player.reportDamage(new ArrayList<>(Arrays.asList(board.get(2).get(4), board.get(5).get(5))));
    assertEquals(board.get(2).get(4).getStatus(), Status.HIT);
    assertEquals(board.get(5).get(5).getStatus(), Status.MISSED);
  }

  @Test
  public void checkTakeShots() {
    List<Coord> shots = player.takeShots();
    assertEquals(shots.size(), 2);
    assertFalse(shots.get(0).atPosn(shots.get(1)));
    for (Coord c : acoords) {
      c.updateStatus(Status.HIT);
    }
    player.takeShots();
    player.takeShots();
    player.takeShots();
    player.takeShots();
    player.takeShots();
    player.takeShots();
    player.takeShots();
    player.takeShots();
    player.takeShots();
    player.takeShots();
    player.takeShots();
    player.takeShots();
    List<Coord> shots2 = player.takeShots();
    assertEquals(shots2.size(), 1);
    for (Coord c : shots) {
      assertFalse(c.atPosn(shots2.get(0)));
    }
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
    assertEquals("You sunk all the opponent's ships!\n", outContent.toString());
  }
}

package cs3500.pa04.controllertest;

import cs3500.pa04.controller.SingleBoard;
import cs3500.pa04.model.AiPlayer;
import cs3500.pa04.model.Coord;
import cs3500.pa04.model.Ship;
import cs3500.pa04.model.ShipType;
import cs3500.pa04.model.Status;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Mock Ai Player used for testing the proxy controller
 */
public class MockPlayer extends AiPlayer {

  private final SingleBoard boards;
  private final List<List<Coord>> board;
  private final List<Ship> ships = new ArrayList<>();
  private final List<Coord> previousShots = new ArrayList<>();

  /**
   * Constructor
   *
   * @param boards the boards object
   * @param board  the board
   */
  public MockPlayer(SingleBoard boards,
                    List<List<Coord>> board) {
    super(boards, board);
    this.boards = boards;
    this.board = board;
  }

  /**
   * Given the specifications for a BattleSalvo board, return a list of ships with their locations
   * on the board.
   *
   * @param height         the height of the board, range: [6, 15] inclusive
   * @param width          the width of the board, range: [6, 15] inclusive
   * @param specifications a map of ship type to the number of occurrences each ship should
   *                       appear on the board
   * @return the placements of each ship on the board
   */
  @Override
  public List<Ship> setup(int height, int width, Map<ShipType, Integer> specifications) {
    Ship a = new Ship(new ArrayList<>(
        Arrays.asList(board.get(0).get(0), board.get(1).get(0), board.get(2).get(0))));
    Ship b = new Ship(
        new ArrayList<>(Arrays.asList(board.get(0).get(1), board.get(1).get(1), board.get(2).get(1),
            board.get(3).get(1))));
    Ship c = new Ship(
        new ArrayList<>(Arrays.asList(board.get(0).get(2), board.get(1).get(2), board.get(2).get(2),
            board.get(3).get(2), board.get(4).get(2))));
    Ship d = new Ship(
        new ArrayList<>(Arrays.asList(board.get(0).get(3), board.get(1).get(3), board.get(2).get(3),
            board.get(3).get(3), board.get(4).get(3), board.get(5).get(3))));
    return new ArrayList<>(Arrays.asList(a, b, c, d));
  }

  /**
   * Returns this player's shots on the opponent's board. The number of shots returned should
   * equal the number of ships on this player's board that have not sunk.
   *
   * @return the locations of shots on the opponent's board
   */
  @Override
  public List<Coord> takeShots() {
    return new ArrayList<>(Arrays.asList(new Coord(0, 0), new Coord(2, 3), new Coord(4, 5),
        new Coord(1, 1)));
  }

  /**
   * Given the list of shots the opponent has fired on this player's board, report which
   * shots hit a ship on this player's board.
   *
   * @param opponentShotsOnBoard the opponent's shots on this player's board
   * @return a filtered list of the given shots that contain all locations of shots that hit a ship
   */
  @Override
  public List<Coord> reportDamage(List<Coord> opponentShotsOnBoard) {
    ArrayList<Coord> hit = new ArrayList<>();
    for (Coord shot : opponentShotsOnBoard) {
      for (List<Coord> coords : board) {
        for (Coord coord : coords) {
          if (shot.atPosn(coord)) {
            if (coord.getStatus().equals(Status.OCCUPIED)) {
              hit.add(shot);
              coord.updateStatus(Status.HIT);
            } else {
              coord.updateStatus(Status.MISSED);
            }
          }
        }
      }
    }
    return hit;
  }

  /**
   * Reports to this player what shots in their previous volley returned from takeShots()
   * successfully hit an opponent's ship.
   *
   * @param shotsThatHitOpponentShips the list of shots that successfully hit the opponent's ships
   */
  @Override
  public void successfulHits(List<Coord> shotsThatHitOpponentShips) {
    boolean won = true;
    for (Ship s : ships) {
      if (!s.isSunk()) {
        won = false;
      }
    }
  }
}

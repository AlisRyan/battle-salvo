package cs3500.pa04.model;

import cs3500.pa04.controller.SingleBoard;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Represents an AI Player
 */
public class AiPlayer implements Player {
  private final SingleBoard boards;
  private final List<List<Coord>> board;
  private List<Ship> ships = new ArrayList<>();
  private final List<Coord> previousShots = new ArrayList<>();
  private final Shooter shooter = new Shooter();

  /**
   * Constructor
   *
   * @param boards the boards object
   * @param board  the board
   */
  public AiPlayer(SingleBoard boards, List<List<Coord>> board) {
    this.boards = boards;
    this.board = board;
  }

  /**
   * Constructor for testing
   *
   * @param boards the boards object
   * @param board  the board
   * @param ships  the ships
   */
  public AiPlayer(SingleBoard boards, List<List<Coord>> board, List<Ship> ships) {
    this.boards = boards;
    this.board = board;
    this.ships = ships;
  }

  /**
   * Get the player's name.
   *
   * @return the player's name
   */
  @Override
  public String name() {
    return "AlisRyan";
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
    for (Map.Entry<ShipType, Integer> s : specifications.entrySet()) {
      int i = s.getValue();
      while (i > 0) {
        ships.add(boards.makeShip(s.getKey().getSize()));
        i--;
      }
    }
    shooter.setSize(height, width);
    return ships;
  }

  /**
   * Returns this player's shots on the opponent's board. The number of shots returned should
   * equal the number of ships on this player's board that have not sunk.
   *
   * @return the locations of shots on the opponent's board
   */
  @Override
  public List<Coord> takeShots() {
    int tot = ships.size();
    for (Ship ship : ships) {
      if (ship.isSunk()) {
        tot--;
      }
    }
    return shooter.shoot(tot);
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
    shooter.updateHits(shotsThatHitOpponentShips);
    boolean won = true;
    for (Ship s : ships) {
      if (!s.isSunk()) {
        won = false;
      }
    }
    if (won) {
      endGame(GameResult.WON, "You sunk all the opponent's ships!");
    }
  }

  /**
   * Notifies the player that the game is over.
   * Win, lose, and draw should all be supported
   *
   * @param result if the player has won, lost, or forced a draw
   * @param reason the reason for the game ending
   */
  @Override
  public void endGame(GameResult result, String reason) {
    boards.gameOver(result, reason);
  }
}
package cs3500.pa04.controller;

import cs3500.pa04.model.Coord;
import cs3500.pa04.model.GameResult;
import cs3500.pa04.model.Player;
import cs3500.pa04.model.Ship;
import cs3500.pa04.model.ShipType;
import cs3500.pa04.model.Status;
import cs3500.pa04.view.View;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Stores and communicates board information for a player
 */
public class SingleBoard {
  private Random rand = new Random();
  private List<List<Coord>> board = new ArrayList<>();
  private Player player;
  private View view;

  /**
   * Sets the player and the player's board
   *
   * @param player the player of this singleboard
   * @param board  the coords of the player's board
   */
  public void setPlayerBoard(Player player, List<List<Coord>> board) {
    this.player = player;
    this.board = board;
  }

  /**
   * Sets this view object
   *
   * @param view the view object for the game
   */
  public void setView(View view) {
    this.view = view;
  }

  /**
   * Seeds the random object for testing
   */
  public void setRandom() {
    rand = new Random(4);
  }

  /**
   * Sets up the boards with their appropriate sizes
   *
   * @param height height of the board
   * @param width  width of the board
   */
  public void setUpBoards(int height, int width) {
    for (int i = 0; i < height; i++) {
      board.add(new ArrayList<>());
      for (int j = 0; j < width; j++) {
        board.get(i).add(new Coord(i, j));
      }
    }
  }

  /**
   * Makes a ship of the given size on the board of the given player type
   *
   * @param size the size of the ship being made
   * @return the created ship
   */
  public Ship makeShip(int size) {
    ArrayList<Coord> temp = new ArrayList<>();
    boolean vertical = rand.nextBoolean();
    int row = rand.nextInt(board.size());
    int col = rand.nextInt(board.get(0).size());
    if (vertical && row + size > board.size()) {
      return makeShip(size);
    } else if (!vertical && col + size > board.get(0).size()) {
      return makeShip(size);
    } else if (vertical) {
      for (int i = row; i < row + size; i++) {
        temp.add(board.get(i).get(col));
        if (board.get(i).get(col).getStatus().equals(Status.OCCUPIED)) {
          return makeShip(size);
        }
      }
    } else {
      for (int i = col; i < col + size; i++) {
        temp.add(board.get(row).get(i));
        if (board.get(row).get(i).getStatus().equals(Status.OCCUPIED)) {
          return makeShip(size);
        }
      }
    }
    return new Ship(temp);
  }

  /**
   * Updates the board of the given player type with the opponent's shots
   *
   * @param shots the shots on the board
   */
  public void updateBoards(List<Coord> shots) {
    for (List<Coord> coords : board) {
      for (Coord coord : coords) {
        for (Coord shot : shots) {
          if (coord.atPosn(shot) && coord.getStatus().equals(Status.OCCUPIED)) {
            coord.updateStatus(Status.HIT);
          } else if (coord.atPosn(shot) && coord.getStatus().equals(Status.EMPTY)) {
            coord.updateStatus(Status.MISSED);
          }
        }
      }
    }
  }

  /**
   * Sets up each board with the given specifications
   *
   * @param specs the specifications to set the boards up with
   */
  public void setup(Map<ShipType, Integer> specs) {
    player.setup(board.size(), board.get(0).size(), specs);
  }

  /**
   * Sends the board of the given playertype to the view to be printed to the player
   */
  public void sendBoard(PlayerType type) {
    boolean visible = !type.equals(PlayerType.AI);
    List<List<String>> display = new ArrayList<>();
    for (int i = 0; i < board.size(); i++) {
      display.add(new ArrayList<>());
      for (int j = 0; j < board.get(i).size(); j++) {
        if (visible || !board.get(i).get(j).getStatus().equals(Status.OCCUPIED)) {
          display.get(i).add(board.get(i).get(j).getStatus().getLetter());
        } else {
          display.get(i).add(Status.EMPTY.getLetter());
        }
      }
    }
    view.displayBoard(type, display);
  }


  /**
   * Sends the game over to the view
   *
   * @param reason the reason for the game to end
   */
  public void gameOver(GameResult result, String reason) {
    view.getController().gameOver(result, reason);
  }
}

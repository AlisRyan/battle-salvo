package cs3500.pa04.controller;

import cs3500.pa04.model.Coord;
import cs3500.pa04.model.GameResult;
import cs3500.pa04.model.Player;
import cs3500.pa04.model.ShipType;
import cs3500.pa04.view.View;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * The main controller for the game
 */
public class Controller {
  private int height;
  private int width;
  private GameResult status = GameResult.INPROGRESS;
  private final Player p1;
  private final Player p2;
  private final SingleBoard p1Board;
  private final SingleBoard p2Board;
  private final View view;

  /**
   *
   * @param p1 first player (manual)
   * @param p2 second player (ai)
   * @param p1Board SingleBoard of the manual player
   * @param p2Board SingleBoard of the ai player
   * @param view the view object for this game
   */
  public Controller(Player p1, Player p2, SingleBoard p1Board, SingleBoard p2Board, View view) {
    this.p1 = p1;
    this.p2 = p2;
    this.p1Board = p1Board;
    this.p2Board = p2Board;
    this.view = view;
    view.setController(this);
    p1Board.setView(view);
    p2Board.setView(view);
  }

  /**
   * Sets the size of the boards with user inputs and verifies the inputs
   */
  public void setSize() {
    try {
      Scanner input = new Scanner(System.in);
      this.height = input.nextInt();
      this.width = input.nextInt();
      if (height > 15 || height < 6 || width > 15 || width < 6) {
        view.welcomeFailed();
      } else {
        p1Board.setUpBoards(height, width);
        p2Board.setUpBoards(height, width);
        view.fleetSpecs();
      }
    } catch (NoSuchElementException e) {
      System.err.println("Need arguments to proceed!");
    }
  }

  /**
   * Calls the methods to progress the game
   */
  private void caller() {
    if (this.status.equals(GameResult.INPROGRESS)) {
      List<Coord> p1shots = p1.takeShots();
      List<Coord> p2shots = p2.takeShots();
      p2Board.updateBoards(p1shots);
      p1Board.updateBoards(p2shots);
      p2Board.sendBoard(PlayerType.AI);
      p1Board.sendBoard(PlayerType.USER);
      p2.successfulHits(new ArrayList<>());
      p1.successfulHits(new ArrayList<>());
      caller();
    }
  }

  /**
   * Sets the fleet specs
   */
  public void setFleetSpecs() {
    try {
      Scanner input = new Scanner(System.in);
      int carrier = input.nextInt();
      int battleship = input.nextInt();
      int destroyer = input.nextInt();
      int submarine = input.nextInt();
      HashMap<ShipType, Integer> specs = new HashMap<>();
      specs.put(ShipType.CARRIER, carrier);
      specs.put(ShipType.BATTLESHIP, battleship);
      specs.put(ShipType.DESTROYER, destroyer);
      specs.put(ShipType.SUBMARINE, submarine);
      int tot = carrier + battleship + destroyer + submarine;
      if (carrier < 1 || battleship < 1 || destroyer < 1 || submarine < 1
          || tot > height || tot > width) {
        view.fleetSpecsFailed();
      } else {
        p1Board.setup(specs);
        p2Board.setup(specs);
        caller();
      }
    } catch (NoSuchElementException e) {
      System.err.println("Need arguments to proceed!");
    }
  }

  /**
   * Gets the proper number of shots from the user
   *
   * @param num the number of shots to obtain
   * @return returns the list of shots obtained from the user
   */
  public static List<Coord> getShots(int num) {
    try {
      List<Coord> coords = new ArrayList<>();
      View.getShots(num);
      Scanner input = new Scanner(System.in);
      while (num > 0) {
        coords.add(new Coord(input.nextInt(), input.nextInt()));
        num--;
      }
      return coords;
    } catch (NoSuchElementException e) {
      throw (e);
    }
  }

  /**
   * Reports to the player about the game ending
   *
   * @param result the result of the game end
   * @param reason the message about the game end to be printed to the user
   */
  public void gameOver(GameResult result, String reason) {
    if (!this.status.equals(GameResult.INPROGRESS)) {
      this.status = GameResult.DRAW;
      view.gameEnd("You and your opponent drew!");
    } else {
      this.status = result;
      view.gameEnd(reason);
    }
  }
}
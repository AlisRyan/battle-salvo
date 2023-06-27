package cs3500.pa04.view;

import cs3500.pa04.controller.Controller;
import cs3500.pa04.controller.PlayerType;
import java.util.List;

/**
 * The main view of the game
 */
public class View {
  private Controller controller;

  /**
   *
   * @param controller sets this controller
   */
  public void setController(Controller controller) {
    this.controller = controller;
  }

  /**
   *
   * @return returns the controller
   */
  public Controller getController() {
    return controller;
  }

  /**
   * Prints the welcomes message to the user
   */
  public void welcomeUser() {
    System.out.println("""
        Hello! Welcome to the OOD BattleSalvo Game!
        Please enter a valid height and width below:\s
        ------------------------------------------------------""");
    controller.setSize();
  }

  /**
   * Prints the message for the welcome failing to the user
   */
  public void welcomeFailed() {
    System.out.println("""
        Uh Oh! You've entered invalid dimensions. Please remember that the height and width
        of the game must be in the range (6, 15), inclusive. Try again!
        ------------------------------------------------------""");
    controller.setSize();
  }

  /**
   * Prompts the user to enter fleet specs
   */
  public void fleetSpecs() {
    System.out.println(
        "Please enter your fleet in the order [Carrier, Battleship, Destroyer, Submarine].\n"
            + "--------------------------------------------------------------------------------");
    controller.setFleetSpecs();
  }

  /**
   * Prints the message for the fleet specs failing
   */
  public void fleetSpecsFailed() {
    System.out.println("""
        Uh Oh! You've entered invalid fleet sizes.
        Please enter your fleet in the order [Carrier, Battleship, Destroyer, Submarine].
        --------------------------------------------------------------------------------""");
    controller.setFleetSpecs();
  }

  /**
   * Prompts the user to enter the proper number of shots
   *
   * @param num the number of shots the user should enter
   */
  public static void getShots(int num) {
    System.out.println("Please enter " + num + " Shots:\n"
        + "------------------------------------------------------------------");
  }

  /**
   * Displays the board to the user
   *
   * @param p the type of board being displayed
   * @param board the board to be displayed
   */
  public void displayBoard(PlayerType p, List<List<String>> board) {
    if (p.equals(PlayerType.AI)) {
      System.out.println("Opponent Board Data:");
    } else {
      System.out.println("Your Board:");
    }
    for (List<String> strings : board) {
      for (String string : strings) {
        System.out.print(string + " ");
      }
      System.out.println();
    }
  }

  /**
   *
   * @param msg the message of the game end
   */
  public void gameEnd(String msg) {
    System.out.println(msg);
  }
}
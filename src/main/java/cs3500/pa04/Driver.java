package cs3500.pa04;

import cs3500.pa04.controller.Controller;
import cs3500.pa04.controller.ProxyController;
import cs3500.pa04.controller.SingleBoard;
import cs3500.pa04.model.AiPlayer;
import cs3500.pa04.model.Coord;
import cs3500.pa04.model.ManualPlayer;
import cs3500.pa04.model.Player;
import cs3500.pa04.view.View;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * This is the main driver of this project.
 */
public class Driver {
  /**
   * Project entry point
   *
   * @param args - no command line args required
   */


  public static void main(String[] args) {
    SingleBoard aiBoards = new SingleBoard();
    List<List<Coord>> aiBoard = new ArrayList<>();
    Player ai = new AiPlayer(aiBoards, aiBoard);
    aiBoards.setPlayerBoard(ai, aiBoard);
    if (args.length == 2) {
      try {
        Socket socket = new Socket(args[0], Integer.parseInt(args[1]));
        ProxyController proxy = new ProxyController(socket, ai, aiBoards);
        proxy.run();
      } catch (IOException e) {
        System.out.println("Please provide valid socket arguments");
      }
    } else if (args.length == 0) {
      SingleBoard manualBoards = new SingleBoard();
      List<List<Coord>> manualBoard = new ArrayList<>();
      Player manual = new ManualPlayer(manualBoards, manualBoard);
      manualBoards.setPlayerBoard(manual, manualBoard);
      View view = new View();
      Controller controller = new Controller(manual, ai, manualBoards, aiBoards, view);
      view.welcomeUser();
    } else {
      System.out.println("Please provide the appropriate number of arguments");
    }
  }
}
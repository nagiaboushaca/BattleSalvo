package cs3500.pa04;

import cs3500.pa04.controller.BattleSalvoController;
import cs3500.pa04.controller.Controller;
import cs3500.pa04.controller.ProxyController;
import cs3500.pa04.model.AiPlayer;
import cs3500.pa04.model.Board;
import cs3500.pa04.model.BoardImpl;
import cs3500.pa04.model.ManualPlayer;
import cs3500.pa04.model.Player;
import cs3500.pa04.view.CommandLineView;
import cs3500.pa04.view.View;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * This is the main driver of this project.
 */
public class Driver {

  /**
   * running client using host and port
   *
   * @param host 0.0.0.0
   *
   * @param port port number
   */
  private static void runClient(String host, int port) {
    Socket server = null;
    Board aiBoard = new BoardImpl();
    Board userBoardInfo = new BoardImpl();
    Player aiPlayer = new AiPlayer(aiBoard, userBoardInfo);
    try {
      server = new Socket(host, port);
    } catch (IOException e) {
      System.out.println("Invalid host or port number");
      System.out.println(e.getMessage());
    }

    Controller proxyController =
        new ProxyController(aiPlayer, aiBoard, userBoardInfo, server);
    proxyController.run();
  }


  /**
   * Project entry point
   *
   * @param args - no command line args required
   */
  public static void main(String[] args) {

    if (args.length == 0) {
      Board userBoard = new BoardImpl();
      Board opponentBoardInfo = new BoardImpl();
      Board aiBoard = new BoardImpl();
      Board userBoardInfo = new BoardImpl();

      Player user = new ManualPlayer(userBoard, opponentBoardInfo);
      Player ai = new AiPlayer(aiBoard, userBoardInfo);

      View view = new CommandLineView();

      Controller controller = new BattleSalvoController(
          user, ai, userBoard, aiBoard, view, new InputStreamReader(System.in), opponentBoardInfo);

      controller.run();
    } else if (args.length == 2) {
      Driver.runClient(args[0], Integer.parseInt(args[1]));
    } else {
      throw new IllegalArgumentException("You have entered too many or too little arguments.");
    }
  }
}
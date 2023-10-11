package cs3500.pa04.view;

import cs3500.pa04.controller.DataValidator;
import cs3500.pa04.model.AbstractPlayer;
import cs3500.pa04.model.Board;
import cs3500.pa04.model.Coord;
import cs3500.pa04.model.GameResult;
import cs3500.pa04.model.ManualPlayer;
import cs3500.pa04.model.Player;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * class that implements the view as a command line application
 */
public class CommandLineView implements View {

  /**
   * empty constructor
   */
  public CommandLineView() {}

  /**
   * displays the given message
   *
   * @param message message to display
   */
  public void displayMessage(String message) {
    System.out.println(message);
  }


  /**
   * displays the given board
   *
   * @param board the board to display
   */
  @Override
  public void displayBoard(Coord[][] board) {
    StringBuilder output = new StringBuilder();

    for (int i = 0; i < board.length; i++) {
      for (Coord c : board[i]) {
        output.append(c.getRepresentation());
        output.append("\t");
      }
      output.append("\n");
    }
    System.out.println(output);
  }

  /**
   * displays endGame screen
   *
   * @param result the game result enumeration
   */
  @Override
  public void displayEndGame(GameResult result) {
    switch (result) {
      case PlayerWin:
        System.out.println("You win!!");
        break;
      case PlayerLose:
        System.out.println("You lose!!");
        break;
      case Draw:
        System.out.println("It's a draw!!");
        break;
      default:
        break;
    }
  }
}

package cs3500.pa04.view;

import cs3500.pa04.model.Coord;
import cs3500.pa04.model.GameResult;
import cs3500.pa04.model.ManualPlayer;
import cs3500.pa04.model.Player;
import java.util.ArrayList;

/**
 * interface for the view
 */
public interface View {

  /**
   * displays the given message
   *
   * @param message message to display
   */
  public void displayMessage(String message);

  /**
   * displays the given player's board
   *
   * @param board the board to display
   */
  public void displayBoard(Coord[][] board);

  /**
   * displays the end game result
   *
   * @param result the result of the game
   */
  public void displayEndGame(GameResult result);
}

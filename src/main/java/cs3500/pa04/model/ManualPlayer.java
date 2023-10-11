package cs3500.pa04.model;

import cs3500.pa04.controller.BattleSalvoController;
import cs3500.pa04.controller.DataValidator;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * represents the user playing manually
 */
public class ManualPlayer extends AbstractPlayer {

  /**
   * constructor
   *
   * @param playerBoard the player's board
   *
   * @param opponentBoardInfo the opponent's known board
   */
  public ManualPlayer(Board playerBoard, Board opponentBoardInfo) {
    super(playerBoard, opponentBoardInfo);
  }

  /**
   * Returns this player's shots on the opponent's board. The number of shots returned should
   * equal the number of ships on this player's board that have not sunk.
   *
   * @return the locations of shots on the opponent's board
   */
  @Override
  public List<Coord> takeShots() {
    this.shotsTaken.clear();
    List<Coord> shotInputs = BattleSalvoController.passShotInputs();
    this.shotsTaken.addAll(shotInputs);
    if (DataValidator.validShots(opponentBoard, shotsTaken)) {
      for (Coord c : this.shotsTaken) {
        this.opponentBoard.getBoard()[c.getX()][c.getY()].setAlreadyShot();
      }
    }

    return shotsTaken;
  }
}

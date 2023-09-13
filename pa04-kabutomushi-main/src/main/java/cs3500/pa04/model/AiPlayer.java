package cs3500.pa04.model;

import cs3500.pa04.controller.DataValidator;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * represents the AI player the user plays against
 */
public class AiPlayer extends AbstractPlayer {

  /**
   * constructor taking in the ai board and the user's board with known info
   *
   * @param playerBoard this ai's board
   *
   * @param opponentBoardInfo the opponent's board info
   */
  public AiPlayer(Board playerBoard, Board opponentBoardInfo) {
    super(playerBoard, opponentBoardInfo);
  }

  /**
   * Returns this ai player's shots on the opponent's board. The number of shots returned should
   * equal the number of ships on this player's board that have not sunk.
   *
   * @return the locations of shots on the opponent's board
   */
  @Override
  public List<Coord> takeShots() {
    this.shotsTaken.clear();
    List<Coord> yetToBeShot = this.opponentBoard.getYetToBeShotAt();

    if (yetToBeShot.size() <= this.ships.size()) {
      return yetToBeShot;
    }

    ArrayList<Coord> curShots = new ArrayList<>();
    for (int i = 0; i < this.ships.size(); i++) {
      Coord randShot = yetToBeShot.get(this.rand.nextInt(yetToBeShot.size()));
      curShots.add(randShot);
      System.out.println(randShot.getX() + " " + randShot.getY());
      yetToBeShot.remove(randShot);
      shotsTaken.add(randShot);
      if (DataValidator.validShots(opponentBoard, curShots)) {
        for (Coord curShot : curShots) {
          this.opponentBoard.getBoard()[curShot.getX()][curShot.getY()].setAlreadyShot();
        }
      }
    }


    return shotsTaken;
  }
}

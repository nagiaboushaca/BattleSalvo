package cs3500.pa04.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * abstract player class to abstract some methods
 */
public abstract class AbstractPlayer implements Player {
  protected Board playerBoard;
  protected Board opponentBoard;
  protected List<Ship> ships;
  protected Random rand;
  protected List<Coord> shotsTaken;

  /**
   * constructor for the player, initializes all lists
   */
  public AbstractPlayer(Board playerBoard, Board opponentBoardInfo) {
    this.rand = new Random();
    this.ships = new ArrayList<>();
    this.playerBoard = playerBoard;
    this.opponentBoard = opponentBoardInfo;
    this.shotsTaken = new ArrayList<>();
  }


  /**
   * returns the name of this player
   *
   * @return the name of the player
   */
  @Override
  public String name() {
    return "kabutomushi";
  }

  /**
   * sets up the board for the player
   *
   * @param height the height of the board, range: [6, 15] inclusive
   *
   * @param width the width of the board, range: [6, 15] inclusive
   *
   * @param specifications a map of ship type to the number of occurrences each ship should
   *                       appear on the board
   *
   * @return the list of ships this player has
   */
  @Override
  public List<Ship> setup(int height, int width, Map<ShipType, Integer> specifications) {
    this.ships = this.playerBoard.setup(height, width, specifications);
    this.opponentBoard.createBoard(height, width);

    return this.ships;
  }


  /**
   * Returns this player's shots on the opponent's board. The number of shots returned should
   * equal the number of ships on this player's board that have not sunk.
   *
   * @return the locations of shots on the opponent's board
   */
  @Override
  public abstract List<Coord> takeShots();

  /**
   * Given the list of shots the opponent has fired on this player's board, report which
   * shots hit a ship on this player's board.
   *
   * @param opponentShotsOnBoard the opponent's shots on this player's board
   *
   * @return a filtered list of the given shots that contain all
   *         locations of shots that hit a ship on this board
   */
  @Override
  public List<Coord> reportDamage(List<Coord> opponentShotsOnBoard) {
    List<Coord> successfulHits = new ArrayList<>();
    for (Coord c : opponentShotsOnBoard) {
      for (Ship s : this.ships) {
        if (s.successfulHit(c)) {
          successfulHits.add(c);
          Coord destroyedCoord = this.playerBoard.getBoard()[c.getX()][c.getY()];
          destroyedCoord.setAlreadyShot();
          destroyedCoord.setRepresentation("X");
          s.updateDamages(destroyedCoord);
          if (s.isDestroyed()) {
            this.ships.remove(s);
            this.playerBoard.removeShip(s);
          }
          break;
        } else {
          Coord missedCoord = this.playerBoard.getBoard()[c.getX()][c.getY()];
          missedCoord.setAlreadyShot();
          missedCoord.setRepresentation("-");
        }
      }
    }
    return successfulHits;
  }

  /**
   * reports the successful shots to the user
   *
   * @param shotsThatHitOpponentShips the list of shots that successfully hit the opponent's ships
   */
  @Override
  public void successfulHits(List<Coord> shotsThatHitOpponentShips) {
    for (Coord shot : shotsTaken) {
      if (shotsThatHitOpponentShips.contains(shot)) {
        this.opponentBoard.getBoard()[shot.getX()][shot.getY()].setRepresentation("H");
      } else {
        this.opponentBoard.getBoard()[shot.getX()][shot.getY()].setRepresentation("M");
      }
    }
  }

  /**
   * Notifies the player that the game is over.
   * Win, lose, and draw should all be supported
   *
   * @param result if the player has won, lost, or forced a draw
   *
   * @param reason the reason for the game ending
   */
  @Override
  public void endGame(GameResult result, String reason) {
    switch (result) {
      case PlayerWin:
        System.out.println("You win!");
        break;
      case PlayerLose:
        System.out.println("You lose...");
        break;
      case Draw:
        System.out.println("It's a tie!");
        break;
      default:
        break;
    }

    System.out.println(reason);
  }
}

package cs3500.pa04.model;

import java.util.List;
import java.util.Map;

/**
 * interface to represent the game board
 */
public interface Board {

  /**
   * getter for the game board
   *
   * @return the game board as a 2D array of Coords
   */
  public Coord[][] getBoard();

  /**
   * creates empty board with given height and width
   *
   * @param height height of the board
   *
   * @param width width of the board
   */
  public void createBoard(int height, int width);

  /**
   * sets up the board with the given height and width and ship specifications
   *
   * @param height height of the board
   *
   * @param width width of the board
   *
   * @param specifications the specifications for the ships for each ship type
   *
   * @return the ships after setup
   */
  public List<Ship> setup(int height, int width, Map<ShipType, Integer> specifications);

  /**
   * getter for the number of allowed shots
   *
   * @return the number of allowed shots
   */
  public int getNumAllowedShots();

  /**
   * getter for the coords yet to be shot at
   *
   * @return the coords yet to be shot at
   */
  public List<Coord> getYetToBeShotAt();

  /**
   * getter for the list of ships
   *
   * @return the list of ships
   */
  public List<Ship> getShips();

  public void removeShip(Ship ship);
}

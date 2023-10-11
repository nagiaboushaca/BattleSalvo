package cs3500.pa04.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * board implementation class
 */
public class BoardImpl implements Board {
  private Coord[][] board;
  private List<Ship> ships;
  private List<Coord> openCoords;
  private Random rand;
  private List<Coord> yetToBeShot;

  /**
   * constructor to initialize all fields
   */
  public BoardImpl() {
    this.ships = new ArrayList<>();
    this.rand = new Random();
    this.openCoords = new ArrayList<>();
    this.yetToBeShot = new ArrayList<>();
  }

  /**
   * creates the player and opponent boards, with the given height and width
   *
   * @param height height parameter
   *
   * @param width  width parameter
   */
  public void createBoard(int height, int width) {
    this.board = new Coord[height][width];
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        this.board[i][j] = new Coord(i, j);
        this.openCoords.add(this.board[i][j]);
      }
    }
    this.yetToBeShot.addAll(this.openCoords);
  }

  /**
   * sets up the board with the given information
   *
   * @param height height of the board
   *
   * @param width width of the board
   *
   * @param specifications the specifications for the ships for each ship type
   *
   * @return the list of ships on this board
   */
  public List<Ship> setup(int height, int width, Map<ShipType, Integer> specifications) {
    this.createBoard(height, width);
    this.placeShips(specifications);
    return this.ships;
  }

  /**
   * getter for the board
   *
   * @return the game board
   */
  @Override
  public Coord[][] getBoard() {
    return this.board;
  }

  /**
   * getter for the number of allowed shots
   *
   * @return the number of allowed shots
   */
  @Override
  public int getNumAllowedShots() {
    return this.ships.size();
  }

  /**
   * getter for the ships
   *
   * @return the ships on this board
   */
  @Override
  public List<Ship> getShips() {
    return this.ships;
  }

  /**
   * removes the given ship from this list of ships after destruction
   *
   * @param ship the ship to remove
   */
  @Override
  public void removeShip(Ship ship) {
    this.ships.remove(ship);
  }

  /**
   * getter for the coords yet to be shot at
   *
   * @return the coords yet to be shot at
   */
  public List<Coord> getYetToBeShotAt() {
    return this.yetToBeShot;
  }

  /**
   * places the ships onto the player board
   *
   * @param specifications specifications for how many ships of each type
   */
  private void placeShips(Map<ShipType, Integer> specifications) {
    int numCarriers = specifications.get(ShipType.Carrier);
    for (int i = 0; i < numCarriers; i++) {
      placeOneShip(ShipType.Carrier, new Ship(ShipType.Carrier));
    }

    int numBattleships = specifications.get(ShipType.Battleship);
    for (int i = 0; i < numBattleships; i++) {
      placeOneShip(ShipType.Battleship, new Ship(ShipType.Battleship));
    }

    int numDestroyers = specifications.get(ShipType.Destroyer);
    for (int i = 0; i < numDestroyers; i++) {
      placeOneShip(ShipType.Destroyer, new Ship(ShipType.Destroyer));
    }

    int numSubmarines = specifications.get(ShipType.Submarine);
    for (int i = 0; i < numSubmarines; i++) {
      placeOneShip(ShipType.Submarine, new Ship(ShipType.Submarine));
    }
  }

  /**
   * checks if the given coords can be placed on board
   *
   * @param coords the coordinates to check
   *
   * @return true if the coords can be placed on board
   */
  private boolean canPlace(ArrayList<Coord> coords) {
    for (Coord c : coords) {
      if (!openCoords.contains(c)) {
        return false;
      }
    }
    return true;
  }

  /**
   * places one given ship with the given shiptype
   *
   * @param shipType the shiptype of the ship
   *
   * @param ship the ship to place
   */
  public void placeOneShip(ShipType shipType, Ship ship) {
    boolean isHorizontal = rand.nextBoolean();
    Coord startCoordinate = null;
    if (isHorizontal) {
      startCoordinate = board[rand.nextInt(board.length)][0];
    } else {
      startCoordinate = board[0][rand.nextInt(board[0].length)];
    }

    int x = startCoordinate.getX();
    int y = startCoordinate.getY();
    ArrayList<Coord> currentShipCoords = new ArrayList<>();

    // Set ship's orientation randomly

    if (isHorizontal) {
      for (int i = 0; i < shipType.getSize(); i++) {
        currentShipCoords.add(board[x][y + i]);
        ship.setDirection(Direction.HORIZONTAL);
      }
    } else {
      for (int j = 0; j < shipType.getSize(); j++) {
        currentShipCoords.add(board[x + j][y]);
        ship.setDirection(Direction.VERTICAL);
      }
    }

    if (canPlace(currentShipCoords)) {
      for (Coord c : currentShipCoords) {
        c.setRepresentation(shipType.getSymbol());
        this.openCoords.remove(c);
      }
      ship.setAliveCoords(currentShipCoords);
      this.ships.add(ship);
    } else {
      placeOneShip(shipType, ship);
    }
  }
}

package cs3500.pa04.model;

import java.util.ArrayList;
import java.util.List;

/**
 * class that represents the ships
 */
public class Ship {
  private ArrayList<Coord> aliveCoords;
  private boolean isDestroyed;
  private ShipType shipType;
  private int length;
  private Direction direction;

  /**
   * constructor for ship, when constructed it is not destroyed, and
   * all of its coordinates are not destroyed
   *
   * @param shipType the type of ship this ship is
   *
   */
  public Ship(ShipType shipType) {
    this.shipType = shipType;
    this.length = this.shipType.getSize();
    this.aliveCoords = new ArrayList<>();
    this.isDestroyed = false;
  }

  /**
   * getter for isDestroyed boolean
   *
   * @return if the ship is destroyed or not
   */
  public boolean isDestroyed() {
    return isDestroyed;
  }

  /**
   * updates any damages to this ship given a shot
   *
   * @param shot the shot from the opponent
   */
  public void updateDamages(Coord shot) {
    if (this.aliveCoords.contains(shot)) {
      this.aliveCoords.remove(shot);
    }


    if (this.aliveCoords.size() == 0) {
      this.isDestroyed = true;
    }
  }

  /**
   * returns true if the shot did successfully hit this ship
   *
   * @param shot the shot from the opponent
   *
   * @return true if the shot hit this ship
   */
  public boolean successfulHit(Coord shot) {
    for (Coord c : this.aliveCoords) {
      if (c.getX() == shot.getX() && c.getY() == shot.getY()) {
        return true;
      }
    }
    return false;
  }

  /**
   * sets the coordinates of the ship to the given coords
   *
   * @param aliveCoords the coords of the ship
   */
  public void setAliveCoords(ArrayList<Coord> aliveCoords) {
    this.aliveCoords = aliveCoords;
  }

  /**
   * setter for the direction of this ship
   *
   * @param direction horizontal or vertical
   */
  public void setDirection(Direction direction) {
    this.direction = direction;
  }

  /**
   * getter method for direction enum
   *
   * @return the direction of this ship
   */
  public Direction getDirection() {
    return direction;
  }

  /**
   * getter for coordinates of the ship
   *
   * @return the coordinates of the ship
   */
  public ArrayList<Coord> getAliveCoords() {
    return aliveCoords;
  }

  /**
   * getter for length of the ship
   *
   * @return the length of the ship
   */
  public int getLength() {
    return length;
  }

  /**
   * gets the coordinate for the json data
   *
   * @return the uppermost/leftmost coordinate
   */
  public Coord getCoord() {
    return new Coord(this.aliveCoords.get(0).getY(), this.aliveCoords.get(0).getX());
  }
}

package cs3500.pa04.model;

/**
 * class to represent a coordinate on the BattleSalvo board
 */
public class Coord {
  private int xcoord;
  private int ycoord;
  private boolean alreadyShot;
  private String representation;
  public static final String ANSI_RESET = "\u001B[0m";
  public static final String ANSI_RED = "\u001B[31m";
  public static final String ANSI_GREEN = "\u001B[32m";
  public static final String ANSI_BLUE = "\u001B[34m";
  public static final String ANSI_PURPLE = "\u001B[35m";
  public static final String ANSI_YELLOW = "\u001B[33m";
  public static final String ANSI_CYAN = "\u001B[36m";

  /**
   * constructor for a coordinate position on the BattleSalvo board,
   * it also keeps tracks of if this coordinate was already shot at or not,
   * and the visual representation for the coordinate
   *
   * @param x the horizontal position on the board starting at 1
   *
   * @param y the vertical position on the board starting at 1
   */
  public Coord(int x, int y) {
    this.xcoord = x;
    this.ycoord = y;
    this.alreadyShot = false;
    this.representation = "0";
  }

  /**
   * setter method to set this coordinate to shot
   */
  public void setAlreadyShot() {
    this.alreadyShot = true;
  }

  /**
   * setter method to set the string representation for this coordinate
   */
  public void setRepresentation(String s) {
    switch (s) {
      case "C":
        this.representation = ANSI_RED + s + ANSI_RESET;
        break;
      case "B":
        this.representation = ANSI_BLUE + s + ANSI_RESET;
        break;
      case "D":
        this.representation = ANSI_PURPLE + s + ANSI_RESET;
        break;
      case "S":
        this.representation = ANSI_GREEN + s + ANSI_RESET;
        break;
      case "H", "X":
        this.representation = ANSI_CYAN + s + ANSI_RESET;
        break;
      case "M", "-":
        this.representation = ANSI_YELLOW + s + ANSI_RESET;
        break;
      default:
        break;
    }
  }

  /**
   * getter for the string representation of this coordinate
   *
   * @return the string representation of this coordinate
   */
  public String getRepresentation() {
    return this.representation;
  }

  /**
   * gets the x coord
   *
   * @return the x coordinate
   */
  public int getX() {
    return xcoord;
  }

  /**
   * gets the y coord
   *
   * @return the y coordinate
   */
  public int getY() {
    return ycoord;
  }

  /**
   * getter for already shot
   *
   * @return boolean for if the coordinate is already shot
   */
  public boolean isAlreadyShot() {
    return alreadyShot;
  }
}

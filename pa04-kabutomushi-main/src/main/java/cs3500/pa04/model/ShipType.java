package cs3500.pa04.model;

/**
 * enumeration for the different type of ships
 */
public enum ShipType {
  Carrier(6, "C"),
  Battleship(5, "B"),
  Destroyer(4, "D"),
  Submarine(3, "S");

  private int size;
  private String symbol;

  /**
   * constructs the shiptype with its size and string symbol
   *
   * @param size size of the ship
   *
   * @param symbol string symbol denoted on the board
   */
  ShipType(int size, String symbol) {
    this.size = size;
    this.symbol = symbol;
  }

  /**
   * gets the size of this shipType
   *
   * @return the size of this shipType
   */
  public int getSize() {
    return size;
  }

  /**
   * gets the string symbol of this shipType
   *
   * @return the string symbol of this shipType
   */
  public String getSymbol() {
    return symbol;
  }
}

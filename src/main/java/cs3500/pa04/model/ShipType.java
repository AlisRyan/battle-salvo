package cs3500.pa04.model;

/**
 * The different types of a ship
 */
public enum ShipType {
  CARRIER(6),
  BATTLESHIP(5),
  DESTROYER(4),
  SUBMARINE(3);
  private final int size;

  /**
   * The shiptype of given size
   *
   * @param s the size of the ship
   */
  ShipType(int s) {
    size = s;
  }

  /**
   * Returns the size of the ship
   *
   * @return returns the size of the ship
   */
  public int getSize() {
    return size;
  }

  /**
   * Converts this ship type to a string
   *
   * @return returns the string of the shiptype
   */
  @Override
  public String toString() {
    return switch (this) {
      case CARRIER -> "CARRIER";
      case BATTLESHIP -> "BATTLESHIP";
      case DESTROYER -> "DESTROYER";
      case SUBMARINE -> "SUBMARINE";
    };
  }

}

package cs3500.pa04.model;

import java.util.List;

/**
 * Represents a ship
 */
public class Ship {
  private final List<Coord> coords;

  /**
   * Creates a ship with the given coords
   *
   * @param coords the coords of the ship
   */
  public Ship(List<Coord> coords) {
    this.coords = coords;
    updateCoords();
  }

  /**
   * Updates the coords to be occupied
   */
  private void updateCoords() {
    for (Coord c : coords) {
      c.updateStatus(Status.OCCUPIED);
    }
  }

  /**
   * Has this ship sunk?
   *
   * @return returns whether this ship has sunk
   */
  public boolean isSunk() {
    for (Coord c : coords) {
      if (!c.getStatus().equals(Status.HIT)) {
        return false;
      }
    }
    return true;
  }

  /**
   *
   * @return returns the start of this ship
   */
  public Coord getStart() {
    return this.coords.get(0);
  }

  /**
   *
   * @return returns the length of this ship
   */
  public int length() {
    return this.coords.size();
  }

  /**
   *
   * @return returns this ship's direction
   */
  public String direction() {
    if (this.coords.get(0).getX() == this.coords.get(1).getX()) {
      return "VERTICAL";
    } else {
      return "HORIZONTAL";
    }
  }

  /**
   *
   * @return returns this ship's coordinates
   */
  public List<Coord> getCoords() {
    return this.coords;
  }
}

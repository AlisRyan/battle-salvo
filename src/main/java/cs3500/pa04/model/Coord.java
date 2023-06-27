package cs3500.pa04.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The coordinates class
 */
public class Coord {
  private final int xcoord;
  private final int ycoord;
  private Status stat;

  /**
   * Sets the coordinate with the given values
   *
   * @param x the x-coordinate
   * @param y the y-coordinate
   */
  public Coord(
      @JsonProperty("y") int y,
      @JsonProperty("x") int x) {
    this.ycoord = y;
    this.xcoord = x;
    stat = Status.EMPTY;
  }

  public int getX() {
    return this.xcoord;
  }

  public int getY() {
    return this.ycoord;
  }

  /**
   * Updates the coordinates status to the given status
   *
   * @param s the status to be updated to
   */
  public void updateStatus(Status s) {
    this.stat = s;
  }

  /**
   * Returns the status of this coordinates
   *
   * @return returns the status
   */
  public Status getStatus() {
    return stat;
  }

  /**
   * Returns whether this coordinate is at the same position as the given coordinate
   *
   * @param coord the coordinate to be compared
   * @return returns whether the coordinates are at the same x and y values
   */
  public boolean atPosn(Coord coord) {
    return this.xcoord == coord.xcoord && this.ycoord == coord.ycoord;
  }

}

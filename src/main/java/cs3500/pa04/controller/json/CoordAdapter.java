package cs3500.pa04.controller.json;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa04.model.Coord;

/**
 * CoordAdapter class to convert Coords to the appropriate json
 */
public class CoordAdapter {
  private final int xcoord;
  private final int ycoord;

  /**
   *
   * @param c the coord to be converted
   */
  public CoordAdapter(Coord c) {
    this.xcoord = c.getX();
    this.ycoord = c.getY();
  }

  /**
   *
   * @param y the coords y value
   * @param x the coords x value
   */
  @JsonCreator
  public CoordAdapter(
      @JsonProperty("x") int x,
      @JsonProperty("y") int y
  ) {
    this.xcoord = x;
    this.ycoord = y;
  }

  /**
   *
   * @return returns this coordAdapter as a coord
   */
  public Coord toCoord() {
    return new Coord(ycoord, xcoord);
  }

  /**
   *
   * @return returns the x value of this coord adapter
   */
  public int getX() {
    return xcoord;
  }

  /**
   *
   * @return returns the y value of this coord adapter
   */
  public int getY() {
    return ycoord;
  }
}

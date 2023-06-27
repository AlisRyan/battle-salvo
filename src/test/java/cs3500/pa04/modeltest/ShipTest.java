package cs3500.pa04.modeltest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import cs3500.pa04.model.Coord;
import cs3500.pa04.model.Ship;
import cs3500.pa04.model.Status;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for methods of the ship test
 */
public class ShipTest {
  private List<Coord> coords;
  private Coord first;
  private Coord second;
  private Coord third;
  private Ship ship;

  /**
   * Initializes the fields for testing
   */
  @BeforeEach
  public void initFields() {
    first = new Coord(2, 3);
    second = new Coord(2, 4);
    third = new Coord(2, 5);
    coords = new ArrayList<>(Arrays.asList(first, second, third));
    ship = new Ship(coords);
  }

  @Test
  public void checkUpdateCoords() {
    for (Coord c : coords) {
      assertEquals(c.getStatus(), Status.OCCUPIED);
    }
  }

  @Test
  public void checkIsSunk() {
    assertFalse(ship.isSunk());
    first.updateStatus(Status.HIT);
    assertFalse(ship.isSunk());
    second.updateStatus(Status.HIT);
    assertFalse(ship.isSunk());
    third.updateStatus(Status.HIT);
    assertTrue(ship.isSunk());
  }
}

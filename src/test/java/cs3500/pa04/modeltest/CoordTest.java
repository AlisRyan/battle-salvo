package cs3500.pa04.modeltest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import cs3500.pa04.model.Coord;
import cs3500.pa04.model.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for methods of the coord class
 */
public class CoordTest {
  private Coord coord;

  /**
   * Initializes the fields for testing
   */
  @BeforeEach
  public void initFields() {
    coord = new Coord(2, 6);
  }

  @Test
  public void checkGetStatus() {
    assertEquals(coord.getStatus(), Status.EMPTY);
    coord.updateStatus(Status.HIT);
    assertEquals(coord.getStatus(), Status.HIT);
    coord.updateStatus(Status.MISSED);
    assertEquals(coord.getStatus(), Status.MISSED);
    coord.updateStatus(Status.OCCUPIED);
    assertEquals(coord.getStatus(), Status.OCCUPIED);
  }

  @Test
  public void checkUpdateStatus() {
    coord.updateStatus(Status.HIT);
    assertEquals(coord.getStatus(), Status.HIT);
    coord.updateStatus(Status.MISSED);
    assertEquals(coord.getStatus(), Status.MISSED);
    coord.updateStatus(Status.OCCUPIED);
    assertEquals(coord.getStatus(), Status.OCCUPIED);
    coord.updateStatus(Status.EMPTY);
    assertEquals(coord.getStatus(), Status.EMPTY);
  }

  @Test
  public void checkAtPosn() {
    Coord b = new Coord(1, 7);
    Coord c = new Coord(2, 6);
    assertFalse(coord.atPosn(b));
    assertTrue(coord.atPosn(c));
  }

}

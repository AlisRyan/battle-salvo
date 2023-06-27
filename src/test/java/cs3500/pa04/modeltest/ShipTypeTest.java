package cs3500.pa04.modeltest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cs3500.pa04.model.ShipType;
import org.junit.jupiter.api.Test;

/**
 * Tests for methods of the ShipType enum
 */
public class ShipTypeTest {
  @Test
  public void checkGetSize() {
    assertEquals(ShipType.CARRIER.getSize(), 6);
    assertEquals(ShipType.BATTLESHIP.getSize(), 5);
    assertEquals(ShipType.DESTROYER.getSize(), 4);
    assertEquals(ShipType.SUBMARINE.getSize(), 3);
  }
}

package cs3500.pa04.modeltest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cs3500.pa04.model.Status;
import org.junit.jupiter.api.Test;

/**
 * Tests for methods of the Status enum
 */
public class StatusTest {
  @Test
  public void checkGetLetter() {
    assertEquals(Status.HIT.getLetter(), "H");
    assertEquals(Status.OCCUPIED.getLetter(), "S");
    assertEquals(Status.EMPTY.getLetter(), "0");
    assertEquals(Status.MISSED.getLetter(), "M");
  }

}

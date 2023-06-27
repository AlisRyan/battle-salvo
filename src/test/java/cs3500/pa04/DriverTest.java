package cs3500.pa04;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.Test;

/**
 * Tests the driver
 */
class DriverTest {

  /**
   * tests main
   */
  @Test
  public void driverTest() {
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));
    Driver.main(new String[0]);
    String expected = """
        Hello! Welcome to the OOD BattleSalvo Game!
        Please enter a valid height and width below:\s
        ------------------------------------------------------
        """;
    assertEquals(expected, outContent.toString());
  }

}
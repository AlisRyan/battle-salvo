package cs3500.pa04.controllertest.jsontest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import cs3500.pa04.controller.json.MethodName;
import org.junit.jupiter.api.Test;

/**
 * Tests the method name enum
 */
public class MethodNameTest {

  @Test
  public void checkWhichMethod() {
    assertEquals(MethodName.whichMethod("setup"), MethodName.SETUP);
    assertEquals(MethodName.whichMethod("take-shots"), MethodName.TAKESHOTS);
    assertEquals(MethodName.whichMethod("report-damage"), MethodName.REPORTDAMAGE);
    assertEquals(MethodName.whichMethod("successful-hits"), MethodName.SUCCESSFULHITS);
    assertEquals(MethodName.whichMethod("join"), MethodName.JOIN);
    assertEquals(MethodName.whichMethod("end-game"), MethodName.ENDGAME);
    assertThrows(IllegalStateException.class, () -> MethodName.whichMethod("iqfhas"),
        "Unexpected value: iqfhas");
  }

  @Test
  public void checkToString() {
    assertEquals(MethodName.SETUP.toString(), "setup");
    assertEquals(MethodName.TAKESHOTS.toString(), "take-shots");
    assertEquals(MethodName.REPORTDAMAGE.toString(), "report-damage");
    assertEquals(MethodName.SUCCESSFULHITS.toString(), "successful-hits");
    assertEquals(MethodName.JOIN.toString(), "join");
    assertEquals(MethodName.ENDGAME.toString(), "end-game");
  }
}

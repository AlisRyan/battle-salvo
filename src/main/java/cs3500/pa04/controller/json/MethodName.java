package cs3500.pa04.controller.json;

/**
 * The different methods that can be called
 */
public enum MethodName {
  SETUP,
  TAKESHOTS,
  REPORTDAMAGE,
  SUCCESSFULHITS,
  JOIN,
  ENDGAME;

  /**
   * @param method the string of the method
   * @return returns which method the given string corresponds to
   */
  public static MethodName whichMethod(String method) {
    return switch (method) {
      case "setup" -> SETUP;
      case "take-shots" -> TAKESHOTS;
      case "report-damage" -> REPORTDAMAGE;
      case "successful-hits" -> SUCCESSFULHITS;
      case "join" -> JOIN;
      case "end-game" -> ENDGAME;
      default -> throw new IllegalStateException("Unexpected value: " + method);
    };
  }

  /**
   *
   * @return converts this method to a string
   */
  @Override
  public String toString() {
    return switch (this) {
      case SETUP -> "setup";
      case TAKESHOTS -> "take-shots";
      case REPORTDAMAGE -> "report-damage";
      case SUCCESSFULHITS -> "successful-hits";
      case JOIN -> "join";
      case ENDGAME -> "end-game";
    };
  }

}

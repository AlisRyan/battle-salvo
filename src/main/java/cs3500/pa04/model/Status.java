package cs3500.pa04.model;

/**
 * Represents the different statuses of a coordinate
 */
public enum Status {
  HIT("H"),
  OCCUPIED("S"),
  EMPTY("0"),
  MISSED("M");
  private final String letter;

  /**
   * Creates a status
   *
   * @param letter the letter representation of the status
   */
  Status(String letter) {
    this.letter = letter;
  }

  /**
   * Returns the letter of the status
   *
   * @return returns this status's letter
   */
  public String getLetter() {
    return letter;
  }
}
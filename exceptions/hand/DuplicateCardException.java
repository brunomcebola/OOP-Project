package exceptions.hand;

public class DuplicateCardException extends Exception {
  /**
   * Prints a message stating that there were duplicate cards on the hand
   */
  public DuplicateCardException() {
    super("Duplicate card.");
  }
}

package exceptions.card;

public class InvalidCardValueAndRankException extends Exception {
  /**
     * Prints the exception of number of invalid value and rank
     */
  public InvalidCardValueAndRankException() {
    super("The specified card value and rank are not valid.");
  }
}

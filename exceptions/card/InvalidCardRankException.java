package exceptions.card;

public class InvalidCardRankException extends Exception {
  /**
     * Prints the exception of number of invalid rank
     */
  public InvalidCardRankException() {
    super("The specified card rank is not valid.");
  }
}

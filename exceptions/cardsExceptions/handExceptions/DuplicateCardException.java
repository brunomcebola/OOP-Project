package exceptions.cardsExceptions.handExceptions;

public class DuplicateCardException extends Exception {
  /**
   * Prints the exception of number of empty deck
   */
  public DuplicateCardException() {
    super("d: duplicate card.");
  }
}

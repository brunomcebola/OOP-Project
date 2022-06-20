package exceptions.deckExceptions;

public class EmptyDeckException extends Exception {
  /**
     * Prints the exception of number of empty deck
     */
  public EmptyDeckException() {
    super("There are no cards available to draw from the deck.");
  }
}

package exceptions.deckExceptions;

public class EmptyDeckException extends Exception {
  public EmptyDeckException() {
    super("There are no cards available to withdraw from the deck.");
  }
}

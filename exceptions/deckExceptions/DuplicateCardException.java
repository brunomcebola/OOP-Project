package exceptions.deckExceptions;

public class DuplicateCardException extends Exception {
  public DuplicateCardException() {
    super("Card already exists on the deck.");
  }
}

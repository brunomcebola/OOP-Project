package exceptions;

public class EmptyDeckException extends Exception {
  public EmptyDeckException() {
    super("There are no cards available on the deck.");
  }
}

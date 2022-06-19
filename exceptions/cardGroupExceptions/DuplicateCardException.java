package exceptions.cardGroupExceptions;

public class DuplicateCardException extends Exception {
  public DuplicateCardException() {
    super("There cannot be duplciate cards.");
  }

  public DuplicateCardException(String card) {
    super("There cannot be duplciate cards (" + card + ").");
  }
}

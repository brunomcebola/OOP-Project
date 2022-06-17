package exceptions.cardGroupExceptions;

public class DuplicateCardException extends Exception {
  public DuplicateCardException() {
    super("There cannot be duplciate cards.");
  }
}

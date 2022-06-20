package exceptions.gameExceptions;

public class InvalidHoldException extends Exception {
  public InvalidHoldException() {
    super("h: illegal command");
  }
}

package exceptions.gameExceptions;

public class InvalidBetException extends Exception {
  public InvalidBetException() {
    super("b: illegal command");
  }
}

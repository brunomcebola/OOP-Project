package exceptions.gameExceptions;

public class InvalidDealException extends Exception {
  public InvalidDealException() {
    super("d: illegal command");
  }
}

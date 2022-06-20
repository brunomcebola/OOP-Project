package exceptions.gameExceptions;

public class InvalidAdviceException extends Exception {
  public InvalidAdviceException() {
    super("a: illegal command");
  }
}

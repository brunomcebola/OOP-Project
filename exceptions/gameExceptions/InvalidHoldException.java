package exceptions.gameExceptions;

public class InvalidHoldException extends Exception {
  /**
     * Prints the exception of illegal command for hold
     */
  public InvalidHoldException() {
    super("h: illegal command");
  }
}

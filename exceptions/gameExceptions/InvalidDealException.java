package exceptions.gameExceptions;

public class InvalidDealException extends Exception {
  /**
     * Prints the exception of number of illegal command for deal
     */
  public InvalidDealException() {
    super("d: illegal command");
  }
}

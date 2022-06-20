package exceptions.gameExceptions.game;

public class InvalidAdviceException extends Exception {
  /**
     * Prints the exception of number of illegal command of advice
     */
  public InvalidAdviceException() {
    super("a: illegal command");
  }
}

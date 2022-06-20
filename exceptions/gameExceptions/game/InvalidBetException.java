package exceptions.gameExceptions.game;

public class InvalidBetException extends Exception {
  /**
     * Prints the exception of number of illegal command for betting
     */
  public InvalidBetException() {
    super("b: illegal command");
  }
}

package exceptions.game;

public class IllegalBetException extends Exception {
  /**
   * Prints a message of illegal bet amount
   */
  public IllegalBetException() {
    super("b: illegal amount");
  }
}
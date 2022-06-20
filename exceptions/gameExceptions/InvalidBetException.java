package exceptions.gameExceptions;

public class InvalidBetException extends Exception {
  public InvalidBetException() {
    super("b: illegal command");
  }

  public InvalidBetException(int bet) {
    super("The placed bet value (" + bet + ") is invalid");
  }
}

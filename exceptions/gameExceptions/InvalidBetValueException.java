package exceptions.gameExceptions;

public class InvalidBetValueException extends Exception {
  public InvalidBetValueException() {
    super("The placed bet value is invalid");
  }

  public InvalidBetValueException(int bet) {
    super("The placed bet value (" + bet + ") is invalid");
  }
}

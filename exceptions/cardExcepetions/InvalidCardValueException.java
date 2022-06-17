package exceptions.cardExcepetions;

public class InvalidCardValueException extends Exception {
  public InvalidCardValueException() {
    super("The specified card value is not valid.");
  }
}

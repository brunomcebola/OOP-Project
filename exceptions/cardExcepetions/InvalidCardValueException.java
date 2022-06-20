package exceptions.cardExcepetions;

public class InvalidCardValueException extends Exception {
  /**
     * Prints the exception of number of invalid value
     */
  public InvalidCardValueException() {
    super("The specified card value is not valid.");
  }
}

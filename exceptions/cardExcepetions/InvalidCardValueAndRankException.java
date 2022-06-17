package exceptions.cardExcepetions;

public class InvalidCardValueAndRankException extends Exception {
  public InvalidCardValueAndRankException() {
    super("The specified card value and rank are not valid.");
  }
}

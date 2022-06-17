package exceptions.cardExcepetions;

public class InvalidCardRankException extends Exception {
  public InvalidCardRankException() {
    super("The specified card rank is not valid.");
  }
}

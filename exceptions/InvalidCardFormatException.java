package exceptions;

public class InvalidCardFormatException extends Exception {
  public InvalidCardFormatException() {
    super("The specified combination of value and rank does not exist");
  }
}

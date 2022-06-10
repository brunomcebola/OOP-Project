package exceptions;

public class InvalidNumberOfArgumentsException extends Exception {
  public InvalidNumberOfArgumentsException() {
    super("It is mandatory to have 4 arguments.");
  }
}

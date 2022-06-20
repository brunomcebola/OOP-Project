package exceptions;

public class InvalidNumberOfArgumentsException extends Exception {
  /**
     * Prints the exception of number of arguments invalid
     */
  public InvalidNumberOfArgumentsException() {
    super("It is mandatory to have 4 arguments.");
  }
}

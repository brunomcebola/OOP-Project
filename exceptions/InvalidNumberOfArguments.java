package exceptions;

public class InvalidNumberOfArguments extends Exception {
  public InvalidNumberOfArguments() {
    super("It is mandatory to have 4 arguments");
  }
}

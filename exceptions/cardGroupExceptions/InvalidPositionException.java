package exceptions.cardGroupExceptions;

public class InvalidPositionException extends Exception {
  public InvalidPositionException() {
    super("There is no existing card on the reuqired postion.");
  }
}

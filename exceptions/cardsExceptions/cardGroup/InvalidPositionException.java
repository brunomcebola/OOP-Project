package exceptions.cardsExceptions.cardGroup;

public class InvalidPositionException extends Exception {
  /**
     * Prints the exception of number of invalid postion
     */
  public InvalidPositionException() {
    super("There is no existing card on the reuqired postion.");
  }
}

package exceptions.gameDebug;

public class invalidDeckSizeException extends Exception {
    /**
     * Prints the exception of number of invalid decksize
     */
    public invalidDeckSizeException() {
        super("The deck is not big enough for the command list in the command file.");
    }
}


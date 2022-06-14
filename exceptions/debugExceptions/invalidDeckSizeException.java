package exceptions.debugExceptions;

public class invalidDeckSizeException extends Exception {
    public invalidDeckSizeException() {
        super("The deck is not big enough for the command list in the command file.");
    }
}


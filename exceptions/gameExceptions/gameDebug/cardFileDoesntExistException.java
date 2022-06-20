package exceptions.gameExceptions.gameDebug;

public class cardFileDoesntExistException extends Exception {
    /**
     * Prints the exception of number of file does not exist
     */
    public cardFileDoesntExistException() {
        super("The card file for debug mode does not exist.");
    }
}

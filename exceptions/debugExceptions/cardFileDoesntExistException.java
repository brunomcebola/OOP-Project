package exceptions.debugExceptions;

public class cardFileDoesntExistException extends Exception {
    public cardFileDoesntExistException() {
        super("The card file for debug mode does not exist.");
    }
}

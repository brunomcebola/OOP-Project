package exceptions.debugExceptions;

public class cmdFileDoesntExistException extends Exception {
    public cmdFileDoesntExistException() {
        super("The command file for debug mode does not exist.");
    }
}

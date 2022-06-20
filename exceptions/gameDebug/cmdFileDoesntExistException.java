package exceptions.gameDebug;

public class cmdFileDoesntExistException extends Exception {
    /**
     * Prints the exception of number of arguments invalid
     */
    public cmdFileDoesntExistException() {
        super("The command file for debug mode does not exist.");
    }
}

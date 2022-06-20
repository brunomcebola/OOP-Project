package exceptions.debugExceptions;

public class invalidCommandException extends Exception{
    /**
     * Prints the exception of number of invalid command
     */
    public invalidCommandException(String cmd, int position) {
        super("Command nº" + position + " in the command file (" + cmd + ") is not supported.");
    }
}

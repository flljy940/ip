package rocky.exception;

/**
 * Exception class for exceptions strictly within the application
 * These exceptions should always be handled
 * The error message should be properly displayed to the user
 */
public class RockyException extends Exception {
    public RockyException(String error) {
        super(error);
    }
}

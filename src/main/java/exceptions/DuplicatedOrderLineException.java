package exceptions;

public class DuplicatedOrderLineException extends RuntimeException {
    public DuplicatedOrderLineException(String message) {
        super(message);
    }
}

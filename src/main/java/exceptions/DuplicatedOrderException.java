package exceptions;

public class DuplicatedOrderException extends RuntimeException {
    public DuplicatedOrderException(String message) {
        super(message);
    }
}

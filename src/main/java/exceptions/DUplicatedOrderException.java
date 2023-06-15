package exceptions;

public class DUplicatedOrderException extends RuntimeException {
    public DUplicatedOrderException(String message) {
        super(message);
    }
}

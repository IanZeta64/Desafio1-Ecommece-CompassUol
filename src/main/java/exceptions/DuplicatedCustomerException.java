package exceptions;

public class DuplicatedCustomerException extends RuntimeException {
    public DuplicatedCustomerException(String message) {
        super(message);
    }
}

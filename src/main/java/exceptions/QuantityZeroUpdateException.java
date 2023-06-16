package exceptions;

public class QuantityZeroUpdateException extends RuntimeException {
    public QuantityZeroUpdateException(String message) {
        super(message);
    }
}

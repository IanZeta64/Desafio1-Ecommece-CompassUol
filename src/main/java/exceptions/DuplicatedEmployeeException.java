package exceptions;

public class DuplicatedEmployeeException extends RuntimeException {
    public DuplicatedEmployeeException(String message) {
        super(message);
    }
}

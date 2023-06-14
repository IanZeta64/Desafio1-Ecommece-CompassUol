package exceptions;

public class DuplicatedProductExceptions extends RuntimeException{
    public DuplicatedProductExceptions(String message) {
        super(message);
    }
}

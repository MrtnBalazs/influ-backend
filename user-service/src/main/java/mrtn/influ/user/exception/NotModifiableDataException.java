package mrtn.influ.user.exception;

public class NotModifiableDataException extends RuntimeException {
    public NotModifiableDataException(String message) {
        super(message);
    }
    public NotModifiableDataException(Throwable cause) {
        super(cause);
    }

}

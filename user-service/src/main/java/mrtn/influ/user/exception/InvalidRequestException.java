package mrtn.influ.user.exception;

public class InvalidRequestException extends RuntimeException {
    public InvalidRequestException(String message) {
        super(message);
    }
    public InvalidRequestException(Throwable cause) {
        super(cause);
    }
}

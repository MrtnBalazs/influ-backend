package mrtn.influ.gateway.error.handling;

public class TechnicalException extends RuntimeException {
    public TechnicalException(String message) {
        super(message);
    }
}

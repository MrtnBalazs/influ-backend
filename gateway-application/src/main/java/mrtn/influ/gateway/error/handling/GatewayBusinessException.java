package mrtn.influ.gateway.error.handling;

public class GatewayBusinessException extends RuntimeException {
    public GatewayBusinessException(String message) {
        super(message);
    }
}

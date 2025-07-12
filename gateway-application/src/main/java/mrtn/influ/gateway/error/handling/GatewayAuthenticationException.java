package mrtn.influ.gateway.error.handling;

public class GatewayAuthenticationException extends RuntimeException {
    public GatewayAuthenticationException(String message) {
        super(message);
    }
}

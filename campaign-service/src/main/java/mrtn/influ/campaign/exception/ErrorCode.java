package mrtn.influ.campaign.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {
    NOT_AUTHORISED_TO_DELETE(HttpStatus.UNAUTHORIZED, "Unauthorized to delete this resource, resource id: %s"),
    CAMPAIGN_NOT_FOUND(HttpStatus.BAD_REQUEST, "Could not find campaign for id: %s"),
    PITCH_NOT_FOUND(HttpStatus.BAD_REQUEST, "Could not find pitch for id: %s");

    private final HttpStatus httpStatus;
    private final String message;

    ErrorCode(HttpStatus httpStatus, String message){
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public HttpStatus getHttpStatus() {
        return this.httpStatus;
    }

    public void throwException(String... arg) {
        throw new BusinessException(this, message.formatted((Object[]) arg));
    }

    public RuntimeException toException(String... arg) {
        return new BusinessException(this, message.formatted((Object[]) arg));
    }
}

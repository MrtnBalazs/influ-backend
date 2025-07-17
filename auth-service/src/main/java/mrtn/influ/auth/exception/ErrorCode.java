package mrtn.influ.auth.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {
    // TODO spring already validates the request, it is never thrown
    FIELD_MISSING(HttpStatus.BAD_REQUEST, "Field is missing! field: %s"),
    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "Token is expired!"),
    TOKEN_ISSUER_WRONG(HttpStatus.UNAUTHORIZED, "Token issuer is wrong!"),
    USER_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "User already exists with email: %s"),
    USER_SERVICE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "User service error"),
    TECHNICAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "");

    private final HttpStatus httpStatus;
    private final String message;

    ErrorCode(HttpStatus httpStatus, String message){
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public HttpStatus getHttpStatus() {
        return this.httpStatus;
    }

    public void toException(String... arg) {
        throw new BusinessException(this, message.formatted((Object[]) arg));
    }
}

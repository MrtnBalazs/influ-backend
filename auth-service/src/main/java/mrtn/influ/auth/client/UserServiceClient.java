package mrtn.influ.auth.client;

import mrtn.influ.auth.exception.ErrorCode;
import mrtn.influ.auth.util.Consts;
import mrtn.influ.user.dto.CreateUserRequest;
import mrtn.influ.user.dto.GetUserResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class UserServiceClient {

    private final RestClient restClient;

    public UserServiceClient(
            RestClient.Builder restClientBuilder,
            @Value("${user-service.base-url}") String baseUrl) {
        restClient = restClientBuilder.baseUrl(baseUrl).build();
    }

    public void callRegisterUser(mrtn.influ.auth.dto.RegisterRequest registerRequest) {
        CreateUserRequest createUserRequest = CreateUserRequest.builder()
                .userType(registerRequest.getUserType().name())
                .username(registerRequest.getUsername())
                .build();
        try {
            ResponseEntity<Void> response = restClient
                    .post()
                    .uri("/api/v1/users")
                    .header(Consts.X_USER_ID_HEADER, registerRequest.getEmail())
                    .body(createUserRequest)
                    .retrieve()
                    .toBodilessEntity();

            if(!response.getStatusCode().equals(HttpStatus.CREATED))
                ErrorCode.USER_SERVICE_ERROR.throwException();
        } catch (Exception exception) {
            ErrorCode.TECHNICAL_ERROR.throwException();
        }
    }

    public GetUserResponse callGetUser(String email) {
        try {
            ResponseEntity<GetUserResponse> response = restClient
                    .get()
                    .uri("/api/v1/users/user")
                    .header(Consts.X_USER_ID_HEADER, email)
                    .retrieve()
                    .toEntity(GetUserResponse.class);

            if(!response.getStatusCode().equals(HttpStatus.OK))
                ErrorCode.USER_SERVICE_ERROR.throwException();

            return response.getBody();
        } catch (Exception exception) {
            throw ErrorCode.TECHNICAL_ERROR.toException();
        }
    }
}

package mrtn.influ.auth.client;

import mrtn.influ.auth.exception.ErrorCode;
import mrtn.influ.auth.util.Consts;
import mrtn.influ.user.dto.CreateUserRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class UserServiceClient {

    @Value( "${user.service.url}" )
    private String userServiceUrl;

    RestClient restClient = RestClient.create();

    public void callRegisterUser(mrtn.influ.auth.dto.RegisterRequest registerRequest) {
        CreateUserRequest createUserRequest = CreateUserRequest.builder()
                .userType(registerRequest.getUserType().name())
                .username(registerRequest.getUsername())
                .build();
        try {
            ResponseEntity<Void> response = restClient
                    .post()
                    .uri(userServiceUrl + "/api/v1/users")
                    .header(Consts.X_USER_ID_HEADER, registerRequest.getEmail())
                    .body(createUserRequest)
                    .retrieve()
                    .toBodilessEntity();

            if(!response.getStatusCode().equals(HttpStatus.CREATED))
                ErrorCode.USER_SERVICE_ERROR.toException();
        } catch (Exception exception) {
            ErrorCode.TECHNICAL_ERROR.toException();
        }
    }
}

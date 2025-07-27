package mrtn.influ.user.endpoint;

import mrtn.influ.user.service.UserService;
import mrtn.influ.user.dto.CreateUserRequest;
import mrtn.influ.user.dto.GetUserResponse;
import mrtn.influ.user.log.LogRequestResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;


@Controller
public class UserController implements UserApi {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @LogRequestResponse
    @Override
    public ResponseEntity<GetUserResponse> getUser(String xUserId){
        GetUserResponse getUserResponse = userService.getUserByEmail(xUserId);
        return ResponseEntity.ok(getUserResponse);
    }

    @LogRequestResponse
    @Override
    public ResponseEntity<Void> createUser(String xUserId, CreateUserRequest createUserRequest) {
        userService.createUser(xUserId, createUserRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}

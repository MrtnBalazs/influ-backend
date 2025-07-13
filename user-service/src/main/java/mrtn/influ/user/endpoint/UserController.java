package mrtn.influ.user.endpoint;

import mrtn.influ.user.business.service.UserService;
import mrtn.influ.userservice.dto.CreateUserRequest;
import mrtn.influ.userservice.dto.GetUserResponse;
import mrtn.influ.userservice.endpoint.UserApi;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "http://localhost:4200") // TODO only for testing without gateway
@Controller
public class UserController implements UserApi {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Override
    public ResponseEntity<GetUserResponse> getUser(String xUserId){
        GetUserResponse getUserResponse = userService.getUserByEmail(xUserId);
        return ResponseEntity.ok(getUserResponse);
    }

    @Override
    public ResponseEntity<Void> createUser(String xUserId, CreateUserRequest createUserRequest) {
        userService.createUser(xUserId, createUserRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}

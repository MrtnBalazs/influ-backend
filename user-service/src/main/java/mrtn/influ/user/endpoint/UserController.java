package mrtn.influ.user.endpoint;

import mrtn.influ.user.dto.SetUserDataRequest;
import mrtn.influ.user.service.UserService;
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
    public ResponseEntity<Void> createUserFromEmail(String username, String email) {
        userService.createUser(email, username);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @LogRequestResponse
    @Override
    public ResponseEntity<Void> setUserData(String username, SetUserDataRequest setUserDataRequest) {
        userService.setUserData(username, setUserDataRequest);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}

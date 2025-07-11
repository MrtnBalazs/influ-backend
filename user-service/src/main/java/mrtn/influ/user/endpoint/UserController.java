package mrtn.influ.user.endpoint;

import mrtn.influ.user.business.service.UserService;
import mrtn.influ.user.dto.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200") // TODO only for testing without gateway
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user")
    public ResponseEntity<GetUserResponse> getUser(@RequestHeader("X-User-Id") String username){
        UserDto userDto = userService.getUserByUsername(username);
        return ResponseEntity.ok(new GetUserResponse(userDto));
    }

    @GetMapping
    public ResponseEntity<GetUsersResponse> getUser(@RequestParam(name = "user_type", required = false) UserType userType){
        List<UserDto> userDtos = userService.getUsers(userType);
        return ResponseEntity.ok(new GetUsersResponse(userDtos));
    }

    @PostMapping("/create")
    public ResponseEntity<Void> createUser(@RequestHeader("X-User-Id") String username, @RequestBody CreateUserRequest createUserRequest) {
        userService.createUser(username, createUserRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/settings")
    public void updateSettings(@RequestHeader("X-User-Id") String username, @RequestBody UpdateSettingsRequest settings) {
        userService.updateSettings(username, settings);
    }
}

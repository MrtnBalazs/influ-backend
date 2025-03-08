package mrtn.influ.auth.endpoint;

import mrtn.influ.auth.dto.*;
import mrtn.influ.auth.model.User;
import mrtn.influ.auth.repository.UserRepository;
import mrtn.influ.auth.service.JwtService;
import mrtn.influ.auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsersWithPasswords() {
        System.out.println("ok");
        return new ResponseEntity<>(userRepository.findAll(), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        final String authToken = jwtService.createAuthToken(loginRequest);
        return ResponseEntity.ok(new LoginResponse(authToken));
    }

    @PostMapping("/signup")
    public ResponseEntity<Void> register(@RequestBody RegisterRequest registerRequest) {
        if (userService.findByUsername(registerRequest.username()) != null) {
            return ResponseEntity.badRequest().build();
        }
        userService.save(new User(registerRequest.username(), registerRequest.password(), "USER"));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/verify")
    public ResponseEntity<?> verify(@RequestHeader("AuthToken") String token) {
         if(jwtService.validateToken(token))
             return ResponseEntity.ok().build();
         else
             return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}

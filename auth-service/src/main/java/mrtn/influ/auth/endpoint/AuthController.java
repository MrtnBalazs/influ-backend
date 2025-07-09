package mrtn.influ.auth.endpoint;

import mrtn.influ.auth.dto.LoginRequest;
import mrtn.influ.auth.dto.LoginResponse;
import mrtn.influ.auth.dto.RegisterRequest;
import mrtn.influ.auth.repository.UserRepository;
import mrtn.influ.auth.service.JwtService;
import mrtn.influ.auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200") // TODO remove for production
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        final String authToken = jwtService.createAuthToken(loginRequest);
        return ResponseEntity.ok(LoginResponse.builder().authToken(authToken).build());
    }

    @PostMapping("/signup")
    public ResponseEntity<Void> register(@RequestBody RegisterRequest registerRequest) {
        userService.registerUser(registerRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/verify")
    public ResponseEntity<String> verify(@RequestHeader("X-Auth-Token") String token) {
         if(jwtService.validateToken(token))
             return ResponseEntity.ok().body(jwtService.getSubjectFromToken(token));
         else
             return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}

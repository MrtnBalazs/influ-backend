package mrtn.influ.auth.endpoint;

import mrtn.influ.auth.dto.*;
import mrtn.influ.auth.model.User;
import mrtn.influ.auth.service.JwtService;
import mrtn.influ.auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) throws Exception {
        final String jwt = jwtService.createJwtToken(loginRequest);
        return ResponseEntity.ok(new LoginResponse(jwt));
    }

    @PostMapping("/signup")
    public ResponseEntity<RegisterResponse> register(@RequestBody RegisterRequest registerRequest) {
        if (userService.findByUsername(registerRequest.username()) != null) {
            return ResponseEntity.badRequest().body(new RegisterResponse("Bad request"));
        }
        userService.save(new User(registerRequest.username(), registerRequest.password(), "ROLE"));
        return ResponseEntity.ok(new RegisterResponse("TODO"));
    }

    @GetMapping("/verify")
    public ResponseEntity<?> verify(@RequestHeader("jwt") String token, @RequestHeader("username") String username) {
         if(jwtService.validateToken(token, username))
             return ResponseEntity.ok().build();
         else
             return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}

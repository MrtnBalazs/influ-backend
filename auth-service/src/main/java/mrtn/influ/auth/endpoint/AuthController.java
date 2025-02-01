package mrtn.influ.auth.endpoint;

import mrtn.influ.auth.dto.AuthenticationRequest;
import mrtn.influ.auth.dto.AuthenticationResponse;
import mrtn.influ.auth.model.User;
import mrtn.influ.auth.service.JwtService;
import mrtn.influ.auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        final String jwt = jwtService.createJwtToken(authenticationRequest);
        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        if (userService.findByUsername(user.getUsername()) != null) {
            return ResponseEntity.badRequest().body("Username is already taken.");
        }
        userService.save(user);
        return ResponseEntity.ok("User registered successfully.");
    }
}

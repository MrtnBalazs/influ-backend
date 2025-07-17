package mrtn.influ.auth.endpoint;

import mrtn.influ.auth.dto.LoginRequest;
import mrtn.influ.auth.dto.LoginResponse;
import mrtn.influ.auth.dto.RegisterRequest;
import mrtn.influ.auth.dao.repository.UserRepository;
import mrtn.influ.auth.service.JwtService;
import mrtn.influ.auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@CrossOrigin(origins = "http://localhost:4200") // TODO set it in properties file
public class AuthController implements AuthApi {
    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Override
    public ResponseEntity<LoginResponse> loginUser(LoginRequest loginRequest) {
        final String authToken = jwtService.createAuthToken(loginRequest);
        return ResponseEntity.ok(LoginResponse.builder().authToken(authToken).build());
    }

    @Override
    public ResponseEntity<Void> registerUser(RegisterRequest registerRequest) {
        userService.registerUser(registerRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Override
    public ResponseEntity<String> verifyToken(String xAuthToken) {
         jwtService.validateToken(xAuthToken);
         return ResponseEntity.ok().body(jwtService.getSubjectFromToken(xAuthToken));
    }
}

package mrtn.influ.controller;

import mrtn.influ.dto.LoginRp;
import mrtn.influ.dto.LoginRq;
import mrtn.influ.dto.RegisterRp;
import mrtn.influ.dto.RegisterRq;
import mrtn.influ.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authorizationService;

    @Autowired
    private AuthService authService;

    // Build Login REST API
    @PostMapping("/login")
    public ResponseEntity<LoginRp> login(@RequestBody LoginRq loginRq) {
        LoginRp loginRp = authService.login(loginRq);
        return new ResponseEntity<>(loginRp, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterRp> register(@RequestBody RegisterRq rq) {
        authService.register(rq);
        return new ResponseEntity<>(new RegisterRp("jwt"), HttpStatus.OK);
    }

}

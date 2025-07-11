package mrtn.influ.auth.service;

import jakarta.validation.ValidationException;
import mrtn.influ.auth.dto.LoginRequest;
import mrtn.influ.auth.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class JwtService {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    public String createAuthToken(LoginRequest loginRequest) {
        if(Objects.isNull(loginRequest.getEmail()) || Objects.isNull(loginRequest.getPassword()))
            throw new ValidationException("Missing email or password from login request!");

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        final UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getEmail());
        return jwtUtil.generateToken(userDetails);
    }

    public void validateToken(String token) {
        jwtUtil.validateExpiry(token);
        jwtUtil.validateIssuer(token);
    }

    public String getSubjectFromToken(String token) {
        return jwtUtil.extractSubject(token);
    }
}

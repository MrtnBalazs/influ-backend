package mrtn.influ.auth.service;

import mrtn.influ.auth.dao.entity.User;
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
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        final User userEntity = userDetailsService.loadUserEntityByUsername(loginRequest.getEmail());
        return jwtUtil.generateToken(userEntity);
    }

    public void validateToken(String token) {
        jwtUtil.validateExpiry(token);
        jwtUtil.validateIssuer(token);
    }

    public String getSubjectFromToken(String token) {
        return jwtUtil.extractSubject(token);
    }
}

package mrtn.influ.auth.service;

import mrtn.influ.auth.dto.LoginRequest;
import mrtn.influ.auth.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class JwtService {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    public String createAuthToken(LoginRequest loginRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequest.username(), loginRequest.password()));

        final UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.username());
        return jwtUtil.generateToken(userDetails);
    }

    public boolean validateToken(String token) {
        return jwtUtil.validateToken(token);
    }
}

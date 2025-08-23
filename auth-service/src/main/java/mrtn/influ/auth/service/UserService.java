package mrtn.influ.auth.service;

import jakarta.transaction.Transactional;
import mrtn.influ.auth.client.UserServiceClient;
import mrtn.influ.auth.dao.entity.User;
import mrtn.influ.auth.dao.repository.UserRepository;
import mrtn.influ.auth.dto.LoginRequest;
import mrtn.influ.auth.dto.LoginResponse;
import mrtn.influ.auth.dto.RegisterRequest;
import mrtn.influ.auth.exception.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserServiceClient userServiceClient;
    @Autowired
    private JwtService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public void registerUser(RegisterRequest registerRequest) {
        Optional<User> optionalUser = userRepository.findByEmail(registerRequest.getEmail());
        if(optionalUser.isPresent())
            ErrorCode.USER_ALREADY_EXISTS.toException(registerRequest.getEmail());

        userServiceClient.callRegisterUser(registerRequest);

        saveUser(registerRequest);
    }

    private void saveUser (RegisterRequest registerRequest) {
        String password = passwordEncoder.encode(registerRequest.getPassword());
        User user = new User(registerRequest.getEmail(), password, registerRequest.getUserType().getValue(), "USER");
        userRepository.save(user);
    }

    public LoginResponse loginUser(LoginRequest loginRequest) {
        final String authToken = jwtService.createAuthToken(loginRequest);
        String userType = getUserType(loginRequest.getEmail());
        return LoginResponse.builder()
                .authToken(authToken)
                .userType(userType)
                .email(loginRequest.getEmail())
                .build();
    }

    private String getUserType(String email) {
        return userServiceClient.callGetUser(email).getUserType();
    }
}

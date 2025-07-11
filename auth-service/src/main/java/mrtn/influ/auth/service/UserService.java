package mrtn.influ.auth.service;

import jakarta.transaction.Transactional;
import jakarta.validation.ValidationException;
import mrtn.influ.auth.dto.RegisterRequest;
import mrtn.influ.auth.exception.UserAlreadyExistsException;
import mrtn.influ.auth.model.User;
import mrtn.influ.auth.repository.UserRepository;
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
    private PasswordEncoder passwordEncoder;

    @Transactional
    public void registerUser(RegisterRequest registerRequest) {
        if(Objects.isNull(registerRequest.getEmail()) || Objects.isNull(registerRequest.getPassword()))
            throw new ValidationException("Missing email or password from register request!");

        Optional<User> optionalUser = userRepository.findByEmail(registerRequest.getEmail());
        if(optionalUser.isPresent())
            throw new UserAlreadyExistsException("User already exists with the email: %s".formatted(registerRequest.getEmail()));

        saveUser(registerRequest);
    }

    private void saveUser (RegisterRequest registerRequest) {
        String password = passwordEncoder.encode(registerRequest.getPassword());
        User user = new User(registerRequest.getEmail(), password, "USER");
        userRepository.save(user);
    }
}

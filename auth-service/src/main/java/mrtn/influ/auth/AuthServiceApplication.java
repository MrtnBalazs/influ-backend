package mrtn.influ.auth;

import mrtn.influ.auth.dao.entity.User;
import mrtn.influ.auth.dao.repository.UserRepository;
import mrtn.influ.auth.dto.LoginRequest;
import mrtn.influ.auth.service.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class AuthServiceApplication implements CommandLineRunner {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthServiceApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(AuthServiceApplication.class, args);
    }

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        User user1 = new User();
        user1.setEmail("email");
        user1.setPassword(passwordEncoder.encode("password"));

        User user2 = new User();
        user2.setEmail("admin");
        user2.setPassword(passwordEncoder.encode("admin"));

        userRepository.save(user1);
        userRepository.save(user2);

        LOGGER.info(jwtService.createAuthToken(new LoginRequest("email", "password")));
    }
}
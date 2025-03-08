package mrtn.influ.auth;

import mrtn.influ.auth.dto.LoginRequest;
import mrtn.influ.auth.model.User;
import mrtn.influ.auth.service.JwtService;
import mrtn.influ.auth.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AuthServiceApplication implements CommandLineRunner {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthServiceApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(AuthServiceApplication.class, args);
    }

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    @Override
    public void run(String... args) {
        userService.save(new User("username", "password", "USER"));
        userService.save(new User("admin", "admin", "ADMIN"));
        LOGGER.info(jwtService.createAuthToken(new LoginRequest("username", "password")));
    }
}
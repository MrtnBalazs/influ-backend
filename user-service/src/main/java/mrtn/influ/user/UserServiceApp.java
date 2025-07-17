package mrtn.influ.user;

import mrtn.influ.user.business.service.UserService;
import mrtn.influ.user.dto.CreateUserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UserServiceApp implements CommandLineRunner {

    @Autowired
    public UserService userService;

    public static void main(String[] args) {
        SpringApplication.run(UserServiceApp.class, args);
    }

    @Override
    public void run(String... args) {
        userService.createUser("emailBrand", CreateUserRequest.builder().username("usernameBrand").userType("BRAND").build());
        userService.createUser("emailInfluencer", CreateUserRequest.builder().username("usernameInfluencer").userType("INFLUENCER").build());
    }
}
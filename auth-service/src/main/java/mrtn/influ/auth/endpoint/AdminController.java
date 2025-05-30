package mrtn.influ.auth.endpoint;

import mrtn.influ.auth.model.User;
import mrtn.influ.auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/auth/admin") // TODO make it available only with ADMIN role
public class AdminController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsersWithPasswords() {
        return new ResponseEntity<>(userRepository.findAll(), HttpStatus.OK);
    }
}

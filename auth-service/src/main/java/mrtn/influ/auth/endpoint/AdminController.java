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
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsersWithPasswords() {
        System.out.println("ok");
        return new ResponseEntity<>(userRepository.findAll(), HttpStatus.OK);
    }
}

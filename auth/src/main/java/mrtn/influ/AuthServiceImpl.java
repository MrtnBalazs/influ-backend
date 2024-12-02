package mrtn.influ;

import mrtn.influ.db.User;
import mrtn.influ.db.UserRepository;
import mrtn.influ.dto.LoginRp;
import mrtn.influ.dto.LoginRq;
import mrtn.influ.dto.RegisterRp;
import mrtn.influ.dto.RegisterRq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService{
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Override
    public RegisterRp register(RegisterRq registerRq) {
        userRepository.save(new User(registerRq.email(), registerRq.password()));

        String token = jwtTokenProvider.generateToken(registerRq.email());

        return new RegisterRp(token);
    }

    @Override
    public LoginRp login(LoginRq loginRq) {

        // 01 - AuthenticationManager is used to authenticate the user
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRq.getUsername(),
                loginRq.getPassword()
        ));

        /* 02 - SecurityContextHolder is used to allow the rest of the application to know
        that the user is authenticated and can use user data from Authentication object */
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 03 - Generate the token based on username and secret key
        String token = jwtTokenProvider.generateToken(authentication.getName());

        // 04 - Return the token to controller
        return new LoginRp(token);
    }
}

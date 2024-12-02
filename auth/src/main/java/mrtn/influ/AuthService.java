package mrtn.influ;

import mrtn.influ.dto.LoginRp;
import mrtn.influ.dto.LoginRq;
import mrtn.influ.dto.RegisterRp;
import mrtn.influ.dto.RegisterRq;

public interface AuthService {
    LoginRp login(LoginRq loginRq);
    RegisterRp register(RegisterRq registerRq);
}

package mrtn.influ.user.service;

import mrtn.influ.user.dao.repository.UserRepository;
import mrtn.influ.user.dto.GetUserResponse;
import mrtn.influ.user.dao.entity.UserEntity;
import mrtn.influ.user.dao.entity.UserType;
import mrtn.influ.user.dto.SetUserDataRequest;
import mrtn.influ.user.exception.InvalidRequestException;
import mrtn.influ.user.exception.NotModifiableDataException;
import mrtn.influ.user.exception.UserAlreadyExistsException;
import mrtn.influ.user.exception.UserNotFoundException;
import mrtn.influ.user.mapper.UserMapper;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserMapper userMapper, UserRepository userRepository) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public GetUserResponse getUserByEmail(String username) {
        UserEntity userEntity = userRepository.findUserByUsername(username).orElseThrow(() -> new UserNotFoundException("Could not find user with the username: %s".formatted(username)));
        return userMapper.mapUserEntityToUserDto(userEntity);
    }

    public void createUser(String email, String username) {
        if(userRepository.findUserByEmail(email).isEmpty() && userRepository.findUserByUsername(username).isEmpty()){
            userRepository.save(new UserEntity(email, username, null));
        } else {
            throw new UserAlreadyExistsException("User already exists, email: %s, username: %s".formatted(email, username));
        }
    }

    public void setUserData(String username, SetUserDataRequest setUserDataRequest) {
        if(Objects.isNull(setUserDataRequest.getUserType()))
            throw new InvalidRequestException("Missing userType!");

        Optional<UserEntity> user = userRepository.findUserByUsername(username);
        user.ifPresentOrElse(userEntity -> {
            if(Objects.isNull(userEntity.getUserType())) {
                userEntity.setUserType(UserType.valueOf(setUserDataRequest.getUserType()));
                userRepository.save(userEntity);
            } else {
                throw new NotModifiableDataException("User type is not modifiable");
            }
        }, () -> {
            throw new UserNotFoundException("User is not found, username: %s".formatted(username));
        });
    }
}

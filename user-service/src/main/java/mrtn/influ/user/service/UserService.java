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

    public GetUserResponse getUserByEmail(String email) {
        UserEntity userEntity = userRepository.findUserByEmail(email).orElseThrow(() -> new UserNotFoundException("Could not find user with the email: %s".formatted(email)));
        return userMapper.mapUserEntityToUserDto(userEntity);
    }

    public void createUser(String email) {
        if(userRepository.findUserByEmail(email).isEmpty()){
            userRepository.save(new UserEntity(email, UUID.randomUUID().toString(), null));
        } else {
            throw new UserAlreadyExistsException("User already exists, email: %s".formatted(email));
        }
    }

    public void setUserData(String email, SetUserDataRequest setUserDataRequest) {
        if(Objects.isNull(setUserDataRequest.getUserType()))
            throw new InvalidRequestException("Missing userType!");

        Optional<UserEntity> user = userRepository.findUserByEmail(email);
        user.ifPresentOrElse(userEntity -> {
            if(Objects.isNull(userEntity.getUserType())) {
                userEntity.setUserType(UserType.valueOf(setUserDataRequest.getUserType()));
                userRepository.save(userEntity);
            } else {
                throw new NotModifiableDataException("User type is not modifiable");
            }
        }, () -> {
            throw new UserNotFoundException("User is not found, email: %s".formatted(email));
        });
    }
}

package mrtn.influ.user.service;

import mrtn.influ.user.dao.repository.UserRepository;
import mrtn.influ.user.dto.CreateUserRequest;
import mrtn.influ.user.dto.GetUserResponse;
import mrtn.influ.user.dao.entity.UserEntity;
import mrtn.influ.user.dao.entity.UserType;
import mrtn.influ.user.exception.UserAlreadyExistsException;
import mrtn.influ.user.exception.UserNotFoundException;
import mrtn.influ.user.mapper.UserMapper;
import org.springframework.stereotype.Service;

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

    public void createUser(String email, CreateUserRequest createUserRequest) {
        if(userRepository.findUserByEmail(email).isEmpty()){
            userRepository.save(new UserEntity(email, createUserRequest.getUsername(), UserType.valueOf(createUserRequest.getUserType())));
        } else {
            throw new UserAlreadyExistsException("User already exists, email: %s".formatted(email));
        }
    }
}

package mrtn.influ.user.business.service;

import mrtn.influ.user.dao.UserRepository;
import mrtn.influ.user.dto.CreateUserRequest;
import mrtn.influ.user.dto.UpdateSettingsRequest;
import mrtn.influ.user.dto.UserDto;
import mrtn.influ.user.dto.UserType;
import mrtn.influ.user.entity.SettingsEntity;
import mrtn.influ.user.entity.UserEntity;
import mrtn.influ.user.mapper.UserMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserMapper userMapper, UserRepository userRepository) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public UserDto getUserByUsername(String username) {
        UserEntity userEntity = userRepository.findUserByUsername(username).orElseThrow(() -> new RuntimeException("Could not find user with the username: %s".formatted(username)));
        return userMapper.mapUserEntityToUserDto(userEntity);
    }

    public List<UserDto> getUsers(UserType userType) {
        List<UserEntity> userEntityList;
        if(Objects.nonNull(userType))
            userEntityList = userRepository.findUserByUserType(userType);
        else
            userEntityList = userRepository.findAll();
        return userMapper.mapUserEntityListToUserDtoList(userEntityList);
    }

    public void createUser(String username, CreateUserRequest createUserRequest) {
        if(userRepository.findUserByUsername(username).isEmpty()){
            userRepository.save(new UserEntity(username, createUserRequest.getUserType()));
        } else {
            throw new RuntimeException("User already exists, username: %s".formatted(username));
        }
    }

    public void updateSettings(String username, UpdateSettingsRequest updateSettingsRequest) {
        UserEntity userEntity = userRepository.findUserByUsername(username).orElseThrow(() -> new RuntimeException("Could not find user with the username: %s".formatted(username)));
        updateUserSettings(userEntity.getSettings(), updateSettingsRequest);
        userRepository.save(userEntity);
    }

    private void updateUserSettings(SettingsEntity settingsEntity, UpdateSettingsRequest updateSettingsRequest) {
        if(Objects.nonNull(updateSettingsRequest.getEmailNotifications())) {
           settingsEntity.setEmailNotifications(updateSettingsRequest.getEmailNotifications());
        }
    }
}

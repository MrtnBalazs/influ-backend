package mrtn.influ.user.mapper;

import mrtn.influ.user.dto.SettingsDto;
import mrtn.influ.user.dto.UserDto;
import mrtn.influ.user.entity.SettingsEntity;
import mrtn.influ.user.entity.UserEntity;
import org.apache.catalina.User;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class UserMapper {

    public UserDto mapUserEntityToUserDto(UserEntity userEntity) {
        return new UserDto(userEntity.getUsername(), userEntity.getUserType(), mapSettingsEntityToSettingsDto(userEntity.getSettings()));
    }

    public List<UserDto> mapUserEntityListToUserDtoList(List<UserEntity> userEntityList) {
        return userEntityList.stream().map(this::mapUserEntityToUserDto).toList();
    }

    public SettingsDto mapSettingsEntityToSettingsDto(SettingsEntity settingsEntity) {
        return new SettingsDto(settingsEntity.isEmailNotifications());
    }
}

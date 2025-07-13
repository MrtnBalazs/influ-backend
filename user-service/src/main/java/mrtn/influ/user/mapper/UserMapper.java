package mrtn.influ.user.mapper;

import mrtn.influ.user.entity.SettingsEntity;
import mrtn.influ.user.entity.UserEntity;
import mrtn.influ.userservice.dto.GetUserResponse;
import mrtn.influ.userservice.dto.GetUserResponseSettings;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class UserMapper {
    // TODO use mapstruct
    public GetUserResponse mapUserEntityToUserDto(UserEntity userEntity) {
        return new GetUserResponse(userEntity.getEmail(), userEntity.getUsername(), userEntity.getUserType().name(), mapSettingsEntityToSettingsDto(userEntity.getSettings()));
    }

    public GetUserResponseSettings mapSettingsEntityToSettingsDto(SettingsEntity settingsEntity) {
        return new GetUserResponseSettings().emailNotification(settingsEntity.isEmailNotifications());
    }
}

package mrtn.influ.user.mapper;

import mrtn.influ.user.dto.GetUserResponse;
import mrtn.influ.user.dto.GetUserResponseSettings;
import mrtn.influ.user.dao.entity.SettingsEntity;
import mrtn.influ.user.dao.entity.UserEntity;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class UserMapper {
    // TODO use mapstruct
    public GetUserResponse mapUserEntityToUserDto(UserEntity userEntity) {
        return new GetUserResponse(userEntity.getEmail(), userEntity.getUsername(), Objects.nonNull(userEntity.getUserType()) ? userEntity.getUserType().toString() : null, null);
    }

    public GetUserResponseSettings mapSettingsEntityToSettingsDto(SettingsEntity settingsEntity) {
        return new GetUserResponseSettings().emailNotification(settingsEntity.isEmailNotifications());
    }
}

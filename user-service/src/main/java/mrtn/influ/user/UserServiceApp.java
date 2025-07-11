package mrtn.influ.user;

import mrtn.influ.user.dao.UserRepository;
import mrtn.influ.user.dto.SettingsDto;
import mrtn.influ.user.dto.UserType;
import mrtn.influ.user.entity.SettingsEntity;
import mrtn.influ.user.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UserServiceApp implements CommandLineRunner {

    @Autowired
    public UserRepository userRepository;

    public static void main(String[] args) {
        SpringApplication.run(UserServiceApp.class, args);
    }

    @Override
    public void run(String... args) {
        UserEntity userEntity1 = new UserEntity("username", UserType.BRAND);
        SettingsEntity settingsEntity1 = new SettingsEntity(false);
        userEntity1.setSettings(settingsEntity1);

        UserEntity userEntity2 = new UserEntity("username2", UserType.INFLUENCER);
        SettingsEntity settingsEntity2 = new SettingsEntity(false);
        userEntity2.setSettings(settingsEntity2);

        UserEntity userEntity3 = new UserEntity("username3", UserType.BRAND);
        SettingsEntity settingsEntity3 = new SettingsEntity(false);
        userEntity3.setSettings(settingsEntity3);

        userRepository.save(userEntity1);
        userRepository.save(userEntity2);
        userRepository.save(userEntity3);
    }
}
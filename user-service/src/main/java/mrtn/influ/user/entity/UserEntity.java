package mrtn.influ.user.entity;

import jakarta.persistence.*;
import mrtn.influ.user.dto.UserType;

@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @Column
    private String username;

    @Enumerated(EnumType.STRING)
    private UserType userType;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private SettingsEntity settings = new SettingsEntity();

    public UserEntity() {}

    public UserEntity(String username, UserType userType) {
        this.username = username;
        this.userType = userType;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public SettingsEntity getSettings() {
        return settings;
    }

    public void setSettings(SettingsEntity settings) {
        this.settings = settings;
    }
}


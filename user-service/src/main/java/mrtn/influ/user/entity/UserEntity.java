package mrtn.influ.user.entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @Column
    private String email;

    @Column
    private String username;

    @Enumerated(EnumType.STRING)
    private UserType userType;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private SettingsEntity settings = new SettingsEntity();

    public UserEntity() {}

    public UserEntity(String email, String username, UserType userType) {
        this.email = email;
        this.username = username;
        this.userType = userType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public SettingsEntity getSettings() {
        return settings;
    }

    public void setSettings(SettingsEntity settings) {
        this.settings = settings;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity that = (UserEntity) o;
        return Objects.equals(email, that.email) && Objects.equals(username, that.username) && userType == that.userType && Objects.equals(settings, that.settings);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, username, userType, settings);
    }
}


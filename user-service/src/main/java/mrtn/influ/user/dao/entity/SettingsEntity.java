package mrtn.influ.user.dao.entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "settings")
public class SettingsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private boolean emailNotifications = false;

    public SettingsEntity(){}
    public SettingsEntity(boolean emailNotifications) {
        this.emailNotifications = emailNotifications;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isEmailNotifications() {
        return emailNotifications;
    }

    public void setEmailNotifications(boolean emailNotifications) {
        this.emailNotifications = emailNotifications;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        SettingsEntity that = (SettingsEntity) o;
        return emailNotifications == that.emailNotifications;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(emailNotifications);
    }

    @Override
    public String toString() {
        return "SettingsEntity{" +
                "emailNotifications=" + emailNotifications +
                '}';
    }
}

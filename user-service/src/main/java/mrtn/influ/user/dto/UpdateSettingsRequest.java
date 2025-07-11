package mrtn.influ.user.dto;

public class UpdateSettingsRequest {
    private Boolean emailNotifications;

    public Boolean getEmailNotifications() {
        return emailNotifications;
    }

    public void setEmailNotifications(Boolean emailNotifications) {
        this.emailNotifications = emailNotifications;
    }
}

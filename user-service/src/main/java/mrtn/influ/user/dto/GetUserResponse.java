package mrtn.influ.user.dto;

public class GetUserResponse {
    private UserDto user;

    public GetUserResponse(UserDto user) {
        this.user = user;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }
}

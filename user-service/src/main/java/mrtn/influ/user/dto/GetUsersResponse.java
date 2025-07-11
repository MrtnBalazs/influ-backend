package mrtn.influ.user.dto;

import java.util.List;

public class GetUsersResponse {
    List<UserDto> users;

    public GetUsersResponse(List<UserDto> users) {
        this.users = users;
    }

    public List<UserDto> getUsers() {
        return users;
    }

    public void setUsers(List<UserDto> users) {
        this.users = users;
    }
}

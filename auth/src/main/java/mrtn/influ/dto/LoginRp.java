package mrtn.influ.dto;

public class LoginRp {
    private String token;
    public LoginRp (String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

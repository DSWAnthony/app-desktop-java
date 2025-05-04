package model;

public class Usuario {

    private String user;
    private String password;

    public Usuario() {

    }

    public Usuario(String password, String user) {
        this.password = password;
        this.user = user;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

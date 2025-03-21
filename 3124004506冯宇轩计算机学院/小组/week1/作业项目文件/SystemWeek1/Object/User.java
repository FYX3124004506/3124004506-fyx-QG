package SystemWeek1.Object;

public class User {
    private int user_id;//用于关联user和students的id
    private String username;//用户名
    private String password;//密码
    private String email;//电子邮件
    private int role;//角色标识

    public User(int user_id, String username, String password, String email, int role) {
        this.user_id = user_id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
    }

    public User() {
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }
}
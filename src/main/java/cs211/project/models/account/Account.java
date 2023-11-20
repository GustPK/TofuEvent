package cs211.project.models.account;

public class Account {

    private String name;
    private String username;
    private String password;
    private String image;
    private String status;
    private String online;

    public Account(String name, String username, String password, String image, String status, String online) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.image = image;
        this.status = status;
        this.online = online;
    }

    public String getName() { return name; }

    public String getUsername() { return username; }

    public String getPassword() { return password; }

    public String getOnline() {
        return online;
    }

    public void setOnline(String online) {
        this.online = online;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getStatus() { return status; }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String name) {
        this.image = name;
    }
    public boolean checkUsername(String username){
        return this.username.equals(username);
    }
    public boolean checkUserNameAndPassword(String username,String password){
        return this.username.equals(username) && this.password.equals(password);
    }
}

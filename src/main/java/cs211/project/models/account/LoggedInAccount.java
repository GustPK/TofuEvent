package cs211.project.models.account;

public class LoggedInAccount {
    private String username;
    private static LoggedInAccount instance;

    private LoggedInAccount() {

    }

    public static LoggedInAccount getInstance() {
        if (instance == null) {
            instance = new LoggedInAccount();
        }
        return instance;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}

package cs211.project.models.account;

public class LoggedInAccount {
    private Account account;
    private static LoggedInAccount instance;

    private LoggedInAccount() {

    }

    public static LoggedInAccount getInstance() {
        if (instance == null) {
            instance = new LoggedInAccount();
        }
        return instance;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}

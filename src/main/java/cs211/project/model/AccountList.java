package cs211.project.model;

import java.util.ArrayList;

public class AccountList {
    private ArrayList<Account> accountList;

    public AccountList() {
        accountList = new ArrayList<>();
    }

    public ArrayList<Account> getAdminList() {
        return accountList;
    }

    public void addAdmin(String user, String pass) {
        accountList.add(new Account(user, pass));
    }
}

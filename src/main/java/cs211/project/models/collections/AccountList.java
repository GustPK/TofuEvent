package cs211.project.models.collections;

import cs211.project.models.account.Account;

import java.util.ArrayList;

public class AccountList {

    private Account thisAccount;
    private ArrayList<Account> accounts;

    public AccountList() {
        accounts = new ArrayList<>();
    }


    public ArrayList<Account> getAccounts() {
        return accounts;
    }
    public void addAccount(Account account) {
        accounts.add(account);
    }

    public boolean checkLogin(String username,String password) {
        for (Account account : accounts) {
            if (account.getUsername().equals(username) && account.getPassword().equals(password)) {
                thisAccount = account;
                return true;
            }
        }

        return false;
    }
}

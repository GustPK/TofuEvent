package cs211.project.models.collections;

import cs211.project.models.account.Account;
import cs211.project.models.event.Event;
import cs211.project.models.event.Team;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class AccountList {

    private ArrayList<Account> accounts;
    public ArrayList<Account> getAccounts() {
        return accounts;
    }
    public AccountList() {
        accounts = new ArrayList<>();
    }
    public void addAccount(Account account) {
        accounts.add(account);
    }


    public Account checkLogin(String username, String password) {
        for (Account account : accounts) {
            if (account.getUsername().equals(username) && account.getPassword().equals(password)) {
                return account;
            }
        }

        return null;
    }

    public Account findByUsername(String username){
        for (Account account: accounts){
            if (account.getUsername().equals(username))
                    return account;
        }
        return null;
    }
    public void sort(Comparator<Account> cmp) {
        Collections.sort(accounts, cmp);
    }
    public boolean isUsernameUnique(String username) {
        for (Account account : accounts) {
            if (account.getUsername().equals(username)) {
                return false;
            }
        }
        return true;
    }
}

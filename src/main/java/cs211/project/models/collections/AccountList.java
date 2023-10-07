package cs211.project.models.collections;

import cs211.project.models.account.Account;

import java.util.ArrayList;

public class AccountList {

    private Account currentAccount;
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

    public Account getCurrentAccount() { return currentAccount; }

    public Account checkLogin(String username, String password) {
        for (Account account : accounts) {
            if (account.getUsername().equals(username) && account.getPassword().equals(password)) {
                return account;
            }
        }

        return null;
    }
    public Account findByAccountName(String accountName){
        for (Account account: accounts){
            if (account.getUsername().equals(accountName)) {
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
}

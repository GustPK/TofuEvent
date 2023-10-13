package cs211.project.services;

import cs211.project.models.account.Account;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.time.format.DateTimeFormatter;

public class LatestSortComparator implements Comparator<Account> {
    @Override
    public int compare(Account account1, Account account2) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime1 = LocalDateTime.parse(account1.getOnline(), formatter);
        LocalDateTime dateTime2 = LocalDateTime.parse(account2.getOnline(), formatter);
        return dateTime2.compareTo(dateTime1);
    }
}


package cs211.project.services;

import cs211.project.models.account.AccountList;

public interface DataSource <T>{
    T readData();
    void writeData(T data);
}

package cs211.project.services;

public interface Datasource<T>{
    T readData();
    void writeData(T data);
}

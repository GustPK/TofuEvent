package cs211.project.services;

public interface DataSource<T>{
    T readData();
    void writeData(T data);
}

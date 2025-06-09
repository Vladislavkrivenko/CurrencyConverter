package service;

import java.sql.SQLException;
import java.util.List;

public interface CurrenciesService<T> {
    List<T> findAll();

    void save(T t) throws SQLException;

    boolean delete(int id);

    boolean update(T t);

}

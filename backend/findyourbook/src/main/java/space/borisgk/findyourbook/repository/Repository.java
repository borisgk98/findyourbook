package space.borisgk.findyourbook.repository;

import java.util.List;

public interface Repository<T> {

    T get(Integer id);
    T put(T model);
    void delete(Integer id);
    void update(Integer id, T model);

    List<T> findAll();
}

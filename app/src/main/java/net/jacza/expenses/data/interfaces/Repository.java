package net.jacza.expenses.data.interfaces;

import java.util.ArrayList;

/*
 * Interface defining repository methods for the CRUD design pattern.
 */
public interface Repository<T> {
    void create(T obj);
    ArrayList<T> read();
    void update(T obj);
    void delete(T obj);
}

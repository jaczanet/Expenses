package net.jacza.expenses.data.interfaces;

import java.util.ArrayList;

/*
 * Interface defining repository methods for the CRUD design pattern.
 */
public interface Repository<T> {
    public void create(T obj);

    public ArrayList<T> read();

    public void update(T obj);

    public void delete(T obj);
}

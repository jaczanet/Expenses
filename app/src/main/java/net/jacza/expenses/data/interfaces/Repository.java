package net.jacza.expenses.data.interfaces;

import java.util.ArrayList;

/*
 * Interface defining methods of the CRUD design pattern for identifiable objects.
 */
public interface Repository<T extends Identifiable> {
    public void create(T obj);

    public ArrayList<T> read();

    public void update(T obj);

    public void delete(T obj);
}

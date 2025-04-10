package net.jacza.expenses.data.interfaces;

import java.util.ArrayList;

/*
 * Interface defining methods for data sources for storing and loading the data.
 */
public interface DataSource<T> {
    ArrayList<T> load();
    void save(ArrayList<T> list);
}

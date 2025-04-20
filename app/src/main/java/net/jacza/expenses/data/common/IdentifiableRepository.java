package net.jacza.expenses.data.common;

import java.util.ArrayList;
import net.jacza.expenses.data.base.Identifiable;
import net.jacza.expenses.data.base.Repository;

/**
 * Class defining common logic for repositories of Identifiable objects.
 */
public abstract class IdentifiableRepository<T extends Identifiable> implements Repository<T> {

    @Override
    public void create(T entry) {
        var objs = read();
        objs.add(entry);
        write(objs);
    }

    @Override
    public void update(T entry) {
        var objs = read();
        objs.replaceAll(item -> item.getID().equals(entry.getID()) ? entry : item);
        write(objs);
    }

    @Override
    public void delete(T entry) {
        var objs = read();
        objs.removeIf(item -> item.getID().equals(entry.getID()));
        write(objs);
    }

    protected abstract void write(ArrayList<T> objs);
}

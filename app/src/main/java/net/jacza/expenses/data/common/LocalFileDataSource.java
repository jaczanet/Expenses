package net.jacza.expenses.data.common;

import java.io.File;
import java.util.ArrayList;
import net.jacza.expenses.data.base.DataSource;
import net.jacza.expenses.data.util.TextFileHandler;

/*
 * Class defining common logic for data sources that directly write and read its own file.
 */
public abstract class LocalFileDataSource<T> implements DataSource<T> {

    private final TextFileHandler FILE;

    protected LocalFileDataSource(File fileHandle) {
        FILE = new TextFileHandler(fileHandle);
    }

    @Override
    public ArrayList<T> load() {
        var objs = new ArrayList<T>();
        var lines = FILE.readLines();
        for (String line : lines) {
            objs.add(parse(line));
        }
        return objs;
    }

    @Override
    public void save(ArrayList<T> objs) {
        var lines = new ArrayList<String>();
        for (T obj : objs) {
            lines.add(serialize(obj));
        }
        FILE.writeLines(lines);
    }

    protected abstract String serialize(T object);

    protected abstract T parse(String line);
}

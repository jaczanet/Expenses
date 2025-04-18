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
        for (var line : lines) {
            objs.add(parse(line));
        }
        return objs;
    }

    @Override
    public void save(ArrayList<T> objs) {
        var lines = new ArrayList<String>();
        for (var obj : objs) {
            lines.add(serialize(obj));
        }
        FILE.writeLines(lines);
    }

    // rule for creating a string representation of the object
    protected abstract String serialize(T object);

    // rule for converting a string representation back to the object
    protected abstract T parse(String line);
}

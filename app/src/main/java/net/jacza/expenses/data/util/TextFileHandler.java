package net.jacza.expenses.data.util;

import android.util.Log;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Class providing methods for writing and reading a list of strings to and from a file.
 */
public class TextFileHandler {

    private final File FILE_HANDLE;

    public TextFileHandler(File fileHandle) {
        this.FILE_HANDLE = fileHandle;
    }

    public ArrayList<String> readLines() {
        var lines = new ArrayList<String>();
        try (var file = new Scanner(FILE_HANDLE)) {
            while (file.hasNextLine()) {
                lines.add(file.nextLine());
            }
        } catch (java.io.FileNotFoundException exception) {
            Log.i("DATA LAYER", FILE_HANDLE.getName() + " doesn't exist yet.");
        }
        return lines;
    }

    public void writeLines(ArrayList<String> lines) {
        try (var file = new FileWriter(FILE_HANDLE)) {
            for (var line : lines) {
                file.write(line + "\n");
            }
        } catch (java.io.IOException exception) {
            Log.e("DATA LAYER", "IOException while writing to file " + FILE_HANDLE.getName());
        }
    }
}

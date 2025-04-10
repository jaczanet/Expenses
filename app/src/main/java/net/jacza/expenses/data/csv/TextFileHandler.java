package net.jacza.expenses.data.csv;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

/*
 * Abstract class providing methods for writing and reading a list of strings to and from a txt-compatible file.
 */
abstract class TextFileHandler {

    private final File FILE_HANDLE;

    protected TextFileHandler(File fileHandle) {
        this.FILE_HANDLE = fileHandle;
    }

    protected ArrayList<String> readLines() {
        ArrayList<String> lines = new ArrayList<>();
        try (Scanner file = new Scanner(FILE_HANDLE)) {
            while (file.hasNextLine()) {
                lines.add(file.nextLine());
            }
        } catch (java.io.FileNotFoundException exception) {}
        return lines;
    }

    protected void writeLines(ArrayList<String> lines) {
        try (FileWriter file = new FileWriter(FILE_HANDLE)) {
            for (String line : lines) {
                file.write(line + "\n");
            }
        } catch (java.io.IOException exception) {}
    }
}

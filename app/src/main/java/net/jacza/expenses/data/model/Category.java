package net.jacza.expenses.data.model;

import java.util.UUID;
import net.jacza.expenses.data.interfaces.Identifiable;

/*
 * // TODO JacaDOC
 */
public class Category implements Identifiable {

    private final UUID ID;
    private String name;

    public Category(UUID ID, String name) {
        this.ID = UUID.randomUUID();
        this.name = name;
    }

    public Category(String name) {
        this.ID = UUID.randomUUID();
        this.name = name;
    }

    @Override
    public UUID getID() {
        return ID;
    }

    public String getName() {
        return name;
    }
}

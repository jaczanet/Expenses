package net.jacza.expenses.data.model;

import java.io.Serializable;
import java.util.UUID;
import net.jacza.expenses.data.base.Identifiable;

/*
 * // TODO JavaDOC
 */
public class Category implements Identifiable, Serializable {

    private final UUID ID;
    private String name;

    public Category(UUID ID, String name) {
        this.ID = ID;
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

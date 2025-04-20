package net.jacza.expenses.data.model;

import java.io.Serializable;
import java.util.UUID;
import net.jacza.expenses.data.base.Identifiable;

/**
 * Data class wrapping category data.
 */
public class Category implements Identifiable, Serializable {

    // attributes

    private final UUID ID;
    private String name;

    // constructors

    public Category(String name) {
        this(UUID.randomUUID(), name);
    }

    public Category(UUID ID, String name) {
        this.ID = ID;
        this.name = name != null ? name : "Category";
    }

    // getters

    @Override
    public UUID getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    // setters

    public void setName(String name) {
        this.name = name;
    }
}

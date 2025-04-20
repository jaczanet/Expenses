package net.jacza.expenses.data.raw;

import java.util.UUID;
import net.jacza.expenses.data.model.Category;

/**
 * Immutable object exposed by the data source layer, providing a Raw version of Category.
 */
public class RawCategory {

    // attributes

    private final UUID ID;
    private final String NAME;

    // constructors

    public RawCategory(UUID ID, String NAME) {
        this.ID = ID;
        this.NAME = NAME;
    }

    // factory methods
    public static RawCategory fromCategory(Category category) {
        return new RawCategory(category.getID(), category.getName());
    }

    // getters

    public UUID getID() {
        return ID;
    }

    public String getNAME() {
        return NAME;
    }
}

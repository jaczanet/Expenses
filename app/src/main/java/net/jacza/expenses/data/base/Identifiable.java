package net.jacza.expenses.data.base;

import java.util.UUID;

/**
 * Interface defining methods to identify an object by its UUID.
 */
public interface Identifiable {
    UUID getID();
}

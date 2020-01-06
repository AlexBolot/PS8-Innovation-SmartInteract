package fr.polytech.pnsinnov.smartinteract.model;

import java.util.Arrays;
import java.util.function.Consumer;

public enum SpecificCategory {
    WHY,
    WHERE,
    WHAT,
    WHEN,
    WHO,
    HOW,
    HOWMANY;

    public static void forEach(Consumer<SpecificCategory> action) {
        Arrays.stream(SpecificCategory.values()).forEach(action);
    }

    public static SpecificCategory fromName(String name) {
        for (SpecificCategory category : SpecificCategory.values()) {
            if (category.name().equalsIgnoreCase(name))
                return category;
        }

        throw new IllegalArgumentException("No SpecificCategory matches \"" + name + "\"");
    }
}

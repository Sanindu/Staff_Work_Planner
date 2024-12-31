package org.example;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ActivityType  implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private List<String> subtypes;

    public ActivityType(String name) {
        this.name = name;
        this.subtypes = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public List<String> getSubtypes() {
        return subtypes;
    }

    public void addSubtype(String subtype) {
        subtypes.add(subtype);
    }

    public void removeSubtype(String subtype) {
        subtypes.remove(subtype);
    }

    @Override
    public String toString() {
        return "ActivityType{name='" + name + "', subtypes=" + subtypes + "}";
    }
}

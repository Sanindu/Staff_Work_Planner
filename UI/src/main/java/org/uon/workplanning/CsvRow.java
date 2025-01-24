package org.uon.workplanning;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.HashMap;
import java.util.Map;

public class CsvRow {
    private Map<String, StringProperty> columns = new HashMap<>();

    public void addColumn(String name, String value) {
        columns.put(name, new SimpleStringProperty(value));
    }

    public StringProperty getColumn(String name) {
        return columns.get(name);
    }
}

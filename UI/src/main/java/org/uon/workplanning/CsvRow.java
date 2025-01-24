package org.uon.workplanning;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.HashMap;
import java.util.Map;

public class CsvRow {
    private Map<String, StringProperty> columns = new HashMap<>();

    // Method to add a new column to the row
    public void addColumn(String name, String value) {
        columns.put(name, new SimpleStringProperty(value));
    }

    // Method to get the value of a column by its name
    public StringProperty getColumn(String name) {
        return columns.get(name);
    }
}

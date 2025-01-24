package org.uon.workplanning;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigLoader {
    private static final Properties properties = new Properties();

    static {
        try (FileInputStream input = new FileInputStream("config.properties")) {
            // Load the properties from the configuration file
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
            // Throw a runtime exception if the configuration file fails to load
            throw new RuntimeException("Failed to load configuration file.");
        }
    }

    // Method to retrieve the value of the "TS_WEIGHT" property from the configuration file
    public static String getTSValue() {
        return properties.getProperty("TS_WEIGHT");
    }
}

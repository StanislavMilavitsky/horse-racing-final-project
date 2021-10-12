package by.milavitsky.horseracing.util;

import java.io.IOException;
import java.util.Properties;

public final class PropertiesUtil {
    /**
     * make singlton for properties file
     */
    private static final Properties PROPERTIES = new Properties();

    static {
        loadProperties();
    }

    private PropertiesUtil() {
    }
/**
 * Get parameters from PROPERTIES object
 *   */
    public static String get(String key) {
        return PROPERTIES.getProperty(key);
    }
/**
 * connect with file properties and load parameters in PROPERTIES object
 * */
    private static void loadProperties() {
        try (var inputStream = PropertiesUtil.class.getClassLoader().getResourceAsStream("application.properties")) {
            PROPERTIES.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }


}

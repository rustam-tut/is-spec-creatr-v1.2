package is.infostms.isc.util;

import java.io.*;

import java.util.*;
import java.util.stream.Collectors;

public class PropertiesLoader {

    private final static Properties properties;

    static {
        properties = new Properties();
        try (InputStream is = PropertiesLoader.class.getClassLoader().getResourceAsStream("static.properties")){
            properties.load(is);
        } catch (IOException ignored) {
        }
    }

    public static String getString(String key) {
        return properties.getProperty(key);
    }

    public static int getInt(String key) {
        return Integer.parseInt(properties.getProperty(key));
    }

    public static Set<String> getStrSet(String[] keys) {
        return Arrays.stream(keys).map(properties::getProperty).collect(Collectors.toSet());
    }


}

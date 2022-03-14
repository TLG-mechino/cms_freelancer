package vn.compedia.website.properties;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@SuppressWarnings("unchecked")
public class EnvironmentProperties {

    private static Map<String, String> data = new HashMap<String, String>();

    static {
        Properties properties = new Properties();
        try {
            properties.load(MasterDataProperties.class.getResourceAsStream("/environment.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Enumeration<String> enumeration = (Enumeration<String>) properties.propertyNames();
        while (enumeration.hasMoreElements()) {
            String key = enumeration.nextElement();
            data.put(key, properties.getProperty(key));
        }
    }

    public static String getData(String key) {
        if (data.containsKey(key)) {
            return data.get(key);
        }
        return "";
    }
}

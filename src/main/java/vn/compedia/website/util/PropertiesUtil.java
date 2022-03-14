package vn.compedia.website.util;

import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
@PropertySource("classpath:application.properties")
public class PropertiesUtil {
    private static Properties props;
    private static Properties emailProps;

    public static String getProperty(String name) {
        try {
            if (props == null) {
                props = new Properties();
                props.load(PropertiesUtil.class.getResourceAsStream("/application.properties"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return props.getProperty(name);
    }

    public static String getEmailProperty(String name) {
        try {
            if (emailProps == null) {
                emailProps = new Properties();
                emailProps.load(PropertiesUtil.class.getResourceAsStream("/email.properties"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return emailProps.getProperty(name);
    }

}

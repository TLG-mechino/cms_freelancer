package vn.compedia.website.util;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.Properties;

@Configuration
@PropertySource("classpath:application.properties")
public class PropertiesUtil {
    private static Properties props;
    private static Properties evnProps;
    private static Properties emailProps;
    private static Properties vnLangProps;
    private static Properties enLangProps;
    private static Properties zhLangProps;

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

    public static String getMessage(String name, int localeId) {
//        if (localeId == Constant.LANGUAGE_CODE_VN) {
//            return getVnMessage(name);
//        }
//        if (localeId == Constant.LANGUAGE_CODE_EN) {
//            return getEnMessage(name);
//        }
        return getZhMessage(name);
    }

    public static String getVnMessage(String name) {
        try {
            if (vnLangProps == null) {
                vnLangProps = new Properties();
                vnLangProps.load(PropertiesUtil.class.getResourceAsStream("/Messages_vi.properties"));
                Properties tmp = new Properties();
                tmp.load(PropertiesUtil.class.getResourceAsStream("/Text_vi.properties"));
                vnLangProps.putAll(tmp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return vnLangProps.getProperty(name);
    }

    public static String getEnMessage(String name) {
        try {
            if (enLangProps == null) {
                enLangProps = new Properties();
                enLangProps.load(PropertiesUtil.class.getResourceAsStream("/Messages_en.properties"));
                Properties tmp = new Properties();
                tmp.load(PropertiesUtil.class.getResourceAsStream("/Text_en.properties"));
                enLangProps.putAll(tmp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return enLangProps.getProperty(name);
    }

    public static String getZhMessage(String name) {
        try {
            if (zhLangProps == null) {
                zhLangProps = new Properties();
                zhLangProps.load(PropertiesUtil.class.getResourceAsStream("/Messages_zh.properties"));
                Properties tmp = new Properties();
                tmp.load(PropertiesUtil.class.getResourceAsStream("/Text_zh.properties"));
                zhLangProps.putAll(tmp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return zhLangProps.getProperty(name);
    }

    public static String getPropertyEnvironment(String name) {
        try {
            if (evnProps == null) {
                evnProps = new Properties();
                evnProps.load(PropertiesUtil.class.getResourceAsStream("/environment.properties"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return evnProps.getProperty(name);
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

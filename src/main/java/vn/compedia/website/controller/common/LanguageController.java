package vn.compedia.website.controller.common;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Scope;
import vn.compedia.website.util.Constant;

import javax.annotation.PostConstruct;
import javax.inject.Named;
import java.io.Serializable;
import java.util.*;

@Named
@Scope(value = "session")
@Getter
@Setter
public class LanguageController implements Serializable {

    private static final long serialVersionUID = 8575412082953212040L;
    public static String LOCALE_VI = "vi";
    private Locale currentLocale = new Locale("vi");
    private Map<String, String> errorMap;
    private Map<String, String> textMap;

    @PostConstruct
    public void init() {
        onChange(LOCALE_VI);
    }

    public void onChange(String locale) {
        currentLocale = new Locale(locale);

        textMap = loadData(Constant.TEXT_MESSAGE, currentLocale);
        errorMap = loadData(Constant.ERROR_MESSAGE, currentLocale);
    }

    public Map<String, String> loadData(String baseName, Locale locale) {
        Map<String, String> messageMap = new HashMap<>();
        ResourceBundle resourceBundle = ResourceBundle.getBundle(baseName, locale);
        Enumeration<String> enumeration = resourceBundle.getKeys();
        while (enumeration.hasMoreElements()) {
            String key = enumeration.nextElement();
            String value = resourceBundle.getString(key);
            messageMap.put(key, value);
        }
        return messageMap;
    }
}

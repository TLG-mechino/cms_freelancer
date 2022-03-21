package vn.compedia.website.util;

public class Constant {

    // Common
    public static final int STATUS_OK = 0;
    public static final int MAX_FILE_SIZE = 50000000;
    public static final String TEXT_MESSAGE = "Text";
    public static final String ENCODING_UTF8 = "UTF-8";
    public static final String ERROR_MESSAGE = "Error";
    public static final String ERROR_GROWL_ID = "growl";
    public static final String ERROR_MESSAGE_ID = "errorMsgDialog";
    public static final String TIME_ZONE_DEFAULT = "Asia/Ho_Chi_Minh";

    // Cookie
    public static final String COOKIE_ACCOUNT = "_JCA";
    public static final String COOKIE_PASSWORD = "_JCP";

    // Role id
    public static final int NOT_LOGIN_ID = 0;
    public static final int LOGIN_ID = 1;

    // Regex
    public static String EMAIL_PATTERN = "^(?=.{1,64}@)[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*@"
            + "[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    public static String PHONE_PATTERN = "(84|0[3|5|7|8|9])+([0-9]{8})\\b";

    // Id screen
    public static final String DASHBOARD = "dashboard";
    public static final String MN_COMPLAIN = "mn-complain";
    public static final String MN_ACCOUNT = "mn-account";
    public static final String MN_HASTAG = "mn-hashtag";
    public static final String MN_USER = "mn-user";
    public static final String MN_TRANSACTION = "mn-transaction";
    public static final String SYS_CONFIG = "sys-config";
    public static final String SERVICE_CONFIG = "service-config";

}

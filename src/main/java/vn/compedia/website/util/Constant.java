package vn.compedia.website.util;

public class Constant {

    // Common
    public static final int STATUS_OK = 0;
    public static final int FIXED_ROW = 5;
    public static final int MAX_FILE_SIZE = 50000000;
    public static final String TEXT_MESSAGE = "Text";
    public static final String ENCODING_UTF8 = "UTF-8";
    public static final String ERROR_MESSAGE = "Error";
    public static final String ERROR_GROWL_ID = "growl";
    public static final String ERROR_MESSAGE_ID = "errorMsgDialog";
    public static final String TIME_ZONE_DEFAULT = "Asia/Ho_Chi_Minh";
    public static final String NO_IMAGE_URL = "/images/no-image.png";
    public static final String REPORT_EXPORT_TRANSACTION = "/WEB-INF/report/TransactionReport.xlsx";
    public static final String TEMPLATE_EXPORT_TRANSACTION = "/WEB-INF/template/TransactionExport.xlsx";

    // Cookie
    public static final String COOKIE_ACCOUNT = "_JCA";
    public static final String COOKIE_PASSWORD = "_JCP";

    // Role id
    public static final int NOT_LOGIN_ID = 0;
    public static final int LOGIN_ID = 1;

    // Regex
    public static String EMAIL_PATTERN = "^(?=.{1,64}@)[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*@" + "[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    public static String PHONE_PATTERN = "(84|0[3|5|7|8|9])+([0-9]{8})\\b";
    public static String FULL_NAME_PATTERN = "^[a-zA-ZÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼỀỀỂẾưăạảấầẩẫậắằẳẵặẹẻẽềềểếỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễệỉịọỏốồổỗộớờởỡợụủứừỬỮỰỲỴÝỶỸửữựỳỵỷỹ\\s|_]+$";
    public static String LINK_FACEBOOK_PATTERN = "((http|https)://)?(www[.])?facebook.com/.+";

    // Id screen
    public static final String DASHBOARD = "dashboard";
    public static final String MN_COMPLAIN = "mn-complain";
    public static final String MN_ACCOUNT = "mn-account";
    public static final String MN_HASHTAG = "mn-hashtag";
    public static final String MN_USER = "mn-user";
    public static final String MN_TRANSACTION = "mn-transaction";
    public static final String MN_TEST = "mn-test";
    public static final String MN_JOB = "mn-job";
    public static final String MN_JOB_RECIPIENT = "mn-job-recipient";
    public static final String MN_REVIEW = "mn-review";
    public static final String MN_POST = "mn-post";
    public static final String MN_CUSTOMER_TALK = "mn-customer-talk";
    public static final String MN_PACKAGE_SERVICE = "mn-package-service";
    public static final String MN_TRANSACTION_USER = "mn-transaction-user";
    public static final String MN_NOTIFICATION = "mn-notification";
    public static final String MN_TEST_EVALUATE = "mn-valuate-test";
    public static final String SYS_CONFIG = "sys-config";
    public static final String SERVICE_CONFIG = "service-config";
    public static final String STICKERS_CONFIG = "stickers-config";
}

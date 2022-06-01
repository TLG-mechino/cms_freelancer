package vn.compedia.website.util;

public class DbConstant {

    public static final int DELETE_STATUS = -1;
    public static final int INACTIVE_STATUS = 0;
    public static final int ACTIVE_STATUS = 1;
    public static final int BLOCKED_STATUS = 2;

    // Default role
    public static final String DEFAULT_ROLE = "Admin";

    // First login
    public static final int FIRST_LOGIN = 1;
    public static final int NOT_FIRST_LOGIN = 2;

    // Type Account
    public static final int ACCOUNT_CMS = 2;
    public static final int ACCOUNT_USER = 1;

    //Evaluate Score
    public static final int COMPLETE_EVALUATE_SCORE = 3;

    //Status Evaluate Test
    public static final int TESTING = 1;
    public static final int TESTED = 2;
    public static final int EVALUATED  = 3;

    //Job
    public static final int DELETE_JOB = -1;
    public static final int INACTIVE_JOB = 0;
    public static final int ACTIVE_JOB = 1;
    public static final int WORKING_JOB = 2;
    public static final int COMPLETE_JOB = 3;

    public static final String SUCCESS = "Success";
    public static final String FAIL = "Fail";

}

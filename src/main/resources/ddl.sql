create table account
(
    ACCOUNT_ID  int auto_increment
        primary key,
    USERNAME    varchar(20)                not null,
    EMAIL       varchar(50)                not null,
    PASSWORD    varchar(200)               not null,
    SALT        varchar(10)                null,
    STATUS      int(1)   default 0         null,
    FIRST_LOGIN int(1)   default 2         null comment '1:ĐÃ LOGIN;2:CHƯA LOGIN',
    CREATE_DATE datetime default curdate() null,
    UPDATE_BY   varchar(20)                null,
    UPDATE_DATE datetime                   null,
    CREATE_BY   varchar(20)                null,
    FULL_NAME   varchar(20)                null,
    PHONE       varchar(11)                null
);

create table bidders
(
    BIDDERS_ID  int auto_increment
        primary key,
    USERNAME    int              null,
    JOB_ID      int              null,
    MONEY       double           null,
    BIDING_TIME datetime         null,
    STATUS      int(1)           null,
    ACCEPTED    int(1) default 2 null comment '1: ĐẤU THẦU THÀNH CÔNG
2: ĐẤU THẦU THẤT BẠI'
)
    comment 'THÔNG TIN VỀ NGƯỜI ĐẤU GIÁ DỰ ÁN';

create table certificate
(
    CERTIFICATE_ID int auto_increment
        primary key,
    CONTENT        varchar(100) null,
    USERNAME       varchar(20)  null
)
    comment 'THÔNG TIN CHỨNG CHỈ';

create table comment
(
    COMMENT_ID   int auto_increment
        primary key,
    POST_ID      int                        null,
    USERNAME     varchar(20)                null comment 'NGƯỜI ĐĂNG',
    COMMENT_TIME datetime default curdate() null,
    PARENT_ID    int                        null,
    CONTENT      text                       null comment 'NỘI DUNG BÌNH LUẬN',
    STATUS       int(1)                     null
)
    comment 'BÌNH LUẬN';

create table complain
(
    COMPLAIN      int auto_increment
        primary key,
    USERNAME      varchar(20)                null comment 'NGƯỜI KHIẾU LẠI',
    TITLE         varchar(100)               null,
    CONTENT       text                       null,
    NOTE          varchar(100)               null,
    COMPLAIN_TYPE int(1)                     null,
    STATUS        int(1)                     null,
    CREATE_TIME   datetime default curdate() null,
    UPDATE_TIME   datetime                   null,
    OBJECT_ID     int                        null,
    TYPE          int(1)                     null
);

create table complain_file
(
    COMPLAIN_FILE_ID int auto_increment
        primary key,
    FILE_NAME        varchar(200) null,
    FILE_PATH        varchar(200) null,
    FILE_TYPE        int          null,
    COMPLAIN_ID      int          null
);

create table exam
(
    EXAM_ID        int auto_increment
        primary key,
    CODE           varchar(20)                null,
    TITLE_VN       varchar(200)               null,
    TITLE_EN       varchar(200)               null,
    DESCRIPTION_VN text                       null,
    DESCRIPTION_EN text                       null,
    CONTENT_VN     text                       null,
    CONTENT_EN     text                       null,
    SCORE          double                     null,
    EXAM_TYPE_ID   int(1)                     null,
    STATUS         int                        null,
    CREATE_BY      varchar(20)                null,
    CREATE_TIME    datetime default curdate() null,
    UPDATE_BY      varchar(20)                null,
    UPDATE_DATE    datetime                   null,
    HASHTAG_ID     int                        null
)
    comment 'ĐỀ THI ';

create table exam_file
(
    EXAM_FILE_ID int auto_increment
        primary key,
    FILE_NAME    varchar(200) null,
    FILE_PATH    varchar(200) null,
    FILE_TYPE    varchar(10)  null,
    OBJECT_ID    int          null,
    TYPE         int(1)       null comment '1:EXAM_ID
2:USER_EXAM_ID'
);

create table exam_type
(
    EXAM_TYPE_ID int auto_increment
        primary key,
    CODE         varchar(20) null,
    NAME         varchar(50) null,
    STATUS       int(1)      null
)
    comment 'KIỂU BÀI THI';

create table hashtag
(
    HASHTAG_ID     int auto_increment
        primary key,
    CODE           varchar(20)                null,
    TITLE_VN       varchar(20)                null,
    TITLE_EN       varchar(20)                null,
    DESCRIPTION_VN varchar(200)               null,
    DESCRIPTION_EN varchar(200)               null,
    STATUS         int(1)                     null,
    CREATE_BY      varchar(20)                null,
    CREATE_DATE    datetime default curdate() null,
    UPDATE_BY      varchar(20)                null,
    UPDATE_DATE    datetime                   null
);

create table hashtag_detail
(
    HASHTAG_DETAIL_ID int auto_increment
        primary key,
    OBJECT_ID         int    null,
    HASHTAG_ID        int    null,
    TYPE              int(1) null comment '1: USER_ID
2: JOB_ID',
    TESTED            int(1) null comment '1: LÀ ĐÃ ĐƯỢC KIỂM ĐỊNH
2: CHƯA ĐƯỢC KIỂM ĐỊNH
CHÚ Ý: NẾU CHƯA ĐƯỢC KIỂM ĐỊNH THÌ SẼ LÀ KĨ NĂNG',
    STATUS            int(1) null
);

create table job
(
    JOB_ID      int auto_increment
        primary key,
    CODE        varchar(20)  null,
    NAME        varchar(200) null,
    DESCRIPTION text         null,
    USERNAME    varchar(20)  null comment 'NGƯỜI ĐĂNG JOB',
    START_TIME  datetime     null,
    END_TIME    datetime     null,
    MONEY_FROM  double       null,
    MONEY_TO    double       null,
    CREATE_DATE datetime     null,
    UPDATE_DATE datetime     null
)
    comment 'THÔNG TIN VỀ DỰ ÁN';

create table link_detail
(
    LIKE_DETAIL_ID int auto_increment
        primary key,
    OBJECT_ID      int         null,
    USERNAME       varchar(20) null comment 'NGƯỜI THÍCH',
    TYPE           int(1)      null comment '1: LƯỢT THÍCH CỦA BÀI ĐĂNG(POST)
2: LƯỢT THÍCH CỦA BÌNH LUẬN(COMMENT)',
    STATUS         int(1)      null
)
    comment 'CHI TIẾT THÍCH';

create table massage
(
    MASSAGE_ID       int auto_increment
        primary key,
    CONVERSATIONS_ID int                        null,
    USERNAME         varchar(20)                null,
    READ_BY          text                       null comment 'LÀ DANH SÁCH ID NGƯỜI ĐÃ ĐỌC KHÔNG BAO GỒM MÌNH
EXAMPLE: ["1","2"]',
    CONTENT          text                       null,
    CREATE_TIME      datetime default curdate() null,
    ATTACHMENT       text                       null
)
    comment 'THÔNG TIN TIN NHẮN';

create table message_file
(
    MESSAGE_FILE_ID int auto_increment
        primary key,
    FILE_NAME       varchar(200) null,
    FILE_PATH       varchar(200) null,
    FILE_TYPE       varchar(10)  null,
    MESSAGE_ID      int          null
);

create table notification
(
    NOTIFICATION_ID int auto_increment
        primary key,
    CONTENT         varchar(100)               null,
    SENDING_TIME    datetime default curdate() null,
    USERNAME        varchar(20)                null comment 'NGƯỜI GỬI THÔNG BÁO',
    STATUS          int(1)                     null
)
    comment 'THÔNG BÁO';

create table notification_ref
(
    NOTIFICATION_REF_ID int auto_increment
        primary key,
    NOTIFICATION_ID     int         null,
    USERNAME            varchar(20) null comment 'NGƯỜI NHẬN',
    STATUS              int(1)      null
);

create table package_service
(
    PACKAGE_SERVICE_ID int auto_increment
        primary key,
    NAME               varchar(100)               null,
    CODE               varchar(20)                null,
    MONEY              double                     null,
    DESCRIPTION        text                       null,
    STATUS             int(1)                     null,
    USERNAME           varchar(20)                null,
    CREATE_DATE        datetime default curdate() null,
    UPDATE_DATE        datetime                   null,
    UPDATE_BY          varchar(20)                null,
    SERVICE_TYPE_ID    int                        null
);

create table payment_type
(
    PAYMENT_TYPE_ID int auto_increment
        primary key,
    CODE            varchar(20) null,
    NAME            varchar(50) null,
    STATUS          int(1)      null
);

create table post
(
    POST_ID       int auto_increment
        primary key,
    CONTENT       text                       null,
    USERNAME      varchar(20)                null comment 'NGƯỜI ĐĂNG',
    POSTING_TIME  datetime default curdate() null,
    BLOCK_COMMENT int(1)   default 1         null comment '1: KHÔNG BLOCK; 2: CÓ BLOCK',
    STATUS        int(1)                     null
)
    comment 'BÀI ĐĂNG';

create table post_file
(
    POST_FILE_ID int auto_increment
        primary key,
    POST_ID      int          null,
    FILE_PATH    varchar(200) null,
    FILE_TYPE    varchar(10)  null,
    FILE_NAME    varchar(100) null
);

create table register_package
(
    REGISTER_PACKAGE_ID int auto_increment
        primary key,
    USERNAME            varchar(20) null,
    REGISTRATION_TIME   date        null comment 'THỜI GIAN ĐĂNG KÍ',
    EXPIRED_TIME        date        null comment 'THỜI GIAN HÊT HẠN',
    MONEY               double      null
)
    comment 'THÔNG TIN ĐĂNG KÍ GÓI DỊCH VỤ';

create table review
(
    REVIEW_ID   int auto_increment
        primary key,
    USERNAME    varchar(20)  null comment 'NGƯỜI ĐÁNH GIÁ',
    JOB_ID      int          null,
    CONTENT     varchar(200) null,
    STAR_AMOUNT int(1)       null,
    STATUS      int(1)       null,
    REVIEW_TIME datetime     null comment 'THỜI GIAN ĐÁNH GIÁ'
)
    comment 'ĐÁNH GIÁ';

create table service_type
(
    SERVICE_TYPE_ID int auto_increment
        primary key,
    CODE            varchar(20)  null,
    NAME            varchar(100) null,
    STATUS          int(1)       null
);

create table time_type
(
    TIME_TYPE_ID int         null,
    CODE         varchar(20) null,
    TITLE        varchar(20) null,
    STATUS       int(1)      null
);

create table transaction
(
    TRANSACTION      int auto_increment
        primary key,
    SENDER           varchar(20)                null,
    RECIPIENT        varchar(20)                null,
    TRANSACTION_TIME datetime default curdate() null,
    AMOUNT_OF_MONEY  double                     null,
    DISCOUNT_MONEY   double                     null,
    FINAL_MONEY      double                     null,
    CODE             varchar(20)                null,
    PAYMENT_TYPE_ID  int                        null,
    STATUS           int(1)                     null
)
    comment 'THÔNG TIN GIAO DỊCH';

create table user
(
    USERNAME          varchar(20)      not null
        primary key,
    IMAGE_PATH        varchar(200)     null,
    ADDRESS           varchar(100)     null,
    TOTAL_SCORE       int    default 0 null comment 'TỔNG SỐ ĐIỂM ĐẠT ĐƯỢC QUA CÁC BÀI KIỂM TRA',
    MONEY_WALLET      int    default 0 null comment 'TỔNG TIỀN TRONG VÍ',
    PROVINCE_ID       int              null,
    DISTRICT_ID       int              null,
    COMMUNE_ID        int              null,
    EXPERIENCE_AMOUNT int(2) default 0 null comment 'SỐ NĂM KINH NGHIỆM',
    RENT_COST         int    default 0 null comment 'GIÁ TIỀN ĐỂ THUÊ',
    WORKING_HOURS     int(5) default 0 null comment 'SỐ GIỜ LÀM VIỆC TỐI THIỂU',
    TIME_TYPE_ID      int(1)           null,
    FACEBOOK_LINK     varchar(200)     null,
    TYPE_LOGIN        varchar(20)      null comment 'KIỂU LOGIN'
)
    comment 'THÔNG TIN NGƯỜI DÙNG';

create table user_exam
(
    USER_EXAM_ID int auto_increment
        primary key,
    USERNAME     varchar(20)                null,
    EXAM_ID      int                        null,
    SUBMIT_TIME  datetime default curdate() null,
    SCORE        double                     null,
    NOTE         varchar(100)               null
);


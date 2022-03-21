package vn.compedia.website.util;


import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class DateUtil {

    public static final String HHMM = "HH:mm";
    public static final String HHMMSS = "HH:mm:ss";
    public static final String MMDDYYYY = "MM/dd/yyyy";
    public static final String DDMMYYYY = "dd/MM/yyyy";
    public static final String YYYYMMDD = "yyyy-MM-dd";
    public static final String YYYYMMDD_FOLDER = "yyyyMMdd";
    public static final String TIME_FORMAT = "01/01/1970 HH:mm:00";
    public static final String YYYYMMDD_DateTime = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_FORMAT = "dd/MM/yyyy HH:mm:ss";
    public static final String TO_DATE_FORMAT = "dd/MM/yyyy 23:59:59";
    public static final String MMDDYYYYHHMMSS = "MM-dd-yyyy-HH-mm-ss";
    public static final String FROM_DATE_FORMAT = "dd/MM/yyyy 00:00:00";
    public static final String DATE_FORMAT_UPLOAD = "ddMMyyyyHHmmssSSS";
    public static final String FROM_DATE_FORMAT_SQL = "dd-MMM-yyyy 00:00:00";
    public static final String TO_DATE_FORMAT_SQL = "dd-MMM-yyyy 23:59:59";
    public static final String FROM_DATE_FORMAT_ORACLE = "yyyy-MM-dd 00:00:00";
    public static final String TO_DATE_FORMAT_ORACLE = "yyyy-MM-dd 23:59:59";
    public static final String DATE_FORMAT_FILE_EXPORT = "dd_MM_yyyy_HH_mm_ss";
    public static final String YEAR = "yyyy";
    public static final String MonthYear = "MM/yyyy";
    public static final String MONTH = "MM";
    public static final SimpleDateFormat cmdateFormat = new SimpleDateFormat(DATE_FORMAT);
    public static final String regexDate = "(^(((0[1-9]|1[0-9]|2[0-8])[\\/](0[1-9]|1[012]))|((29|30|31)[\\/](0[13578]|1[02]))|((29|30)[\\/](0[4,6,9]|11)))[\\/](19|[2-9][0-9])\\d\\d$)|(^29[\\/]02[\\/](19|[2-9][0-9])(00|04|08|12|16|20|24|28|32|36|40|44|48|52|56|60|64|68|72|76|80|84|88|92|96)$)";
    public static final String regexTime = "^([0-1]?[0-9]|2[0-3]):[0-5][0-9]$";

    public static Date formatToDate(Date toDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(TO_DATE_FORMAT);
        String strDate = dateFormat.format(toDate);
        try {
            return dateFormat.parse(strDate);
        } catch (ParseException e) {
            return null;
        }
    }

    public static Date formatDate(Date toDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(FROM_DATE_FORMAT);
        String strDate = dateFormat.format(toDate);
        try {
            return dateFormat.parse(strDate);
        } catch (ParseException e) {
            return null;
        }
    }

    public static Date endDate(Date toDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(TO_DATE_FORMAT);
        String strDate = dateFormat.format(toDate);
        try {
            return dateFormat.parse(strDate);
        } catch (ParseException e) {
            return null;
        }
    }

    public static String getNowDate() {
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat(MMDDYYYY);
        String date = dateFormat.format(now);

        SimpleDateFormat hhFormat = new SimpleDateFormat(HHMMSS);
        String hh = hhFormat.format(now);
        String result = LocalTime.parse(hh, DateTimeFormatter.ofPattern("HH:mm:ss")).format(DateTimeFormatter.ofPattern("hh:mm:ss a"));
        return date + ", " + result;
    }

    public static Date convertToDateViaInstant(LocalDateTime dateToConvert) {
        return java.util.Date
                .from(dateToConvert.atZone(ZoneId.systemDefault())
                        .toInstant());
    }

    public static LocalDateTime convertToLocalDateTimeViaInstant(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }

    public static String formatDatePattern(Date date, String pattern) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
            return dateFormat.format(date);
        } catch (Exception e) {
            return null;
        }
    }

    public static Date formatFromDate(Date toDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(FROM_DATE_FORMAT);
        String strDate = dateFormat.format(toDate);
        try {
            return dateFormat.parse(strDate);
        } catch (ParseException e) {
            return null;
        }
    }

    public static LocalDateTime formatToLocalDateTime(String date, String pattern) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
            return LocalDateTime.parse(date, formatter);
        } catch (Exception e) {
            return null;
        }
    }

    public static String formatToPattern(LocalDateTime date, String pattern) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
            return dateFormat.format(date);
        } catch (Exception e) {
            return null;
        }
    }

    public static Date formatDate(Date date, String pattern) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        String strDate = dateFormat.format(date);
        try {
            return dateFormat.parse(strDate);
        } catch (ParseException e) {
            return null;
        }
    }

    public static Date formatDateToPattern(Date toDate, String pattern) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        String strDate = dateFormat.format(toDate);
        try {
            return cmdateFormat.parse(strDate);
        } catch (ParseException e) {
            return null;
        }
    }

    public static Date startOfDate(Date toDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(FROM_DATE_FORMAT);
        String strDate = dateFormat.format(toDate);
        try {
            return cmdateFormat.parse(strDate);
        } catch (ParseException e) {
            return null;
        }
    }

    public static Date endOfDate(Date toDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(TO_DATE_FORMAT);
        String strDate = dateFormat.format(toDate);
        try {
            return cmdateFormat.parse(strDate);
        } catch (ParseException e) {
            return null;
        }
    }

    public static String getCurrentDateStr() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT_UPLOAD);
        return dateFormat.format(new Date());
    }

    public static String generateDateExport() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT_FILE_EXPORT);
        return dateFormat.format(new Date());
    }

    public static String getTodayFolder() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(YYYYMMDD_FOLDER);
        return dateFormat.format(new Date());
    }

    public static Date formatDatePattern(String date, String format) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(format);
            return dateFormat.parse(date);
        } catch (Exception e) {
            return null;
        }
    }

    public static Date parseDatePattern(Date date, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        String strDate = dateFormat.format(date);
        try {
            return dateFormat.parse(strDate);
        } catch (ParseException e) {
            return null;
        }
    }

    public static String setMinute(Date date, int minus) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(MMDDYYYYHHMMSS);
        Date temp = new Date();
        temp.setTime(date.getTime() + minus * 60 * 1000);
        return dateFormat.format(temp);
    }

    public static Date plusDay(Date date, int day) {
        return plusHour(date, day * 24);
    }

    public static Date minusDay(Date date, int day) {
        return minusHour(date, day * 24);
    }

    public static Date plusHour(Date date, int hour) {
        return plusMinute(date, hour * 60);
    }

    public static Date minusHour(Date date, int hour) {
        return minusMinute(date, hour * 60);
    }

    public static Date plusMinute(Date date, int minus) {
        return plusSecond(date, minus * 60);
    }

    public static Date minusMinute(Date date, int minus) {
        return minusSecond(date, minus * 60);
    }

    public static Date plusSecond(Date date, long second) {
        try {
            Date temp = new Date();
            temp.setTime(date.getTime() + second * 1000);
            return temp;
        } catch (Exception e) {
            return null;
        }
    }

    public static Date minusSecond(Date date, long second) {
        try {
            Date temp = new Date();
            temp.setTime(date.getTime() - second * 1000);
            return temp;
        } catch (Exception e) {
            return null;
        }
    }

    public static Date plusWeek(Date date, int day) {
        return plusHour(date, day * 24 * 7);
    }

    public static String buildStringPattern(Date time, String pattern) {
        try {
            return new SimpleDateFormat(pattern).format(time);
        } catch (Exception e) {
            return null;
        }
    }

    public static Date buildTimePattern(Date time, String pattern) {
        try {
            return new SimpleDateFormat(DATE_FORMAT).parse(new SimpleDateFormat(pattern).format(time));
        } catch (Exception e) {
            return null;
        }
    }


    public static Timestamp convertToTimestampFormat(String dateTime, String format) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(format);
            Date parsedDate = dateFormat.parse(dateTime);
            return new Timestamp(parsedDate.getTime());
        } catch (Exception e) {
            return null;
        }
    }


    public static Long betweenDate(Date fromDate, Date toDate) {
        LocalDate fDate = toLocalDate(fromDate);
        LocalDate tDate = toLocalDate(toDate);
        Duration diff = Duration.between(fDate.atStartOfDay(), tDate.atStartOfDay());
        return diff.toHours();
    }

    public static Duration between(Date fromDate, Date toDate) {
        LocalDateTime fDate = convertToLocalDateTimeViaInstant(fromDate);
        LocalDateTime tDate = convertToLocalDateTimeViaInstant(toDate);
        return Duration.between(fDate, tDate);
    }

    public static int minusBetween(Date fromDate, Date toDate) {
        LocalDateTime fDate = convertToLocalDateTimeViaInstant(fromDate);
        LocalDateTime tDate = convertToLocalDateTimeViaInstant(toDate);
        return (int) ChronoUnit.MINUTES.between(fDate, tDate);
    }

    public static int secondBetween(Date fromDate, Date toDate) {
        LocalDateTime fDate = convertToLocalDateTimeViaInstant(fromDate);
        LocalDateTime tDate = convertToLocalDateTimeViaInstant(toDate);
        return (int) ChronoUnit.SECONDS.between(fDate, tDate);
    }

    public static int daysBetween(Date fromDate, Date toDate) {
        LocalDateTime fDate = convertToLocalDateTimeViaInstant(fromDate);
        LocalDateTime tDate = convertToLocalDateTimeViaInstant(toDate);
        return (int) ChronoUnit.DAYS.between(fDate, tDate);
    }

    public static int weeksBetween(Date fromDate, Date toDate) {
        LocalDateTime fDate = convertToLocalDateTimeViaInstant(fromDate);
        LocalDateTime tDate = convertToLocalDateTimeViaInstant(toDate);
        return (int) ChronoUnit.WEEKS.between(fDate, tDate);
    }

    public static int monthsBetween(Date fromDate, Date toDate) {
        LocalDateTime fDate = convertToLocalDateTimeViaInstant(fromDate);
        LocalDateTime tDate = convertToLocalDateTimeViaInstant(toDate);
        int j = 0;
        for (int i = 0; i < ChronoUnit.MONTHS.between(fDate, tDate); i++) {
            if (fDate.getDayOfMonth() > tDate.plusMonths(i).getDayOfMonth()) {
                j--;
            } else {
                j++;
            }
        }
        return j;
    }


    public static int yearsBetween(Date fromDate, Date toDate) {
        LocalDateTime fDate = convertToLocalDateTimeViaInstant(fromDate);
        LocalDateTime tDate = convertToLocalDateTimeViaInstant(toDate);
        return (int) ChronoUnit.YEARS.between(tDate, tDate);
    }


    public static int daysBetween(LocalDateTime fromDate, LocalDateTime toDate) {
        return (int) ChronoUnit.DAYS.between(fromDate, toDate);
    }

    public static int weeksBetween(LocalDateTime fromDate, LocalDateTime toDate) {
        return (int) ChronoUnit.WEEKS.between(fromDate, toDate);
    }

    public static int monthsBetween(LocalDateTime fromDate, LocalDateTime toDate) {
        int j = 0;
        for (int i = 0; i < ChronoUnit.MONTHS.between(fromDate, toDate); i++) {
            if (fromDate.getDayOfMonth() > toDate.plusMonths(i).getDayOfMonth()) {
                j--;
            } else {
                j++;
            }
        }
        return j;
    }


    public static Long betweenDate(LocalDateTime fromDate, LocalDateTime toDate) {
        Duration diff = Duration.between(fromDate, toDate);
        return diff.toDays();
    }

    public static LocalDate toLocalDate(Date date) {
        return date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    public static long[] countdown(Date fromDate, Date toDate) {
        long diff = (toDate.getTime() - fromDate.getTime());
        long days = TimeUnit.MILLISECONDS.toDays(diff);
        long hours = TimeUnit.MILLISECONDS.toHours(diff) - TimeUnit.DAYS.toHours(TimeUnit.MILLISECONDS.toDays(diff));
        long minutes = TimeUnit.MILLISECONDS.toMinutes(diff) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(diff));
        long seconds = TimeUnit.MILLISECONDS.toSeconds(diff) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(diff));
        long milliseconds = TimeUnit.MILLISECONDS.toMillis(diff) - TimeUnit.MINUTES.toMillis(TimeUnit.MILLISECONDS.toSeconds(diff));

        return new long[]{Math.max(days, 0), Math.max(hours, 0), Math.max(minutes, 0), Math.max(seconds, 0), Math.max(milliseconds, 0)};
    }

    public static Date convertLocalDateToDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static LocalDateTime convertDateToLocalDate(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    public static Date addNow(Date date) {
        LocalDateTime dateConvert = convertDateToLocalDate(date);
        LocalDateTime now = LocalDateTime.now().withHour(dateConvert.getHour()).withMinute(dateConvert.getMinute()).withSecond(dateConvert.getSecond());
        return convertLocalDateToDate(now);
    }

    public static Date addNowWithoutS(Date date) {
        LocalDateTime dateConvert = convertDateToLocalDate(date);
        LocalDateTime now = LocalDateTime.now().withHour(dateConvert.getHour()).withMinute(dateConvert.getMinute()).withSecond(0);
        return convertLocalDateToDate(now);
    }

    public static Date getStartDateOfMonth(int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DATE, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public static Date getEndDateOfMonth(int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }

    public static boolean validateDateRegex(String date) {
        return date.matches(regexDate);
    }

    public static boolean validateTimeRegex(String time) {
        return time.matches(regexTime);
    }
}

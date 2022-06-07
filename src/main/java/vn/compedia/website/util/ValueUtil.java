package vn.compedia.website.util;

import java.util.Date;

public class ValueUtil {

    private ValueUtil() {
        throw new IllegalStateException("Utility class");
    }

    public static String getStringByObject(Object obj) {
        if (obj == null) {
            return null;
        }
        return obj.toString();
    }

    public static Date getDateByObject(Object obj) {
        if (obj == null) {
            return null;
        }
        return (Date) obj;
    }

    public static Long getLongByObject(Object obj) {
        if (obj == null || obj.toString().isEmpty()) {
            return null;
        }
        return Long.valueOf(obj.toString());
    }

    public static Double getDoubleByObject(Object obj) {
        if (obj == null || obj.toString().isEmpty()) {
            return null;
        }
        return Double.valueOf(obj.toString());
    }

    public static Float getFloatByObject(Object obj) {
        if (obj == null || obj.toString().isEmpty()) {
            return null;
        }
        return Float.valueOf(obj.toString());
    }

    public static Integer getIntegerByObject(Object obj) {
        if (obj == null || obj.toString().isEmpty()) {
            return null;
        }
        return Integer.valueOf(obj.toString());
    }

    public static Boolean getBooleanByObject(Object obj) {
        return (obj != null && "1,true".contains(obj.toString()));
    }


    public static boolean isNumber(String value) {
        try {
            Long.valueOf(value);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static Integer isDivisible(Integer number) {
        if (number % 5 == 1) {
            return number + 19;
        } else if (number % 5 == 2){
            return number + 18;
        } else if (number % 5 == 3){
            return number + 17;
        } else if (number % 5 == 4){
            return number + 16;
        }
        return number + 20;
    }


//    public static List<Long> getListByObject(Object obj){
//        if(obj == null || obj.toString().isEmpty()){
//            return new ArrayList<>();
//        }
//        String[] temp = obj.toString().split(",");
//        return Arrays.asList(obj.toString().split(","))
//                .stream()
//                .mapToLong(Long::parseLong)
//                .toArray();
//    }

}

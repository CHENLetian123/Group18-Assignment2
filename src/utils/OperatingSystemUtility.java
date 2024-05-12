package utils;
import java.util.*;
public class OperatingSystemUtility {


    private static ArrayList<String> operatingSystems = new ArrayList<>(){{
        add("iPad");
        add("Android");
        add("Chrome");
        add("Windows");
        add("Amazon Fire");
    }};

    public static ArrayList<String> getOperatingSystems() {
        return operatingSystems;
    }

    public static boolean isValidOperatingSystem(String os) {
        //must not be case sensitive
        for (String osName:operatingSystems){
            if (osName.equalsIgnoreCase(os)) {
                return true;
            }
        }
        return false;
    }

    public static String getStandardizedOperatingSystem(String os) {
        // 获取与输入字符串相匹配的标准操作系统名称
        for (String osName : operatingSystems) {
            if (osName.equalsIgnoreCase(os)) {
                return osName;
            }
        }
        return null; // 如果操作系统无效，返回 null
    }

}
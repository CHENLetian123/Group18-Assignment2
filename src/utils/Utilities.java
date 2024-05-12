package utils;

import java.util.ArrayList;
import java.util.List;

public class Utilities {

    /**
     * This method takes in a decimal point number and truncates it to two decimal places.  Note
     * that the method does NOT round when truncating; the numbers after the two decimal places are
     * just removed.
     * <p>
     * The method does the truncating in this manner:
     * - multiply the number by 100 e.g. 16.543235523 * 100 = 1654.3235523
     * - cast the multiplied number as an in e.g. 1654.3235523 = 1654
     * - finally, the multiplied and casted number is divided by 100 and returned e.g. 1654 = 16.54
     *
     * @param number Number to be truncated to two decimal places
     * @return the number, passed as a parameter, truncated to two decimal places (note: not rounded)
     */
    /**
     * 这个方法接收一个小数点数并将其截断到两位小数。注意该方法在截断时不会进行四舍五入；
     * 两位小数后的数字会被移除。
     * <p>
     * 该方法的截断方式如下：
     * - 将数字乘以100，例如 16.543235523 * 100 = 1654.3235523
     * - 将乘积转换为整数，例如 1654.3235523 = 1654
     * - 最后，将乘积转化后的整数除以100并返回，例如 1654 = 16.54
     *
     * @param number 要截断到两位小数的数字
     * @return 被截断到两位小数的数字（注意：不进行四舍五入）
     */
    public static double toTwoDecimalPlaces(double number) {
        return (int) (number * 100) / 100.0;
    }

    /**
     * This method returns Y if the booleanToConvert value is true. Returns N otherwise.
     *
     * @param booleanToConvert The boolean value that will be used to determine Y/N
     * @return Returns Y if the booleanToConvert value is true. Returns N otherwise.
     */
    /**
     * 这个方法在 booleanToConvert 值为 true 时返回 Y。否则返回 N。
     *
     * @param booleanToConvert 将用于确定 Y/N 的布尔值
     * @return 如果 booleanToConvert 值为 true，返回 Y。否则返回 N。
     */
    public static char booleanToYN(boolean booleanToConvert) {
        return booleanToConvert ? 'Y' : 'N';
    }

    /**
     * This method returns true if the charToConvert value is Y or y. Returns false in all other cases.
     *
     * @param charToConvert The char value that will be used to determine true/false.
     * @return Returns true if the charToConvert value is Y or y. Returns false otherwise.
     */
    /**
     * 这个方法在 charToConvert 值为 Y 或 y 时返回 true。在所有其他情况下返回 false。
     *
     * @param charToConvert 将用于确定 true/false 的字符值
     * @return 如果 charToConvert 值为 Y 或 y，返回 true。否则返回 false。
     */
    public static boolean YNtoBoolean(char charToConvert) {
        return ((charToConvert == 'y') || (charToConvert == 'Y'));
    }


    /**
     * This method returns true if the numberToCheck is between min and max (both inclusive)
     *
     * @param numberToCheck The number whose range is being checked.
     * @param min The minimum range number to check against (inclusive)
     * @param max The maximum range number to check against (inclusive)
     * @return Returns true if the numberToCheck is between min and max (both inclusive), false otherwise.
     */
    /**
     * 这个方法在 numberToCheck 在 min 和 max 之间（包括 min 和 max）时返回 true
     *
     * @param numberToCheck 要检查范围的数字
     * @param min 要检查的最小范围数字（包含）
     * @param max 要检查的最大范围数字（包含）
     * @return 如果 numberToCheck 在 min 和 max 之间（包含），返回 true。否则返回 false。
     */
    public static boolean validRange(int numberToCheck, int min, int max) {
        return ((numberToCheck >= min) && (numberToCheck <= max));
    }

    /**
     * 这个方法在 numbertoCheck 在 min-delta 和 max+delta 之间时返回 true
     *
     * @param numbertoCheck 要检查范围的浮点数
     * @param min 要检查的最小范围数字
     * @param max 要检查的最大范围数字
     * @param delta 允许的范围误差
     * @return 如果 numbertoCheck 在 min-delta 和 max+delta 之间，返回 true。否则返回 false。
     */
    public static boolean validRange(float numbertoCheck, float min, float max, float delta) {
        return ((numbertoCheck >= (min-delta)) && (numbertoCheck <= (max+delta)));

    }

    /**
     * 这个方法截断字符串到指定长度。如果原字符串长度小于指定长度，返回原字符串。
     *
     * @param stringToTruncate 将被截断到特定长度的字符串
     * @param length 要截断到的长度
     * @return 被截断到指定长度的字符串，或原字符串（如果长度小于指定长度）
     */
    public static String truncateString(String stringToTruncate, int length){
        if (stringToTruncate != null) {
            if (stringToTruncate.length() <= length) {
                return stringToTruncate;
            } else {
                return stringToTruncate.substring(0, length);
            }
        }
        else
            return null;
    }

    /**
     * 这个方法验证字符串是否小于或等于指定长度。
     *
     * @param strToCheck 将被检查的字符串
     * @param maxLength 最大允许长度
     * @return 如果字符串长度小于或等于最大长度，返回 true。否则返回 false。
     */
    public static boolean validStringlength(String strToCheck, int maxLength){
        if (strToCheck != null ){
            return strToCheck.length() <= maxLength;
        }
        return false;
    }

    /**
     *
     * 这个方法在 indexToCheck 是有效索引时返回 true。
     *
     * @param list 用于检查的列表
     * @param indexToCheck 要检查的索引
     * @return 如果 indexToCheck 在有效范围内（0 到列表大小 - 1），返回 true。否则返回 false。
     */
    public static boolean isValidIndex(List list, int indexToCheck){
        return ((indexToCheck >= 0) && (indexToCheck < list.size()));
    }

    /**
     * 验证存储是否在有效范围内并且是8的倍数。
     *
     * @param storage 要验证的存储大小
     * @return 如果存储有效，返回 true，否则返回 false。
     */
    public static boolean isValidStorage(int storage) {
        return (storage >= 8 && storage <= 128 && storage % 8 == 0);
    }
}

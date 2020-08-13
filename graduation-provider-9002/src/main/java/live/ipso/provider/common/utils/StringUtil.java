package live.ipso.provider.common.utils;

/**
 * string 判断处理工具类
 */

public class StringUtil {
    /**
     * 定义下划线
     */
    private static final char UNDERLINE = '_';

    /**
     * String为空判断(不允许空格)
     *
     * @param str
     * @return boolean
     * @author dolyw.com
     * @date 2018/9/4 14:49
     */
    public static boolean isBlank(String str) {
        return str == null || "".equals(str.trim());
    }

    /**
     * String不为空判断(不允许空格)
     *
     * @param str
     * @return boolean
     * @author dolyw.com
     * @date 2018/9/4 14:51
     */
    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }

    /**
     * Byte数组为空判断
     *
     * @param bytes
     * @return boolean
     * @author dolyw.com
     * @date 2018/9/4 15:39
     */
    public static boolean isNull(byte[] bytes) {
        // 根据byte数组长度为0判断
        return bytes == null || bytes.length == 0;
    }

    /**
     * Byte数组不为空判断
     *
     * @param bytes
     * @return boolean
     * @author dolyw.com
     * @date 2018/9/4 15:41
     */
    public static boolean isNotNull(byte[] bytes) {
        return !isNull(bytes);
    }

    /**
     * 驼峰转下划线工具
     *
     * @param param
     * @return java.lang.String
     * @author dolyw.com
     * @date 2018/9/4 14:52
     */
    public static String camelToUnderline(String param) {
        if (isNotBlank(param)) {
            int len = param.length();
            StringBuilder sb = new StringBuilder(len);
            for (int i = 0; i < len; i++) {
                char c = param.charAt(i);
                if (Character.isUpperCase(c)) {
                    sb.append(UNDERLINE);
                    sb.append(Character.toLowerCase(c));
                } else {
                    sb.append(c);
                }
            }
            return sb.toString();
        } else {
            return "";
        }
    }

    /**
     * 下划线转驼峰工具
     *
     * @param param
     * @return java.lang.String
     * @author dolyw.com
     * @date 2018/9/4 14:52
     */
    public static String underlineToCamel(String param) {
        if (isNotBlank(param)) {
            int len = param.length();
            StringBuilder sb = new StringBuilder(len);
            for (int i = 0; i < len; i++) {
                char c = param.charAt(i);
                if (c == 95) {
                    ++i;
                    if (i < len) {
                        sb.append(Character.toUpperCase(param.charAt(i)));
                    }
                } else {
                    sb.append(c);
                }
            }
            return sb.toString();
        } else {
            return "";
        }
    }


    /**
     * String前后加逗号
     */
    public static String addComma(String str) {
        if (str != null && !str.isEmpty()) {
            if (str.indexOf(0) != ',') {
                str = (',' + str);
            }
            if (str.indexOf(str.length() - 1) != ',') {
                str = (str + ',');
            }
        }
        return str;
    }
}

package com.pointlion.mvc.common.utils;

public class CryptoUtils {

    /**
     * 英文逗号：44，直接变成：90
     * 数字 0~9：48~57，全部 加30，变成大写字母，78~87
     * 大写字母：65~90
     * 小写字母：97~122
     */

    // 原 48~57，如果双数，加 30
    // 原 78~87，如果双数，减 30
    // 原：44，转为 90
    // 原：90，转为 44

    /**
     * 加密
     */
    public static String encrypt(String data) {
        int len = data.length();
        char[] charArray = data.toCharArray();

        for (int i = 0; i < len; i++) {
            if (charArray[i] == 44) {
                charArray[i] = 90;

            } else if (charArray[i] == 90) {
                charArray[i] = 44;

            } else if (charArray[i] % 2 == 0) {
                if (charArray[i] >= 48 && charArray[i] <= 57) {
                    charArray[i] = (char) (charArray[i] + 30);

                } else if (charArray[i] >= 78 && charArray[i] <= 87) {
                    charArray[i] = (char) (charArray[i] - 30);
                }
            }
        }

        // 位置互换
        exchange(charArray);

        for (int i = 0; i < len; i++) {
            if (charArray[i] > 65) {
                charArray[i] = (char) (charArray[i] - i % 6);
            }
        }

        return String.valueOf(charArray);
    }

    /**
     * 英文逗号：44，直接变成：90
     * 数字 0~9：48~57，全部 加30，变成大写字母，78~87
     * 大写字母：65~90
     * 小写字母：97~122
     */

    // 原 48~57，如果双数，加 30
    // 原 78~87，如果双数，减 30
    // 原：44，转为 90
    // 原：90，转为 44

    /**
     * 解密
     */
    public static String decrypt(String data) {
        int len = data.length();
        char[] charArray = data.toCharArray();

        for (int i = 0; i < len; i++) {
            if (charArray[i] > 65) {
                charArray[i] = (char) (charArray[i] + i % 6);
            }
        }

        // 位置互换
        exchange(charArray);

        for (int i = 0; i < len; i++) {
            if (charArray[i] == 44) {
                charArray[i] = 90;

            } else if (charArray[i] == 90) {
                charArray[i] = 44;

            } else if (charArray[i] >= 48 && charArray[i] <= 57 && charArray[i] % 2 == 0) {
                charArray[i] = (char) (charArray[i] + 30);

            } else if (charArray[i] >= 78 && charArray[i] <= 87 && charArray[i] % 2 == 0) {
                charArray[i] = (char) (charArray[i] - 30);
            }
        }

        return String.valueOf(charArray);
    }

    private static void exchange(char[] data) {
        int len = data.length;
        int times = 2;

        for (int t = 0; t < times; t++) {
            for (int i = 0; i < (len / 2); i++) {
                if (i % (times + t) == 0) {
                    char tmp = data[i];
                    data[i] = data[len - i - 1];
                    data[len - i - 1] = tmp;
                }
            }
        }
    }
}

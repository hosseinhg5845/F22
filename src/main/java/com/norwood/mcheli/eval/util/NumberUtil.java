package com.norwood.mcheli.eval.util;

public class NumberUtil {
    public static long parseLong(String str) {
        if (str == null) {
            return 0L;
        }
        str = str.trim();
        int len = str.length();
        if (len == 0) {
            return 0L;
        }
        char lastChar = str.charAt(len - 1);
        if (lastChar == '.' || lastChar == 'L' || lastChar == 'l') {
            len--;
            str = str.substring(0, len);
        }
        if (len >= 3 && str.charAt(0) == '0') {
            char c = str.charAt(1);
            if (c == 'B' || c == 'b') {
                return parseLongBin(str, 2, len - 2);
            }
            if (c == 'O' || c == 'o') {
                return parseLongOct(str, 2, len - 2);
            }
            if (c == 'X' || c == 'x') {
                return parseLongHex(str, 2, len - 2);
            }
        }
        return parseLongDec(str, 0, len);
    }

    public static long parseLongBin(String str) {
        return (str == null) ? 0L : parseLongBin(str, 0, str.length());
    }

    public static long parseLongBin(String str, int pos, int len) {
        long result = 0L;
        int end = pos + len;
        while (pos < end) {
            result <<= 1;
            char c = str.charAt(pos);
            if (c == '0') {
                // result already shifted, just continue
            } else if (c == '1') {
                result |= 1L;
            } else {
                throw new NumberFormatException(str.substring(pos, end));
            }
            pos++;
        }
        return result;
    }

    public static long parseLongOct(String str) {
        return (str == null) ? 0L : parseLongOct(str, 0, str.length());
    }

    public static long parseLongOct(String str, int pos, int len) {
        long result = 0L;
        int end = pos + len;
        while (pos < end) {
            result <<= 3;
            char c = str.charAt(pos);
            if (c >= '0' && c <= '7') {
                result |= (c - '0');
            } else {
                throw new NumberFormatException(str.substring(pos, end));
            }
            pos++;
        }
        return result;
    }

    public static long parseLongDec(String str) {
        return (str == null) ? 0L : parseLongDec(str, 0, str.length());
    }

    public static long parseLongDec(String str, int pos, int len) {
        long result = 0L;
        boolean negative = false;
        int end = pos + len;
        if (pos < end && str.charAt(pos) == '-') {
            negative = true;
            pos++;
        } else if (pos < end && str.charAt(pos) == '+') {
            pos++;
        }
        while (pos < end) {
            char c = str.charAt(pos);
            if (c >= '0' && c <= '9') {
                result = result * 10L + (c - '0');
            } else {
                throw new NumberFormatException(str.substring(pos, end));
            }
            pos++;
        }
        return negative ? -result : result;
    }

    public static long parseLongHex(String str) {
        return (str == null) ? 0L : parseLongHex(str, 0, str.length());
    }

    public static long parseLongHex(String str, int pos, int len) {
        long result = 0L;
        int end = pos + len;
        while (pos < end) {
            result <<= 4;
            char c = str.charAt(pos);
            int digit;
            if (c >= '0' && c <= '9') {
                digit = c - '0';
            } else if (c >= 'a' && c <= 'f') {
                digit = c - 'a' + 10;
            } else if (c >= 'A' && c <= 'F') {
                digit = c - 'A' + 10;
            } else {
                throw new NumberFormatException(str.substring(pos, end));
            }
            result |= digit;
            pos++;
        }
        return result;
    }
}

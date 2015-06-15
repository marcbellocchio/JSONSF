package com.trulytech.mantis.util;
 
import java.security.*;
import java.text.*;
import java.util.Random;
import java.util.ArrayList;
 
/**
 * <p>
 * Title: Mantis
 * </p>
 * <p>
 * Description: ???????
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company:
 * </p>
 *
 * @author Wang Xian
 * @version 1.2
 */
 
public class StringUtils {
 
    /**
     * HashCode.
     */
    private static MessageDigest digest = null;
 
    /**
     * ???Hash????(MD5????)
     *
     * @param data
     *            ???????
     * @return Hash????
     */
    public synchronized static final String hash(String data) {
 
        return hash(data.getBytes());
 
    }
 
    /**
     * ???Hash????(MD5????)
     *
     * @param data
     *            byte[]
     * @return String
     */
    public synchronized static final String hash(byte[] data) {
        if (digest == null) {
            try {
                digest = MessageDigest.getInstance("MD5");
            } catch (NoSuchAlgorithmException nsae) {
                System.err.println("Failed to load the MD5 MessageDigest. "
                        + "Jive will be unable to function normally.");
                nsae.printStackTrace();
            }
        }
 
        digest.update(data);
        return encodeHex(digest.digest());
 
    }
 
    /**
     * Turns an array of bytes into a String representing each byte as an
     * unsigned hex number.
     * <p>
     * Method by Santeri Paavolainen, Helsinki Finland 1996<br>
     * (c) Santeri Paavolainen, Helsinki Finland 1996<br>
     * Distributed under LGPL.
     *
     * @param bytes
     *            an array of bytes to convert to a hex-string
     * @return generated hex string
     */
    public static final String encodeHex(byte[] bytes) {
        StringBuffer buf = new StringBuffer(bytes.length * 2);
        int i;
 
        for (i = 0; i < bytes.length; i++) {
            if (((int) bytes[i] & 0xff) < 0x10) {
                buf.append("0");
            }
            buf.append(Integer.toString((int) bytes[i] & 0xff, 16));
        }
        return buf.toString();
    }
 
    /**
     * Turns a hex encoded string into a byte array. It is specifically meant to
     * "reverse" the toHex(byte[]) method.
     *
     * @param hex
     *            a hex encoded String to transform into a byte array.
     * @return a byte array representing the hex String[
     */
    public static final byte[] decodeHex(String hex) {
        char[] chars = hex.toCharArray();
        byte[] bytes = new byte[chars.length / 2];
        int byteCount = 0;
        for (int i = 0; i < chars.length; i += 2) {
            byte newByte = 0x00;
            newByte |= hexCharToByte(chars[i]);
            newByte <<= 4;
            newByte |= hexCharToByte(chars[i + 1]);
            bytes[byteCount] = newByte;
            byteCount++;
        }
        return bytes;
    }
 
    /**
     * Returns the the byte value of a hexadecmical char (0-f). It's assumed
     * that the hexidecimal chars are lower case as appropriate.
     *
     * @param ch
     *            a hexedicmal character (0-f)
     * @return the byte value of the character (0x00-0x0F)
     */
    private static final byte hexCharToByte(char ch) {
        switch (ch) {
        case '0':
            return 0x00;
        case '1':
            return 0x01;
        case '2':
            return 0x02;
        case '3':
            return 0x03;
        case '4':
            return 0x04;
        case '5':
            return 0x05;
        case '6':
            return 0x06;
        case '7':
            return 0x07;
        case '8':
            return 0x08;
        case '9':
            return 0x09;
        case 'a':
            return 0x0A;
        case 'b':
            return 0x0B;
        case 'c':
            return 0x0C;
        case 'd':
            return 0x0D;
        case 'e':
            return 0x0E;
        case 'f':
            return 0x0F;
        }
        return 0x00;
    }
 
    // *********************************************************************
    // * Base64 - a simple base64 encoder and decoder.
    // *
    // * Copyright (c) 1999, Bob Withers - bwit@pobox.com
    // *
    // * This code may be freely used for any purpose, either personal
    // * or commercial, provided the authors copyright notice remains
    // * intact.
    // *********************************************************************
 
    /**
     * Base64????
     *
     * @param data
     *            a String to encode.
     * @return a base64 encoded String.
     */
    public static String encodeBase64(String data) {
        return encodeBase64(data.getBytes());
    }
 
    /**
     * Encodes a byte array into a base64 String.
     *
     * @param data
     *            a byte array to encode.
     * @return a base64 encode String.
     */
    public static String encodeBase64(byte[] data) {
        int c;
        int len = data.length;
        StringBuffer ret = new StringBuffer(((len / 3) + 1) * 4);
        for (int i = 0; i < len; ++i) {
            c = (data[i] >> 2) & 0x3f;
            ret.append(cvt.charAt(c));
            c = (data[i] << 4) & 0x3f;
            if (++i < len)
                c |= (data[i] >> 4) & 0x0f;
 
            ret.append(cvt.charAt(c));
            if (i < len) {
                c = (data[i] << 2) & 0x3f;
                if (++i < len)
                    c |= (data[i] >> 6) & 0x03;
 
                ret.append(cvt.charAt(c));
            } else {
                ++i;
                ret.append((char) fillchar);
            }
 
            if (i < len) {
                c = data[i] & 0x3f;
                ret.append(cvt.charAt(c));
            } else {
                ret.append((char) fillchar);
            }
        }
        return ret.toString();
    }
 
    private static final int fillchar = '=';
 
    private static final String cvt = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
            + "abcdefghijklmnopqrstuvwxyz" + "0123456789+/";
 
    /**
     *
     * ??????????? Returns a random String of numbers and letters (lower and upper
     * case) of the specified length. The method uses the Random class that is
     * built-in to Java which is suitable for low to medium grade security uses.
     * This means that the output is only pseudo random, i.e., each number is
     * mathematically generated so is not truly random.
     * <p>
     *
     * The specified length must be at least one. If not, the method will return
     * null.
     *
     * @param length
     *            the desired length of the random String to return.
     * @return a random String of numbers and letters of the specified length.
     */
    public static final String randomString(int length) {
        if (length < 1) {
            return null;
        }
        // Create a char buffer to put random letters and numbers in.
        char[] randBuffer = new char[length];
        for (int i = 0; i < randBuffer.length; i++) {
            randBuffer[i] = numbersAndLetters[randGen.nextInt(71)];
        }
        return new String(randBuffer);
    }
 
    /**
     * ??????????
     *
     * @param length
     *            int ???????
     * @return String
     */
    public static final String randomNumber(int length) {
        if (length < 1) {
            return null;
        }
        // Create a char buffer to put random letters and numbers in.
        char[] randBuffer = new char[length];
        for (int i = 0; i < randBuffer.length; i++) {
            randBuffer[i] = numbers[randGen.nextInt(9)];
        }
        return new String(randBuffer);
    }
 
    /**
     * Array of numbers and letters of mixed case. Numbers appear in the list
     * twice so that there is a more equal chance that a number will be picked.
     * We can use the array to get a random number or letter by picking a random
     * array index.
     */
    private static char[] numbersAndLetters = ("0123456789abcdefghijklmnopqrstuvwxyz"
            + "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ").toCharArray();
 
    private static char[] numbers = ("0123456789").toCharArray();
 
    private static Random randGen = new Random();
 
    private static final char[] zeroArray = "0000000000000000".toCharArray();
 
    /**
     * ??????0????? Pads the supplied String with 0's to the specified length and
     * returns the result as a new String. For example, if the initial String is
     * "9999" and the desired length is 8, the result would be "00009999". This
     * type of padding is useful for creating numerical values that need to be
     * stored and sorted as character data. Note: the current implementation of
     * this method allows for a maximum <tt>length</tt> of 16.
     *
     * @param string
     *            the original String to pad.
     * @param length
     *            the desired length of the new padded String.
     * @return a new String padded with the required number of 0's.
     */
    public static final String zeroPadString(String string, int length) {
        if (string == null || string.length() > length) {
            return string;
        }
        StringBuffer buf = new StringBuffer(length);
        buf.append(zeroArray, 0, length - string.length()).append(string);
        return buf.toString();
    }
 
    // ???????
    // String strSource - ????
    // String strFrom - ????????
    // String strTo - ????????
    public static String replace(String strSource, String strFrom, String strTo) {
        // ??????????????????????????
        if (strFrom == null || strFrom.equals(""))
            return strSource;
        String strDest = "";
        // ????????????
        int intFromLen = strFrom.length();
        int intPos;
        // ????????
        while ((intPos = strSource.indexOf(strFrom)) != -1) {
            // ????????????????
            strDest = strDest + strSource.substring(0, intPos);
            // ????????????
            strDest = strDest + strTo;
            // ???????????????????
            strSource = strSource.substring(intPos + intFromLen);
        }
        // ??????????????
        strDest = strDest + strSource;
        // ????
        return strDest;
    }
 
    /**
     * ??????
     *
     * @param value
     *            Integer
     * @return String
     */
    public static String stringValue(Integer value) {
        if (value == null)
            return null;
        return String.valueOf(value);
    }
 
    /**
     * ??????
     *
     * @param value
     *            Integer
     * @return String
     */
    public static String stringValue(Double value) {
        if (value == null)
            return null;
        return String.valueOf(value);
    }
 
    /**
     * ??????
     *
     * @param value
     *            Integer
     * @return String
     */
    public static String stringValue(Long value) {
        if (value == null)
            return null;
        return String.valueOf(value);
    }
 
    /**
     * ??????
     *
     * @param value
     *            Integer
     * @return String
     */
    public static String stringValue(Short value) {
        if (value == null)
            return null;
        return String.valueOf(value);
    }
 
    /**
     * ??????
     *
     * @param Str
     *            String ?????
     * @param splitchar
     *            char ??????
     * @return ArrayList
     */
    public static ArrayList Split(String Str, char splitchar) {
        if (Str != null) {
            ArrayList ret = new ArrayList();
            StringBuffer tmpBuffer = new StringBuffer();
            for (int i = 0; i < Str.length(); i++) {
                if (Str.charAt(i) != splitchar)
                    tmpBuffer.append(Str.charAt(i));
                else {
                    ret.add(tmpBuffer.toString());
                    tmpBuffer.delete(0, tmpBuffer.length());
                }
            }
            if (tmpBuffer.length() > 0) {
                ret.add(tmpBuffer.toString());
                tmpBuffer.delete(0, tmpBuffer.length());
            }
 
            return ret;
 
        } else
            return new ArrayList();
    }
 
    /**
     * BCD????
     *
     * @param str
     *            String ???????,????????????
     * @return byte[]
     */
    public static byte[] str2bcd(String str) {
        String s = str.toUpperCase();
        int ii = str.length();
        byte[] b = new byte[ii / 2];
        for (int i = 0, j = 0; i < ii; i++, j++) {
            int i1, i2;
            if (s.charAt(i) - 'A' >= 0)
                i1 = s.charAt(i) - 'A' + 10;
            else
                i1 = s.charAt(i) - '0';
 
            if (s.charAt(i + 1) - 'A' >= 0)
                i2 = s.charAt(i + 1) - 'A' + 10;
            else
                i2 = s.charAt(i + 1) - '0';
 
            b[j] = (byte) (i1 * 0x10 + i2);
            i = i + 1;
        }
        return b;
    }
 
    /**
     * BBCode?????
     *
     * @param Str
     *            String
     * @param BStr
     *            String
     * @param EStr
     *            String
     * @param ReStr
     *            String
     * @return String ???? BStr = "[b]"; EStr = "[/b]"; ReStr = "<b>$lichao$</b>";
     *         Str = LCReplace(Str, BStr, EStr, ReStr);
     */
    public static String LCReplace(String Str, String BStr, String EStr,
            String ReStr) {
        String ReturnStr = "", Str1 = "", Str2 = "";
        int i, j, n;
        n = 0;
        if ((Str.indexOf(BStr) > -1) && ((Str.indexOf(EStr) > -1))) {
            while (Str.indexOf(BStr, n) > -1) {
                i = Str.indexOf(BStr);
                j = Str.indexOf(EStr);
                Str1 = Str.substring((i + BStr.length()), j);
 
                Str2 = StringUtils.replace(ReStr, "$lichao$", Str1);
 
                Str1 = BStr + Str1 + EStr;
                Str = StringUtils.replace(Str, Str1, Str2);
                n = i + Str2.length() - Str1.length();
            }
        }
        ReturnStr = Str;
        return ReturnStr;
    }
 
    /**
     * ???????????????????????
     *
     * @param s
     *            String
     * @return String
     */
    public static String getDigitsOnly(String s) {
        StringBuffer digitsOnly = new StringBuffer();
        char c;
        for (int i = 0; i < s.length(); i++) {
            c = s.charAt(i);
            if (Character.isDigit(c)) {
                digitsOnly.append(c);
            }
        }
        return digitsOnly.toString();
    }
 
    /**
     * ???????????????? http://www.merriampark.com/anatomycc.htm
     *
     * @param cardNumber
     *            String
     * @return boolean
     */
    public static boolean isValidCreditCard(String cardNumber) {
        String digitsOnly = getDigitsOnly(cardNumber);
        int sum = 0;
        int digit = 0;
        int addend = 0;
        boolean timesTwo = false;
 
        for (int i = digitsOnly.length() - 1; i >= 0; i--) {
            digit = Integer.parseInt(digitsOnly.substring(i, i + 1));
            if (timesTwo) {
                addend = digit * 2;
                if (addend > 9) {
                    addend -= 9;
                }
            } else {
                addend = digit;
            }
            sum += addend;
            timesTwo = !timesTwo;
        }
 
        int modulus = sum % 10;
        return modulus == 0;
 
    }
 
    /**
     * ??????StartStr??EndStr???????
     *
     * @param in
     *            String ????
     * @param StartStr
     *            String ??????
     * @param EndStr
     *            String ???????
     * @return ArrayList ??????
     */
    public static ArrayList findString(String in, String StartStr, String EndStr) {
        ArrayList Arr = new ArrayList();
 
        while (true) {
            int from = in.indexOf(StartStr, 0);
            if (from == -1)
                break;
            in = in.substring(from + StartStr.length(), in.length());
 
            int to = in.indexOf(EndStr, 0);
            int mid = in.indexOf(StartStr);
            if ((mid > to || mid == -1) && to != -1)
                Arr.add(in.substring(0, to));
 
        }
        return Arr;
 
    }
 
    /**
     * ????????Unicode????
     *
     * @param text
     *            String
     * @return String
     */
    public static String toUnicode(String text) {
        StringBuffer strBuffer = new StringBuffer();
        if (text == null) {
            return "";
        }
        char[] cArray = text.toCharArray();
        final int cArrayLength = cArray.length;
        strBuffer.delete(0, strBuffer.length());
        String hexStr;
        for (int i = 0; i < cArrayLength; i++) {
            strBuffer.append("\\u");
            hexStr = Integer.toHexString(cArray[i]);
            for (int j = 0; j < 4 - hexStr.length(); j++) {
                strBuffer.append('0');
            }
            strBuffer.append(hexStr);
        }
        return strBuffer.toString();
    }
 
    /**
     * ????IP????????????????
     *
     * @param IPAddress
     *            String IP???,????:192.168.1.120
     * @param NetworkAddress
     *            String ?????? ????:192.168.1.0
     * @param MaskAddress
     *            String ???????? ????:255.255.255.0
     * @return boolean
     */
    public static boolean isvalid(String IPAddress, String NetworkAddress,
            String MaskAddress) {
        boolean flag = true;
        String[] ips = IPAddress.split("\\.");
        String[] mas = MaskAddress.split("\\.");
        String[] nws = NetworkAddress.split("\\.");
 
        try {
            if (ips.length < 4 || mas.length < 4 || nws.length < 4) {
                return false;
            }
            for (int i = 3; i >= 0; i--) {
                int nw = Integer.parseInt(ips[i]) & Integer.parseInt(mas[i]);
                if (Integer.parseInt(nws[i]) != nw) {
                    return false;
                }
            }
            return flag;
        } catch (Exception ex) {
            return false;
        }
    }
 
    /**
     * ????????'????''?????????SQL??????????
     *
     * @param Param
     *            String
     * @return String
     */
    public static String ConvertSQLParameter(String Param) {
        StringBuffer ret = new StringBuffer();
        if (Param != null) {
            for (int i = 0; i < Param.length(); i++) {
                if (Param.charAt(i) == '\'') {
                    ret.append("''");
                } else
                    ret.append(Param.charAt(i));
            }
        }
        return ret.toString();
    }
 
    public static String decodeBase64(byte[] data) {
        int c, c1;
        int len = data.length;
        StringBuffer ret = new StringBuffer((len * 3) / 4);
        for (int i = 0; i < len; ++i) {
            c = cvt.indexOf(data[i]);
            ++i;
            c1 = cvt.indexOf(data[i]);
            c = ((c << 2) | ((c1 >> 4) & 0x3));
            ret.append((char) c);
            if (++i < len) {
                c = data[i];
                if (fillchar == c)
                    break;
 
                c = cvt.indexOf((char) c);
                c1 = ((c1 << 4) & 0xf0) | ((c >> 2) & 0xf);
                ret.append((char) c1);
            }
 
            if (++i < len) {
                c1 = data[i];
                if (fillchar == c1)
                    break;
 
                c1 = cvt.indexOf((char) c1);
                c = ((c << 6) & 0xc0) | c1;
                ret.append((char) c);
            }
        }
        return ret.toString();
    }
 
    /**
     * ??html????????
     *
     * @param inputHtml
     * @return
     */
    public static String extractText(String inputHtml) {
        if (inputHtml == null)
            return null;
        else
            return inputHtml.replaceAll("</?[^>]+>", "");
 
    }
 
}
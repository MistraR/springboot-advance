package com.advance.mistra.utils.http;

import org.apache.commons.lang3.StringUtils;

import java.text.Normalizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Mistra
 * @ Version: 1.0
 * @ Time: 2020/4/12 13:17
 * @ Description: IPV6格式验证
 * @ Copyright (c) Mistra,All Rights Reserved.
 * @ Github: https://github.com/MistraR
 * @ CSDN: https://blog.csdn.net/axela30w
 */
public class IpValidatedUtil {

    private static final String COLON = ":";
    private static final String DOUBLE_COLON = "::";
    private static final String EMPTY_STRING = "";
    private static final String FOUR_ZERO = "0000";
    private static final String LOOP_ADDRESS = "0000:0000:0000:0000:0000:0000:0000:0000";
    private static final String[] BINARYARRAY = {"0000", "0001", "0010", "0011", "0100", "0101", "0110", "0111",
            "1000", "1001", "1010", "1011", "1100", "1101", "1110", "1111"};


    public static void main(String[] args) {
//        validatIPv6();
        baseConversion("e");


    }


    /**
     * ipv6 验证
     */
    public static void validatIPv6() {
        String IPV6SEG = "^([0-9A-Fa-f]{0,4}:){2,7}([0-9A-Fa-f]{1,4}$|((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)(\\.|$)){4})$";
        String s = "([A-Fa-f0-9]{0,4}::?){2,7}[A-Fa-f0-9]{1,4}";
        String s1 = "::?";

        Pattern IPV6_HEX_COMPRESSED_PATTERN =
                Pattern.compile(
                        "^(([0-9A-Fa-f]{1,4}(:[0-9A-Fa-f]{1,4}){0,5})?)" + // 0-6 hex fields
                                "::" +
                                "(([0-9A-Fa-f]{1,4}(:[0-9A-Fa-f]{1,4}){0,5})?)$"); // 0-6 hex fields
//        System.out.println("2000::1:2345:6789:abcd".matches(IPV6SEG));
//        System.out.println("FE80::212:34FF::FE00:ABCD".matches(IPV6SEG));
//        System.out.println("FF01::1101".matches(IPV6SEG));
//        System.out.println("2000::1:2345:6789:abcd".matches(s));
//        System.out.println("FE80::212:34FF::FE00:ABCD".matches(s));
//        System.out.println("FE80::212:34FF::FE00:ABCD".matches(s1));
//        System.out.println("FF01::1101".matches(s));
        System.out.println(isValidIpv6Addr("FF01::1101"));
        System.out.println(isValidIpv6Addr("FE80::212:34FF::FE00:ABCD"));
        System.out.println(isValidIpv6Addr("2000::1:2345:6789:abcd"));
        System.out.println(IPV6_HEX_COMPRESSED_PATTERN.matcher("FF01::1101").matches());
        System.out.println(IPV6_HEX_COMPRESSED_PATTERN.matcher("2000::1:2345:6789:abcd").matches());
        System.out.println(IPV6_HEX_COMPRESSED_PATTERN.matcher("FE80::212:34FF::FE00:ABCD").matches());

//        String ipv61 = "2001:db8:a583:64:c68c:d6df:600c:ee9a";
//        String ipv62 = "2001:db8:a583::9e42:be55:53a7";
//        String ipv63 = "2001:db8:a583:::9e42:be55:53a7";
//        String ipv64 = "1:2:3:4:5::";
//        String ipv65 = "CDCD:910A:2222:5498:8475:1111:3900:2020";
//        String ipv66 = "1030::C9B4:FF12:48AA:1A2B";
//        String ipv67 = "2000:0:0:0:0:0:0:1";
//        String ipv68 = "::0:0:0:0:0:0:1";
//        String ipv69 = "2000:0:0:0:0::";
//        System.out.println("check > " + ipv61 + " > " + isValidIpv6Addr(ipv61));
//        System.out.println("check > " + ipv62 + " > " + isValidIpv6Addr(ipv62));
//        System.out.println("check > " + ipv63 + " > " + isValidIpv6Addr(ipv63));
//        System.out.println("check > " + ipv64 + " > " + isValidIpv6Addr(ipv64));
//        System.out.println("check > " + ipv65 + " > " + isValidIpv6Addr(ipv65));
//        System.out.println("check > " + ipv66 + " > " + isValidIpv6Addr(ipv66));
//        System.out.println("check > " + ipv67 + " > " + isValidIpv6Addr(ipv67));
//        System.out.println("check > " + ipv68 + " > " + isValidIpv6Addr(ipv68));
//        System.out.println("check > " + ipv69 + " > " + isValidIpv6Addr(ipv69));
    }

    /**
     * 验证IPV6格式
     *
     * @param ipAddr
     * @return
     */
    public static boolean isValidIpv6Addr(String ipAddr) {
        String regex = "(^((([0-9A-Fa-f]{1,4}:){7}(([0-9A-Fa-f]{1,4}){1}|:))"
                + "|(([0-9A-Fa-f]{1,4}:){6}((:[0-9A-Fa-f]{1,4}){1}|"
                + "((22[0-3]|2[0-1][0-9]|[0-1][0-9][0-9]|"
                + "([0-9]){1,2})([.](25[0-5]|2[0-4][0-9]|"
                + "[0-1][0-9][0-9]|([0-9]){1,2})){3})|:))|"
                + "(([0-9A-Fa-f]{1,4}:){5}((:[0-9A-Fa-f]{1,4}){1,2}|"
                + ":((22[0-3]|2[0-1][0-9]|[0-1][0-9][0-9]|"
                + "([0-9]){1,2})([.](25[0-5]|2[0-4][0-9]|"
                + "[0-1][0-9][0-9]|([0-9]){1,2})){3})|:))|"
                + "(([0-9A-Fa-f]{1,4}:){4}((:[0-9A-Fa-f]{1,4}){1,3}"
                + "|:((22[0-3]|2[0-1][0-9]|[0-1][0-9][0-9]|"
                + "([0-9]){1,2})([.](25[0-5]|2[0-4][0-9]|[0-1][0-9][0-9]|"
                + "([0-9]){1,2})){3})|:))|(([0-9A-Fa-f]{1,4}:){3}((:[0-9A-Fa-f]{1,4}){1,4}|"
                + ":((22[0-3]|2[0-1][0-9]|[0-1][0-9][0-9]|"
                + "([0-9]){1,2})([.](25[0-5]|2[0-4][0-9]|"
                + "[0-1][0-9][0-9]|([0-9]){1,2})){3})|:))|"
                + "(([0-9A-Fa-f]{1,4}:){2}((:[0-9A-Fa-f]{1,4}){1,5}|"
                + ":((22[0-3]|2[0-1][0-9]|[0-1][0-9][0-9]|"
                + "([0-9]){1,2})([.](25[0-5]|2[0-4][0-9]|"
                + "[0-1][0-9][0-9]|([0-9]){1,2})){3})|:))"
                + "|(([0-9A-Fa-f]{1,4}:){1}((:[0-9A-Fa-f]{1,4}){1,6}"
                + "|:((22[0-3]|2[0-1][0-9]|[0-1][0-9][0-9]|"
                + "([0-9]){1,2})([.](25[0-5]|2[0-4][0-9]|"
                + "[0-1][0-9][0-9]|([0-9]){1,2})){3})|:))|"
                + "(:((:[0-9A-Fa-f]{1,4}){1,7}|(:[fF]{4}){0,1}:((22[0-3]|2[0-1][0-9]|"
                + "[0-1][0-9][0-9]|([0-9]){1,2})"
                + "([.](25[0-5]|2[0-4][0-9]|[0-1][0-9][0-9]|([0-9]){1,2})){3})|:)))$)";
        if (ipAddr == null) {
            System.out.println("ipv6 addresss is null ");
            return false;
        }
        ipAddr = Normalizer.normalize(ipAddr, Normalizer.Form.NFKC);
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(ipAddr);
        boolean match = matcher.matches();
        if (!match) {
            System.out.println("invalid ipv6 addresss = " + ipAddr);
        }
        return match;
    }

    /**
     *  IPV6ADDR = (
     *             (IPV6SEG:){7,7}IPV6SEG|                # 1:2:3:4:5:6:7:8
     *             (IPV6SEG:){1,7}:|                      # 1::                                 1:2:3:4:5:6:7::
     *             (IPV6SEG:){1,6}:IPV6SEG|               # 1::8               1:2:3:4:5:6::8   1:2:3:4:5:6::8
     *             (IPV6SEG:){1,5}(:IPV6SEG){1,2}|        # 1::7:8             1:2:3:4:5::7:8   1:2:3:4:5::8
     *             (IPV6SEG:){1,4}(:IPV6SEG){1,3}|        # 1::6:7:8           1:2:3:4::6:7:8   1:2:3:4::8
     *             (IPV6SEG:){1,3}(:IPV6SEG){1,4}|        # 1::5:6:7:8         1:2:3::5:6:7:8   1:2:3::8
     *             (IPV6SEG:){1,2}(:IPV6SEG){1,5}|        # 1::4:5:6:7:8       1:2::4:5:6:7:8   1:2::8
     *     IPV6SEG:((:IPV6SEG){1,6})|             # 1::3:4:5:6:7:8     1::3:4:5:6:7:8   1::8
     *             :((:IPV6SEG){1,7}|:)|                  # ::2:3:4:5:6:7:8    ::2:3:4:5:6:7:8  ::8       ::
     *     fe80:(:IPV6SEG){0,4}%[0-9a-zA-Z]{1,}|  # fe80::7:8%eth0     fe80::7:8%1  (link-local IPv6 addresses with zone index)
     *             ::(ffff(:0{1,4}){0,1}:){0,1}IPV4ADDR|  # ::255.255.255.255  ::ffff:255.255.255.255  ::ffff:0:255.255.255.255 (IPv4-mapped IPv6 addresses and IPv4-translated addresses)
     *             (IPV6SEG:){1,4}:IPV4ADDR               # 2001:db8:3:4::192.0.2.33  64:ff9b::192.0.2.33 (IPv4-Embedded IPv6 Address)
     *             )
     */
    /**
     * TODO IPV6网段格式验证
     *
     * @param ipAddr
     * @return
     */
    public static boolean isValidIpv6Segment(String ipAddr) {

        return true;
    }

    private static String hexStr = "0123456789ABCDEF";


    /**
     * 进制转换测试
     *
     * @param str
     */
    public static void baseConversion(String str) {
        System.out.println("源字符串：" + str);
        String hexString = binaryToHexString(str.getBytes());
        System.out.println("转换为十六进制：" + hexString);
        System.out.println("转换为二进制：" + bytes2BinaryStr(str.getBytes()));
        byte[] bArray = hexStringToBinary(hexString);
        System.out.println("将str的十六进制文件转换为二进制再转为String：" + new String(bArray));

        System.out.println(bytes2BinaryStr(hexStringToBinary(str)));
    }

    public static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1)
            return null;
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2),
                    16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }

    /**
     * 字节数组转换为二进制字符串
     *
     * @param bArray 字节数组
     * @return 二进制字符串
     */
    public static String bytes2BinaryStr(byte[] bArray) {
        StringBuilder outStr = new StringBuilder(EMPTY_STRING);
        int pos = 0;
        for (byte b : bArray) {
            // 高四位
            pos = (b & 0xF0) >> 4;
            outStr.append(BINARYARRAY[pos]);
            // 低四位
            pos = b & 0x0F;
            outStr.append(BINARYARRAY[pos]);
        }
        return outStr.toString();
    }

    /**
     * 将二进制转换为十六进制字符串
     *
     * @param bytes 二进制字节数组
     * @return 十六进制字符串
     */
    public static String binaryToHexString(byte[] bytes) {
        StringBuilder result = new StringBuilder(EMPTY_STRING);
        String hex = EMPTY_STRING;
        for (int i = 0; i < bytes.length; i++) {
            // 字节高4位
            hex = String.valueOf(hexStr.charAt((bytes[i] & 0xF0) >> 4));
            // 字节低4位
            hex += String.valueOf(hexStr.charAt(bytes[i] & 0x0F));
            result.append(hex);
        }
        return result.toString();
    }

    /**
     * 将十六进制转换为字节数组
     *
     * @param hexString 十六进制字符串
     * @return 二进制字节数组
     */
    public static byte[] hexStringToBinary(String hexString) {
        // hexString的长度对2取整，作为bytes的长度
        int len = hexString.length() / 2;
        byte[] bytes = new byte[len];
        // 字节高四位
        byte high = 0;
        // 字节低四位
        byte low = 0;
        for (int i = 0; i < len; i++) {
            // 右移四位得到高位
            high = (byte) ((hexStr.indexOf(hexString.charAt(2 * i))) << 4);
            low = (byte) hexStr.indexOf(hexString.charAt(2 * i + 1));
            // 高地位做或运算
            bytes[i] = (byte) (high | low);
        }
        return bytes;
    }

    /**
     * 将非简写的IPv6 转换成 简写的IPv6
     *
     * @param fullIPv6 非简写的IPv6
     * @return 简写的IPv6
     */
    public static String parseFullIPv6ToAbbreviation(String fullIPv6) {
        String abbreviation = EMPTY_STRING;
        // 1,校验 ":" 的个数 不等于7  或者长度不等于39  直接返回空串
        int count = fullIPv6.length() - fullIPv6.replaceAll(COLON, EMPTY_STRING).length();
        if (fullIPv6.length() != 39 || count != 7) {
            return abbreviation;
        }
        // 2,去掉每一位前面的0
        String[] arr = fullIPv6.split(COLON);
        for (int i = 0; i < arr.length; i++) {
            arr[i] = arr[i].replaceAll("^0{1,3}", EMPTY_STRING);
        }
        // 3,找到最长的连续的0
        String[] arr2 = arr.clone();
        for (int i = 0; i < arr2.length; i++) {
            if (!"0".equals(arr2[i])) {
                arr2[i] = "-";
            }
        }
        Pattern pattern = Pattern.compile("0{2,}");
        Matcher matcher = pattern.matcher(StringUtils.join(arr2, EMPTY_STRING));
        String maxStr = EMPTY_STRING;
        int start = -1;
        int end = -1;
        while (matcher.find()) {
            if (maxStr.length() < matcher.group().length()) {
                maxStr = matcher.group();
                start = matcher.start();
                end = matcher.end();
            }
        }
        // 4,合并        
        if (maxStr.length() > 0) {
            for (int i = start; i < end; i++) {
                arr[i] = COLON;
            }
        }
        abbreviation = StringUtils.join(arr, COLON);
        abbreviation = abbreviation.replaceAll(":{2,}", DOUBLE_COLON);
        return abbreviation;
    }

    /**
     * 将简写的IPv6 转换成 非简写的IPv6
     *
     * @param abbreviation 简写的IPv6
     * @return 非简写的IPv6
     */
    public static String parseAbbreviationToFullIPv6(String abbreviation) {
        if (DOUBLE_COLON.equals(abbreviation)) {
            return LOOP_ADDRESS;
        }
        String[] arr = new String[]{FOUR_ZERO, FOUR_ZERO, FOUR_ZERO, FOUR_ZERO, FOUR_ZERO, FOUR_ZERO, FOUR_ZERO, FOUR_ZERO};
        if (abbreviation.startsWith(DOUBLE_COLON)) {
            String[] temp = abbreviation.substring(2).split(COLON);
            for (int i = 0; i < temp.length; i++) {
                String tempStr = FOUR_ZERO + temp[i];
                arr[i + 8 - temp.length] = tempStr.substring(tempStr.length() - 4);
            }
        } else if (abbreviation.endsWith(DOUBLE_COLON)) {
            String[] temp = abbreviation.substring(0, abbreviation.length() - 2).split(COLON);
            for (int i = 0; i < temp.length; i++) {
                String tempStr = FOUR_ZERO + temp[i];
                arr[i] = tempStr.substring(tempStr.length() - 4);
            }
        } else if (abbreviation.contains(DOUBLE_COLON)) {
            String[] tempArr = abbreviation.split(DOUBLE_COLON);

            String[] temp0 = tempArr[0].split(COLON);
            for (int i = 0; i < temp0.length; i++) {
                String tempStr = FOUR_ZERO + temp0[i];
                arr[i] = tempStr.substring(tempStr.length() - 4);
            }
            String[] temp1 = tempArr[1].split(COLON);
            for (int i = 0; i < temp1.length; i++) {
                String tempStr = FOUR_ZERO + temp1[i];
                arr[i + 8 - temp1.length] = tempStr.substring(tempStr.length() - 4);
            }
        } else {
            String[] tempArr = abbreviation.split(COLON);
            for (int i = 0; i < tempArr.length; i++) {
                String tempStr = FOUR_ZERO + tempArr[i];
                arr[i] = tempStr.substring(tempStr.length() - 4);
            }
        }
        return StringUtils.join(arr, COLON);
    }
}

package com.advance.mistra.utils.http;

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


    public static void main(String[] args) {
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

}

package com.advance.mistra.test.json;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * @Author: Mistra
 * @Version: 1.0
 * @Time: 2020/2/19 14:03
 * @Description: JSONArray  JSONObject 测试类
 * @Copyright (c) Mistra,All Rights Reserved.
 * @Github: https://github.com/MistraR
 * @CSDN: https://blog.csdn.net/axela30w
 */
@Slf4j
public class JSONArrayJSONObjectTest {

    public static void main(String[] args) {
//        jsonConvertCollention();
        jsonArray();
    }

    /**
     * JSONArray 测试
     */
    public static void jsonArray() {
        JSONObject jsonObject1 = new JSONObject();
        JSONArray jsonArray1 = new JSONArray();
        jsonObject1.put("name", "Mistra1");
        jsonArray1.add("1");
        jsonObject1.put("firm_code", jsonArray1);
        log.info("jsonObject1:{}", jsonObject1);
        JSONObject jsonObject2 = new JSONObject();
        jsonObject2.put("name", "Mistra2");
        jsonObject2.put("firm_code", "1");
        log.info("jsonObject2:{}", jsonObject2);
        try {
            JSONArray jsonArray3 = jsonObject1.getJSONArray("firm_code");
            log.info("jsonArray3:{}", jsonArray3);
        } catch (ClassCastException c) {
            String firmCode = jsonObject2.getString("firm_code");
            log.info("firm_code:{}", firmCode);
        }
    }

    /**
     * json与集合相互转换
     */
    public static void jsonConvertCollention() {
        List<String> list = new ArrayList<>(16);
        list.add("1.1.1.1");
        list.add("2.2.2.2");
        System.out.println(list.toString());

        JSONArray jsonArray1 = new JSONArray();
        jsonArray1.add("1.1.1.1");
        jsonArray1.add("2.2.2.2");
        jsonArray1.add("1.1.1.1");
        System.out.println(jsonArray1);

        JSONArray jsonArray2 = new JSONArray();
        JSONObject jsonObject1 = new JSONObject();
        JSONObject jsonObject2 = new JSONObject();
        jsonObject1.put("ip1", "1.1.1.1");
        jsonObject1.put("ip2", "2.2.2.2");
        jsonObject2.put("ip3", "3.3.3.3");
        jsonObject2.put("ip4", "4.4.4.4");
        jsonArray2.add(jsonObject1);
        jsonArray2.add(jsonObject2);
        System.out.println(jsonArray2);

        HashSet<String> hashSet = new HashSet<>(4);
        hashSet.add("1.1.1.1");
        hashSet.add("2.2.2.2");
        System.out.println(StringUtils.collectionToDelimitedString(hashSet, ","));
        JSONArray jsonArray3 = new JSONArray();
        jsonArray3.addAll(hashSet);
        System.out.println(jsonArray3);
    }
}

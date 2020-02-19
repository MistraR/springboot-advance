package com.advance.mistra.test.clone;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @Author: Mistra
 * @Version: 1.0
 * @Time: 2020/2/18 20:39
 * @Description: 深度拷贝测试
 * @Copyright (c) Mistra,All Rights Reserved.
 * @Github: https://github.com/MistraR
 * @CSDN: https://blog.csdn.net/axela30w
 */
@Slf4j
public class DeepCloneTest {

    public static void main(String[] args) throws InterruptedException {
        ConcurrentLinkedQueue<JSONObject> deepCloneQueue = new ConcurrentLinkedQueue<>();
        ConcurrentLinkedQueue<JSONObject> queue = new ConcurrentLinkedQueue<>();
        ConcurrentHashMap<String, JSONArray> map = new ConcurrentHashMap<>();
        JSONArray A = new JSONArray();
        for (int i = 1; i < 10000; i++) {
            JSONObject tmp = new JSONObject();
            tmp.put("name", "张三");
            A.add(tmp);
        }
        map.put("1", A);
        map.get("1").forEach(obj -> {
            deepCloneQueue.add((JSONObject) ((JSONObject) obj).clone());
            queue.add((JSONObject) obj);
        });
        map.clear();
        Thread.sleep(5 * 1000 * 10);
        System.gc();
        log.info("deepCloneQueue:{},queue:{}", deepCloneQueue.size(), queue.size());
    }
}

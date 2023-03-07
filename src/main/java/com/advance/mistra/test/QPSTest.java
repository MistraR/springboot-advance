package com.advance.mistra.test;

import com.advance.mistra.plugin.redis.RedisUtil;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * @author rui.wang
 * @ Version: 1.0
 * @ Time: 2023/3/7 11:35
 * @ Description: JMeter线程组设置：1秒内启动1100个线程，每个线程循环请求500次
 * Caffeine QPS:30012
 * HashMap QPS:30545
 * Redis QPS:1359
 */
@Component
public class QPSTest {

    private final RedisUtil redisUtil;

    private final HashMap<String, String> hashMap = new HashMap<>();

    private final Cache<String, String> caffeineCache = Caffeine.newBuilder().expireAfterWrite(200000, TimeUnit.SECONDS)
            .maximumSize(10000).build();

    public QPSTest(RedisUtil redisUtil) {
        this.redisUtil = redisUtil;
    }

    @PostConstruct
    private void init() {
        for (int i = 0; i < 5000; i++) {
            String kv = "MISTRA" + i;
            redisUtil.set(kv, kv);
            hashMap.put(kv, kv);
            caffeineCache.put(kv, kv);
        }
        redisUtil.set("MISTRA", "mistra");
        hashMap.put("MISTRA", "mistra0");
        caffeineCache.put("MISTRA", "mistra0");
    }

    @PostMapping("/test-redis")
    public String test() {
        return redisUtil.get("MISTRA");
    }

    @PostMapping("/test-hashMap")
    public String testMap() {
        return hashMap.get("MISTRA");
    }

    @PostMapping("/test-caffeine")
    public String testCaffeine() {
        return caffeineCache.getIfPresent("MISTRA");
    }
}

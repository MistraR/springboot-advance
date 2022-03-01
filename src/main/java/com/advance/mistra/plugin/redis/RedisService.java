package com.advance.mistra.plugin.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author mistra@future.com
 * @date 2022/3/1
 */
@Component
public class RedisService {

    @Autowired
    private ReentrantLockUtil reentrantLockUtil;

    public void test() {
        String key = String.format("MISTRA:assignUserCommunity:%s", "15000001");
        // 获取到锁之后执行业务逻辑
        reentrantLockUtil.lockThenExecute(key, () -> {

        });
    }
}

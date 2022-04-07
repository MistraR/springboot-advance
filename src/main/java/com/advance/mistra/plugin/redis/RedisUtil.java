package com.advance.mistra.plugin.redis;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import com.google.common.collect.Maps;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;
import redis.clients.jedis.Tuple;
import redis.clients.jedis.params.SetParams;

/**
 * @author mistra@future.com
 * @date 2022/2/28
 */
@Component
public class RedisUtil {
    private static final Logger log = LoggerFactory.getLogger(RedisUtil.class);
    private static final String SET_TYPE_NX = "NX";
    private static final String EXPIRE_UNIT_EX = "EX";
    private static final String SUCCESS_RESULT = "OK";
    private static final int BATCH_SIZE = 5000;
    private static final int DEFAULT_LOCK_TIME_SECONDS = 60;
    @Autowired
    private JedisPool jedisPool;

    public RedisUtil() {
    }

    public boolean isKeyExist(String key) {
        return (Boolean) this.execute((jedis) -> {
            return jedis.exists(key);
        });
    }

    public Long setKeyExpire(String key, int seconds) {
        return (Long) this.execute((jedis) -> {
            return jedis.expire(key, seconds);
        });
    }

    public String get(String key) {
        return (String) this.execute((jedis) -> {
            return jedis.get(key);
        });
    }

    public String set(String key, String value) {
        return (String) this.execute((jedis) -> {
            return jedis.set(key, value);
        });
    }

    public String setex(String key, String value, int seconds) {
        return (String) this.execute((jedis) -> {
            return jedis.setex(key, seconds, value);
        });
    }

    public Long setIfNotExist(String key, String value) {
        return (Long) this.execute((jedis) -> {
            return jedis.setnx(key, value);
        });
    }

    public String setKeyIfNotExist(String key, String value, int seconds) {
        SetParams params = new SetParams();
        params.nx();
        params.ex(seconds);
        return (String) this.execute((jedis) -> {
            return jedis.set(key, value, params);
        });
    }

    public Long ttl(String key) {
        return (Long) this.execute((jedis) -> {
            return jedis.ttl(key);
        });
    }

    public Long pttl(String key) {
        return (Long) this.execute((jedis) -> {
            return jedis.pttl(key);
        });
    }

    public boolean isMapKeyExist(String key, String mapKey) {
        return (Boolean) this.execute((jedis) -> {
            return jedis.hexists(key, mapKey);
        });
    }

    public Long setMap(String key, String mapKey, String mapVal) {
        return (Long) this.execute((jedis) -> {
            return jedis.hset(key, mapKey, mapVal);
        });
    }

    public String setMapAll(String key, Map<String, String> map) {
        return MapUtils.isEmpty(map) ? "" : (String) this.execute((jedis) -> {
            return jedis.hmset(key, map);
        });
    }

    public Long zRemRangeByRank(String key, long start, long end) {
        return StringUtils.isBlank(key) ? null : (Long) this.execute((jedis) -> {
            return jedis.zremrangeByRank(key, start, end);
        });
    }

    public String batchSetMapAll(String key, Map<String, String> map) {
        return MapUtils.isEmpty(map) ? "" : (String) this.executePipeline((pair) -> {
            Pipeline pipeline = (Pipeline) pair.getRight();
            Set<String> mapKeySet = map.keySet();
            int index = 0;
            Iterator var6 = mapKeySet.iterator();

            while (var6.hasNext()) {
                String mapKey = (String) var6.next();
                pipeline.hset(key, mapKey, (String) map.get(mapKey));
                ++index;
                if (index % 5000 == 0) {
                    pipeline.sync();
                }
            }
            pipeline.sync();
            return "OK";
        });
    }

    public String getMap(String key, String mapKey) {
        return (String) this.execute((jedis) -> {
            return jedis.hget(key, mapKey);
        });
    }

    public Map<String, String> getMapAll(String key) {
        return (Map) this.execute((jedis) -> {
            return jedis.hgetAll(key);
        });
    }

    public Map<String, String> batchGetMap(String key, String... mapKeys) {
        return (Map) (mapKeys != null && mapKeys.length >= 1 ? (Map) this.executePipeline((pair) -> {
            Pipeline pipeline = (Pipeline) pair.getRight();
            return this.processMap(key, Arrays.asList(mapKeys), pipeline);
        }) : Maps.newHashMap());
    }

    public Map<String, String> batchGetMapAll(String key) {
        return (Map) this.executePipeline((pair) -> {
            Jedis jedis = (Jedis) pair.getLeft();
            Pipeline pipeline = (Pipeline) pair.getRight();
            Set<String> mapKeys = jedis.hkeys(key);
            return (Map) (CollectionUtils.isEmpty(mapKeys) ? Maps.newHashMap() : this.processMap(key, mapKeys, pipeline));
        });
    }

    private Map<String, String> processMap(String key, Collection<String> mapKeys, Pipeline pipeline) {
        Map<String, String> result = Maps.newHashMap();
        int index = 0;
        Map<byte[], Response<byte[]>> newMap = Maps.newHashMap();
        Iterator var7 = mapKeys.iterator();

        while (var7.hasNext()) {
            String mapKey = (String) var7.next();
            newMap.put(mapKey.getBytes(), pipeline.hget(key.getBytes(), mapKey.getBytes()));
            ++index;
            if (index % 5000 == 0) {
                pipeline.sync();
            }
        }

        pipeline.sync();
        var7 = newMap.entrySet().iterator();

        while (var7.hasNext()) {
            Map.Entry<byte[], Response<byte[]>> entry = (Map.Entry) var7.next();
            if (entry.getKey() != null) {
                Response<byte[]> sResponse = (Response) entry.getValue();
                if (sResponse != null && sResponse.get() != null) {
                    result.put(new String((byte[]) entry.getKey()), (new String((byte[]) sResponse.get())).toString());
                }
            }
        }
        return result;
    }

    public Long deleteMapKeys(String key, String... mapKeys) {
        return mapKeys != null && mapKeys.length >= 1 ? (Long) this.execute((jedis) -> {
            return jedis.hdel(key, mapKeys);
        }) : 0L;
    }

    public Long sAdd(String key, String... set) {
        return (Long) this.execute((jedis) -> {
            return jedis.sadd(key, set);
        });
    }

    public Boolean sisMember(String key, String member) {
        return (Boolean) this.execute((jedis) -> {
            return jedis.sismember(key, member);
        });
    }

    public Long deleteKey(String key) {
        return (Long) this.execute((jedis) -> {
            return jedis.del(key);
        });
    }

    public Long deleteKeyBatch(List<String> keyList) {
        return CollectionUtils.isEmpty(keyList) ? 0L : (Long) this.execute((jedis) -> {
            return jedis.del((String[]) keyList.toArray(new String[keyList.size()]));
        });
    }

    public Long deleteZSetKeys(String key, String... zsetKeys) {
        return zsetKeys != null && zsetKeys.length >= 1 ? (Long) this.execute((jedis) -> {
            return jedis.zrem(key, zsetKeys);
        }) : 0L;
    }

    public Long listHeadAdd(String key, String... vals) {
        return vals != null && vals.length >= 1 ? (Long) this.execute((jedis) -> {
            return jedis.lpush(key, vals);
        }) : 0L;
    }

    public Long listTailAdd(String key, String... vals) {
        return vals != null && vals.length >= 1 ? (Long) this.execute((jedis) -> {
            return jedis.rpush(key, vals);
        }) : 0L;
    }

    public String listTailDelete(String key) {
        return (String) this.execute((jedis) -> {
            return jedis.rpop(key);
        });
    }

    public String listLtrim(String key, long start, long stop) {
        return (String) this.execute((jedis) -> {
            return jedis.ltrim(key, start, stop);
        });
    }

    public List<String> listSub(String key, long begin, long end) {
        return (List) this.execute((jedis) -> {
            return jedis.lrange(key, begin, end);
        });
    }

    public Long listSize(String key) {
        return (Long) this.execute((jedis) -> {
            return jedis.llen(key);
        });
    }

    public String renameKey(String oldKey, String newKey) {
        return (String) this.execute((jedis) -> {
            return jedis.rename(oldKey, newKey);
        });
    }

    public boolean tryLockAndSetExpire(String key, int seconds) {
        return (Boolean) this.execute((jedis) -> {
            String result = jedis.set(key, key, SetParams.setParams().nx().ex(seconds));
            return "OK".equalsIgnoreCase(result);
        });
    }

    /**
     * @deprecated
     */
    @Deprecated
    public boolean tryLock(String key) {
        return this.tryLockAndSetExpire(key, 60);
    }

    public boolean setBit(String key, Long offset, boolean value, int expireTime) {
        return (Boolean) this.execute((jedis) -> {
            if (jedis.setbit(key, offset, value)) {
                jedis.expire(key, expireTime);
                return true;
            } else {
                return false;
            }
        });
    }

    public boolean getBit(String key, Long offset) {
        return (Boolean) this.execute((jedis) -> {
            return jedis.getbit(key, offset);
        });
    }

    public Long countBit(String key) {
        return (Long) this.execute((jedis) -> {
            return jedis.bitcount(key);
        });
    }

    public Long countBit(String key, Long start, Long end) {
        return (Long) this.execute((jedis) -> {
            return jedis.bitcount(key, start, end);
        });
    }

    /**
     * @deprecated
     */
    @Deprecated
    public Long unLock(String key) {
        return this.deleteKey(key);
    }

    public Long incr(String key) {
        return (Long) this.execute((jedis) -> {
            return jedis.incr(key);
        });
    }

    public Long incrBy(String key, long increment) {
        return (Long) this.execute((jedis) -> {
            return jedis.incrBy(key, increment);
        });
    }

    public Long hincrBy(String key, String field, long increment) {
        return (Long) this.execute((jedis) -> {
            return jedis.hincrBy(key, field, increment);
        });
    }

    public Long decr(String key) {
        return (Long) this.execute((jedis) -> {
            return jedis.decr(key);
        });
    }

    public Long zadd(String key, Double score, String member) {
        return (Long) this.execute((jedis) -> {
            return jedis.zadd(key, score, member);
        });
    }

    public Long zcard(String key) {
        return (Long) this.execute((jedis) -> {
            return jedis.zcard(key);
        });
    }

    public Set<Tuple> zrangeWithScores(String key, Long start, Long stop) {
        return (Set) this.execute((jedis) -> {
            return jedis.zrangeWithScores(key, start, stop);
        });
    }

    public Set<Tuple> zrevrangeWithScores(String key, Long start, Long stop) {
        return (Set) this.execute((jedis) -> {
            return jedis.zrevrangeWithScores(key, start, stop);
        });
    }

    public Set<Tuple> zrangeByScoreWithScores(String key, Double min, Double max) {
        return (Set) this.execute((jedis) -> {
            return jedis.zrangeByScoreWithScores(key, min, max);
        });
    }

    public Set<Tuple> zrevrangeByScoreWithScores(String key, Double min, Double max) {
        return (Set) this.execute((jedis) -> {
            return jedis.zrevrangeByScoreWithScores(key, min, max);
        });
    }

    public Long zrank(String key, String member) {
        return (Long) this.execute((jedis) -> {
            return jedis.zrank(key, member);
        });
    }

    public Long zrevrank(String key, String member) {
        return (Long) this.execute((jedis) -> {
            return jedis.zrevrank(key, member);
        });
    }

    public Double zscore(String key, String member) {
        return (Double) this.execute((jedis) -> {
            return jedis.zscore(key, member);
        });
    }

    public Long zrem(String key, String member) {
        return (Long) this.execute((jedis) -> {
            return jedis.zrem(key, new String[]{member});
        });
    }

    public Set<String> keys(String pattern) {
        return (Set) this.execute((jedis) -> {
            return jedis.keys(pattern);
        });
    }

    public <V> V doWorkInPool(RedisUtil.PoolWork<Jedis, V> work) {
        if (this.jedisPool == null) {
            throw new IllegalArgumentException("pool must not be null");
        } else if (work == null) {
            throw new IllegalArgumentException("work must not be null");
        } else {
            Jedis poolResource = this.jedisPool.getResource();

            Object result;
            try {
                result = work.doWork(poolResource);
            } catch (RuntimeException var9) {
                throw var9;
            } catch (Exception var10) {
                throw new RuntimeException(var10);
            } finally {
                poolResource.close();
            }

            return (V) result;
        }
    }

    private <R> R execute(Function<Jedis, R> function) {
        Jedis jedis = null;

        Object var3;
        try {
            jedis = this.jedisPool.getResource();
            var3 = function.apply(jedis);
        } finally {
            if (jedis != null) {
                jedis.close();
            }

        }

        return (R) var3;
    }

    public <R> R executePipeline(Function<Pair<Jedis, Pipeline>, R> function) {
        Jedis jedis = null;
        Pipeline pipeline = null;

        Object var4;
        try {
            jedis = this.jedisPool.getResource();
            pipeline = jedis.pipelined();
            var4 = function.apply(Pair.of(jedis, pipeline));
        } finally {
            if (null != pipeline) {
                pipeline.close();
            }

            if (null != jedis) {
                jedis.close();
            }

        }

        return (R) var4;
    }

    public interface PoolWork<T, V> {
        V doWork(T var1) throws Exception;
    }
}

package com.demo.t_web.comn.util;

/**
 * <pre>
 * com.wigo.trend.comn.util.RedisCacheEnum
 *   - RedisCacheEnum.java
 * </pre>
 *
 * @author : 한태우
 * @ClassName : RedisCacheEnum
 * @description :
 * @date : 7/3/24
 */
public enum RedisCache {
    MAP_DATA_LIST("MAPDATA", 5);

    private final String redisCache;
    private final int expireTime;

    RedisCache(String redisCache, int expire) {
        this.redisCache = redisCache;
        this.expireTime = expire;
    }

    public String redisCacheName(){
        return redisCache;
    }

    public int redisExpire(){
        return expireTime;
    }
}

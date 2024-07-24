package com.demo.t_web.comn.config;//package com.wigo.trend.comn.config;

import com.demo.t_web.comn.util.RedisCache;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 * com.wigo.trend.comn.config.RedisConfig
 *   - RedisConfig.java
 * </pre>
 *
 * @author : 한태우
 * @className : RedisConfig
 * @description :
 * @date : 6/12/24
 */
@NoArgsConstructor
@Configuration
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 1800)
public class RedisConfig {

    @Value("${spring.data.redis.host}")
    private String host;

    @Value("${spring.data.redis.password}")
    private String password;

    @Value("${spring.data.redis.port}")
    private int port;

    @Bean
    public RedisConnectionFactory lettuceConnectionFactory(){
        final RedisStandaloneConfiguration rsc = new RedisStandaloneConfiguration(host, port);
        rsc.setPassword(RedisPassword.of(password));
        return new LettuceConnectionFactory(rsc);
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(){
        final RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(lettuceConnectionFactory());
        template.setDefaultSerializer(new StringRedisSerializer());
        return template;
    }

    @Bean
    public CacheManager cacheManager(ResourceLoader resourceLoader){

        RedisCacheConfiguration configuration = RedisCacheConfiguration.defaultCacheConfig()
                .disableCachingNullValues()
                .serializeValuesWith(RedisSerializationContext.SerializationPair
                        .fromSerializer(new JdkSerializationRedisSerializer(getClass().getClassLoader())))
                .entryTtl(Duration.ofMinutes(5));

        RedisCacheManager cacheManager = RedisCacheManager.RedisCacheManagerBuilder.fromConnectionFactory(lettuceConnectionFactory())
                        .cacheDefaults(configuration)
                        .build();

        cacheManager.setTransactionAware(true);

        return cacheManager;
    }

    public Map<String, RedisCacheConfiguration> setEachExpireCache(){
        Map<String, RedisCacheConfiguration> config = new HashMap<>();
        RedisCache[] caches = RedisCache.values();
        for(RedisCache cache : caches){
            config.put(cache.redisCacheName(), RedisCacheConfiguration.defaultCacheConfig()
                    .entryTtl(Duration.ofMinutes(cache.redisExpire())));
        }
        return config;
    }

}

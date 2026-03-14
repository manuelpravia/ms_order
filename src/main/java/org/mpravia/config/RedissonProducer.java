package org.mpravia.config;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.codec.JsonJacksonCodec;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.redisson.codec.TypedJsonJacksonCodec;

@ApplicationScoped
public class RedissonProducer {

    @Inject
    RedisProperties redisProperties;

    @Produces
    @ApplicationScoped
    public RedissonClient redissonClient() {

        Config config = new Config();

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        config.setCodec(new JsonJacksonCodec(mapper));

        config.useSingleServer()
                .setAddress(redisProperties.getHost())
                .setConnectionPoolSize(redisProperties.getPoolSize())
                .setConnectionMinimumIdleSize(redisProperties.getMinIdle())
                .setTimeout(redisProperties.getTimeout())
                .setConnectTimeout(redisProperties.getConnectTimeout());

        return Redisson.create(config);
    }
}
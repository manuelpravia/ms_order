package org.mpravia.config;

import lombok.Data;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
@Data
public class RedisProperties {

    @ConfigProperty(name = "redis.host")
    String host;

    @ConfigProperty(name = "redis.pool.size")
    int poolSize;

    @ConfigProperty(name = "redis.pool.minIdle")
    int minIdle;

    @ConfigProperty(name = "redis.timeout")
    int timeout;

    @ConfigProperty(name = "redis.connectTimeout")
    int connectTimeout;

}

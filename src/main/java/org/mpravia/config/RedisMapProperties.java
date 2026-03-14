package org.mpravia.config;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.Data;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
@Data
public class RedisMapProperties {

    @ConfigProperty(name = "cache.order.name")
    String orderName;

    @ConfigProperty(name = "cache.order.prefix")
    String orderPrefix;

    @ConfigProperty(name = "cache.order.ttl")
    int orderTtl;
}

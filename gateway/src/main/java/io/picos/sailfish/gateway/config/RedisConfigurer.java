package io.picos.sailfish.gateway.config;

import io.picos.sailfish.gateway.cache.CacheEvictService;
import io.picos.sailfish.gateway.impl.cache.*;
import io.picos.sailfish.gateway.impl.cache.event.TokenRevokedEventListener;
import io.picos.sailfish.gateway.impl.cache.event.UserChangedEventListener;
import io.picos.sailfish.gateway.impl.cache.message.TokenRevokedMessageListener;
import io.picos.sailfish.gateway.impl.cache.message.UserChangedMessageListener;
import io.picos.sailfish.gateway.impl.support.DefaultGatewayProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

@Configuration
@EnableCaching
@ConditionalOnProperty(prefix = "gateway", name = "cacheEnabled", havingValue = "true", matchIfMissing = true)
public class RedisConfigurer {

    @Autowired
    private DefaultGatewayProperties gatewayProperties;

    @Bean
    CacheEvictService cacheEvictService() {
        return new CacheEvictServiceImpl();
    }

    @Bean
    TokenRevokedEventListener tokenRevokedEventListener() {
        return new TokenRevokedEventListener();
    }

    @Bean
    UserChangedEventListener userChangedEventListener() {
        return new UserChangedEventListener();
    }

    @Bean
    TokenRevokedMessageListener tokenChangedMessageListener() {
        return new TokenRevokedMessageListener();
    }

    @Bean
    UserChangedMessageListener userChangedMessageListener() {
        return new UserChangedMessageListener();
    }

    @Bean
    @Autowired
    RedisMessageListenerContainer redisMessageListenerContainer(JedisConnectionFactory jedisConnectionFactory) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(jedisConnectionFactory);
        container.addMessageListener(tokenChangedMessageListener(),
                                     new PatternTopic(gatewayProperties.getTokenChangedChannel()));
        container.addMessageListener(userChangedMessageListener(),
                                     new PatternTopic(gatewayProperties.getUserChangedChannel()));
        return container;
    }

}

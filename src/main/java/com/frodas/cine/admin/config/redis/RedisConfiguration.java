package com.frodas.cine.admin.config.redis;

import com.frodas.cine.admin.presentation.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Order(3)
@Configuration
@RequiredArgsConstructor
public class RedisConfiguration {

    @Value("${spring.cache.hostname}")
    private String hostname;
    @Value("${spring.cache.port}")
    private Integer port;
    @Value("${spring.cache.password}")
    private String password;

    @Bean
    JedisConnectionFactory jedisConnectionFactory() {
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration(hostname, port);
        config.setPassword(password);
        return new JedisConnectionFactory(config);
    }

    // GUARDANDO STRING JSON
//    @Bean
//    RedisTemplate<String, ConfigDto> redisConfigTemplate() {
//        RedisTemplate<String, ConfigDto> redisTemplate = new RedisTemplate<>();
//        redisTemplate.setConnectionFactory(jedisConnectionFactory());
//        return redisTemplate;
//    }

    @Bean
    RedisTemplate<String, ConfigDto> redisConfigTemplate() {
        RedisTemplate<String, ConfigDto> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(jedisConnectionFactory());
        return redisTemplate;
    }

    @Bean
    RedisTemplate<String, GeneroDto> redisGeneroTemplate() {
        RedisTemplate<String, GeneroDto> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(jedisConnectionFactory());
        return redisTemplate;
    }

    @Bean
    RedisTemplate<String, ComidaDto> redisComidaTemplate() {
        RedisTemplate<String, ComidaDto> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(jedisConnectionFactory());
        return redisTemplate;
    }

    @Bean
    RedisTemplate<String, PeliculaResDto> redisPeliculaTemplate() {
        RedisTemplate<String, PeliculaResDto> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(jedisConnectionFactory());
        return redisTemplate;
    }

    @Bean
    RedisTemplate<String, RolDto> redisRolTemplate() {
        RedisTemplate<String, RolDto> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(jedisConnectionFactory());
        return redisTemplate;
    }

    @Bean
    RedisTemplate<String, Integer> redisDashboardTemplate() {
        RedisTemplate<String, Integer> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(jedisConnectionFactory());
        return redisTemplate;
    }

}

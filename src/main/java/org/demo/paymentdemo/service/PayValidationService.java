package org.demo.paymentdemo.service;

import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class PayValidationService {

    private static final String REDIS_TOKEN_PREFIX = "token:"; // 각 토큰의 고유한 접두사

    private final StringRedisTemplate stringRedisTemplate;

    public boolean isDuplicatedOrder(String token) {
        String key = REDIS_TOKEN_PREFIX + token;
        boolean exists = Boolean.TRUE.equals(stringRedisTemplate.hasKey(key));
        log.info("Token exists in Redis: {}", exists);
        return exists;
    }

    public boolean isTokenValidForUser(String token, String userId) {
        String key = REDIS_TOKEN_PREFIX + token;
        String storedUserId = stringRedisTemplate.opsForValue().get(key);
        boolean valid = userId.equals(storedUserId);
        log.info("Token is valid for user {}: {}", userId, valid);
        return valid;
    }

    public void saveToken(String token, String cardRefId) {
        String key = REDIS_TOKEN_PREFIX + token;
        stringRedisTemplate.opsForValue().set(key, cardRefId, 1, TimeUnit.SECONDS);
        log.info("Token {} saved for user {} in Redis", token, cardRefId);
    }
}

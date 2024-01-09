package com.committers.snowflowerthon.committersserver.auth.redis;

import com.committers.snowflowerthon.committersserver.common.response.ApiResponse;
import com.committers.snowflowerthon.committersserver.common.response.SuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class RedisController {

    private RedisTemplate<String, String> redisTemplate;

    @PostMapping("/redisTest")
    public ApiResponse<?> addRedisKey() {
        ValueOperations<String, String> vop = redisTemplate.opsForValue();
        vop.set("red", "apple");
        vop.set("yellow", "apple");
        vop.set("green", "watermelon");

        return ApiResponse.success(SuccessCode.SET_REDIS_KEY_SUCCESS);
    }

    @GetMapping("/redisTest/{key}")
    public ApiResponse<?> getRedisKey(@PathVariable String key) {
        ValueOperations<String, String> vop = redisTemplate.opsForValue();
        String value = vop.get(key);
        return ApiResponse.success(SuccessCode.GET_REDIS_KEY_SUCCESS, value);
    }
}
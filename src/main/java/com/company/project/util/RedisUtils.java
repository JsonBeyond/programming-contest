package com.company.project.util;

import com.company.project.core.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;
import redis.clients.jedis.Protocol;
import redis.clients.util.SafeEncoder;

/**
 * @author qxs on 2019/7/20.
 */
@Component
@Slf4j
public class RedisUtils {

    /**
     * 默认过期时长 1天
     */
    public static final long DEFAULT_TIMEOUT = 60 * 60 * 24;

    @Resource
    private StringRedisTemplate stringRedisTemplate;


    public boolean setNx(final String key, final String value, long expire, TimeUnit unit) throws Exception {

        //只能为ex(second)|px(milliseconds)
        String expx = "ex";
        if (unit == TimeUnit.MILLISECONDS) {
            expx = "px";
        } else if (unit == TimeUnit.SECONDS) {
            //pass
        } else if (unit == TimeUnit.MINUTES) {
            expire = expire * 60;
        } else if (unit == TimeUnit.HOURS) {
            expire = expire * 60 * 60;
        } else if (unit == TimeUnit.DAYS) {
            expire = expire * 60 * 60 * 24;
        } else {
            throw new RuntimeException("TimeUnit value error . TimeUnit value is (MILLISECONDS|SECONDS) 。key:" + key + ", value:" + value);

        }
        final String fexpx = expx;
        final long fexpire = expire;

        Object obj = stringRedisTemplate.execute((RedisCallback<Object>) connection ->
                connection.execute("set", new byte[][]{
                        SafeEncoder.encode(key), SafeEncoder.encode(value),
                        SafeEncoder.encode("NX"),
                        SafeEncoder.encode(fexpx), Protocol.toByteArray(fexpire)}));

        return obj != null && obj instanceof byte[] && "ok".equalsIgnoreCase(new String((byte[]) obj));

    }
    public Boolean hasKey(final String key) throws ServiceException {
        try {
            return stringRedisTemplate.hasKey(key);
        } catch (Exception e) {
            log.error("redis exception error:{}", e);
            throw new ServiceException("redis 连接异常");
        }
    }
}

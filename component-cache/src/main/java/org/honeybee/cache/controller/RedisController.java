package org.honeybee.cache.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.redis.util.RedisLockRegistry;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

@RestController
@RequestMapping("/redis")
@Api(tags = "Redis测试相关接口")
@Slf4j
public class RedisController {

    @Autowired
    private RedisLockRegistry redisLockRegistry;

    @GetMapping
    @ApiOperation(value = "测试分布式锁", notes = "依次获取数字")
    public void testLock(@RequestParam("num") Integer num) {
        Lock lock = redisLockRegistry.obtain("lock");
        try {
            boolean isLock = lock.tryLock(5, TimeUnit.SECONDS);     //等待获取锁, 超过5S就放弃
            if(isLock) {
                log.info("获取数值成功,数值是" + num);
            } else {
                log.error("获取数组失败,重试");
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        } finally {
            lock.unlock();      //释放锁
        }
    }

}

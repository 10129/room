package com.jiao.xy99.redis;

import java.util.concurrent.TimeUnit;
import java.util.Map;
import java.util.HashMap;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
/**
 *
 * @ClassName: RedisTest
 * @Description: TODO
 * @author xzh
 * @date 20170323
 *
 */
public class RedisTest extends BaseTest
{
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private JedisPool jedisPool;
    public Map<String,String> student = new HashMap<String,String>();
    /**
     * 测试插入与获取Redis的数据
     * @Title: testPutAndGet
     * @Description: TODO
     * @throws
     */
    @Test
    public void testPutAndGet()
    {
        System.out.println(this.getClass().getName()+"testPutAndGet");
        redisTemplate.opsForHash().put("user", "name", "rhwayfun");
        Object object = redisTemplate.opsForHash().get("user", "name");
        System.out.println(object);
    }
    /**
     * 测试Redis作为缓存的例子
     * @Title: testCache
     * @Description: TODO
     * @throws InterruptedException
     * @throws
     */
    @Test
    public void testCache() throws InterruptedException
    {
        //-------jedisPool配置使用--redisTemplate--进行操作Start-------------//
        System.out.println("-------jedisPool配置使用--redisTemplate--进行操作Start-------------");
        // 插入一条数据
        redisTemplate.opsForHash().put("user", "name", "rhwayfun");
        // 设置失效时间为6秒
        redisTemplate.expire("user", 6, TimeUnit.SECONDS);
        // 3秒后获取
        Thread.sleep(3000);
        Object object = redisTemplate.opsForHash().get("user", "name");
        System.out.println("3秒后：" + object);
        // 7秒后获取
        Thread.sleep(4000);
        object = redisTemplate.opsForHash().get("user", "name");
        System.out.println("7秒后：" + object);
        System.out.println("-------jedisPool配置使用--redisTemplate--进行操作end---------------\n");
        //-------jedisPool配置使用--redisTemplate--进行操作end-------------//

        //-------jedisPool配置使用----Jedis----进行操作Start-------------//
        System.out.println("-------jedisPool配置使用Jedis的进行操作Start-------------");
        Jedis jedis = jedisPool.getResource();
        System.out.println("服务器IP地址:"+jedis.configGet("bind"));
        /*hash api:hset*/
        jedis.hset("me", "name", "xzh");
        System.out.println("jedis.hset(me, name, xzh):" + jedis.hget("me", "name"));

        /*hash api:hmset*/
        student.put("naem","xzh");
        student.put("stid", "1");
        jedis.hmset("student", student);

        //关闭连接
        if(jedis != null)
        {
            student.clear();
            jedisPool.close();
        }
        System.out.println("-------jedisPool配置使用Jedis的进行操作end--------------\n");
        //-------jedisPool配置使用----Jedis----进行操作end-------------//
    }
}

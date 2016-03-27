package com.vs2.microblog;

import com.vs2.microblog.dao.UserDaoRedis;
import com.vs2.microblog.dao.api.UserDao;
import com.vs2.microblog.entity.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JacksonJsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import sun.applet.resources.MsgAppletViewer_ko;

/**
 * Created by Walde on 20.03.16.
 */
@SpringBootApplication
public class Application {

    public static void main(String args[]) {
        SpringApplication.run(Application.class, args);
    }

    @Bean(name="jedisConnFactory")
    public JedisConnectionFactory jedisConnectionFactory() {
        JedisConnectionFactory factory = new JedisConnectionFactory();
        factory.setUsePool(true);

        return factory;
    }

    @Bean(name="userRedisTemplate")
    public RedisTemplate<String, User> stringUserRedisTemplate() {
        RedisTemplate<String, User> template = new RedisTemplate<String, User>();
        template.setConnectionFactory(jedisConnectionFactory());
        template.setKeySerializer(new StringRedisSerializer());

        return template;
    }

    @Bean
    public UserDao userDaoRedis() {
        return new UserDaoRedis();
    }
}

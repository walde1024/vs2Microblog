package com.vs2.microblog;

import com.vs2.microblog.controller.session.SessionManager;
import com.vs2.microblog.controller.utils.UserUtils;
import com.vs2.microblog.dao.MessageDaoRedis;
import com.vs2.microblog.dao.UserDaoRedis;
import com.vs2.microblog.dao.api.MessageDao;
import com.vs2.microblog.dao.api.UserDao;
import com.vs2.microblog.entity.User;
import com.vs2.microblog.security.SecurityExcludeConfiguration;
import com.vs2.microblog.view.TimelineView;
import com.vs2.microblog.view.provider.TimelineViewProvider;
import com.vs2.microblog.view.provider.UserProfileViewProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.data.redis.config.annotation.web.http.RedisHttpSessionConfiguration;
import org.springframework.session.web.http.SessionRepositoryFilter;
import org.springframework.web.filter.DelegatingFilterProxy;

import java.util.Arrays;


/**
 * Created by Walde on 20.03.16.
 * https://dzone.com/articles/externalizing-session-state
 */
@SpringBootApplication
@EnableRedisHttpSession
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

    @Bean
    public RedisHttpSessionConfiguration redisHttpSessionConfiguration() {
        return new RedisHttpSessionConfiguration();
    }

    @Bean(name="userRedisTemplate")
    public RedisTemplate<String, User> stringUserRedisTemplate() {
        RedisTemplate<String, User> template = new RedisTemplate<String, User>();
        template.setConnectionFactory(jedisConnectionFactory());
        template.setKeySerializer(new StringRedisSerializer());

        return template;
    }

    @Bean
    @Order(value = 0)
    public FilterRegistrationBean sessionRepositoryFilterRegistration(SessionRepositoryFilter springSessionRepositoryFilter) {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new DelegatingFilterProxy(springSessionRepositoryFilter));
        filterRegistrationBean.setUrlPatterns(Arrays.asList("/*"));

        return filterRegistrationBean;
    }

    @Bean
    @Order(value = 1)
    public FilterRegistrationBean securityFilterRegistration(SessionRepositoryFilter securityRepositoryFilter) {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new DelegatingFilterProxy(securityRepositoryFilter));
        filterRegistrationBean.setUrlPatterns(Arrays.asList("/*"));

        return filterRegistrationBean;
    }

    @Bean
    public SecurityExcludeConfiguration securityExcludeConfiguration() {
        return new SecurityExcludeConfiguration(Arrays.asList("/webjars", "/css", "/login", "/register"));
    }

    @Bean
    public UserDao userDaoRedis() {
        return new UserDaoRedis();
    }

    @Bean
    public MessageDao messageDaoRedis() {
        return new MessageDaoRedis();
    }

    @Bean
    public TimelineViewProvider timelineViewProvider() {
        return new TimelineViewProvider();
    }

    @Bean
    public UserProfileViewProvider userProfileViewProvider() {
        return new UserProfileViewProvider();
    }

    @Bean
    public SessionManager sessionManager() {
        return new SessionManager();
    }

    @Bean
    public UserUtils userUtils() {
        return new UserUtils();
    }
}

package io.javabrains;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.client.RestTemplate;

import io.javabrains.model.User;

@SpringBootApplication
@EnableCaching
public class CourseApi3Application extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(CourseApi3Application.class, args);
	}
	
	
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(CourseApi3Application.class); 
	} 
	
	@Bean
	JedisConnectionFactory jedisConnectionFactory() {
		return new JedisConnectionFactory();
		
	}
	
	@Bean
	RedisTemplate<String, User> redisTemplate() {
		RedisTemplate<String, User> redisTemplate = new RedisTemplate();
		redisTemplate.setConnectionFactory(jedisConnectionFactory());
		return redisTemplate;
		
	}
	
	@Bean
//	@LoadBalanced
	public RestTemplate getRestTemplate() {
	  	return new RestTemplate() ;
	 } 


}

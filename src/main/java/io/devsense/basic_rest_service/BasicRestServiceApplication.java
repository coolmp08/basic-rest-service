package io.devsense.basic_rest_service;

import io.devsense.basic_rest_service.domain.model.Quote;
import io.devsense.basic_rest_service.messaging.Receiver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.lang.NonNull;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@EnableScheduling
public class BasicRestServiceApplication {

	private static final Logger log = LoggerFactory.getLogger(BasicRestServiceApplication.class);

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(@NonNull CorsRegistry registry) {

				registry.addMapping("/greeting-javaconfig").allowedOrigins("http://localhost:9000");
			}
		};
	}

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder){
		return builder.build();
	}

	@Bean
	@Profile("!test")
	public CommandLineRunner run(RestTemplate restTemplate) throws Exception{
		return args -> {
			Quote quote = restTemplate.getForObject("http://localhost:8080/api/random", Quote.class);
			log.info( quote.toString());
		};
	}

	@Bean
	RedisMessageListenerContainer redisMessageListenerContainer(RedisConnectionFactory redisConnectionFactory,
																MessageListenerAdapter messageListenerAdapter){
		RedisMessageListenerContainer container = new RedisMessageListenerContainer();
		container.setConnectionFactory(redisConnectionFactory);
		container.addMessageListener(messageListenerAdapter, new PatternTopic("chitchat"));

		return container;
	}

	@Bean
	MessageListenerAdapter messageListenerAdapter(Receiver receiver){
		return  new MessageListenerAdapter(receiver,"receiveMessage");
	}

	@Bean
	Receiver receiver(){
		return  new Receiver();
	}

	@Bean
	StringRedisTemplate stringRedisTemplate(RedisConnectionFactory redisConnectionFactory){
		return new StringRedisTemplate(redisConnectionFactory);
	}

	public static void main(String[] args) throws InterruptedException {

		ApplicationContext context = SpringApplication.run(BasicRestServiceApplication.class, args);
		StringRedisTemplate stringRedisTemplate = context.getBean(StringRedisTemplate.class);
		Receiver receiver = context.getBean(Receiver.class);
//		RedisMessageListenerContainer redisMessageListenerContainer = context.getBean(RedisMessageListenerContainer.class);

		while (receiver.getCount() == 0){
			log.info("Sending message on redis");
			stringRedisTemplate.convertAndSend("chitchat", "Hello from Redis! ");
			Thread.sleep(500);
		}

		System.exit(0);
	}
}

package ru.itis.pdfserver.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@EnableRabbit
@Configuration
public class RabbitMQConfiguration {

    public static final String SELL_QUEUE_NAME = "sell_queue";
    public static final String BUY_QUEUE_NAME = "buy_queue";
    public static final String REPLY_QUEUE_NAME = "pdf-reply";

    @Value("${rabbit.connection.port}")
    private int port;

    @Value("${rabbit.connection.host}")
    private String host;

    @Bean
    public ConnectionFactory connectionFactory() {
        return new CachingConnectionFactory(host, port);
    }

    @Bean
    public Queue sellQueue() {
        return new Queue(SELL_QUEUE_NAME, false);
    }

    @Bean
    public Queue buyQueue() {
        return new Queue(BUY_QUEUE_NAME, false);
    }

    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

}

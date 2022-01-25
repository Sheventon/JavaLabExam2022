package ru.itis.pdfclient.configuration;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfiguration {

    public static final String EXCHANGE_NAME = "pdf-exchange";

    public static final String SELL_QUEUE_NAME = "sell_queue";
    public static final String BUY_QUEUE_NAME = "buy_queue";

    @Value("${spring.rabbitmq.port}")
    private int port;

    @Value("${spring.rabbitmq.host}")
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
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    @Bean
    public Binding sellBinding(Queue sellQueue, TopicExchange exchange) {
        return BindingBuilder.bind(sellQueue).to(exchange).with(sellQueue.getName());
    }

    @Bean
    public Binding buyBinding(Queue buyQueue, TopicExchange exchange) {
        return BindingBuilder.bind(buyQueue).to(exchange).with(buyQueue.getName());
    }

    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}

package br.com.alloha.rabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.amqp.support.converter.SimpleMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableRabbit
@Configuration
public class RabbitMQConfig {

    public static final String FILA = "fila_queue";
    public static final String EXCHANGE = "fila_exchange";
    public static final String ROUTING_KEY = "routingKey";

    @Bean
    public Queue fila() {        
        return new Queue(FILA);
    }

    @Bean
    // Faz roteamento das mensagens para outras filas
    public TopicExchange exchange() {        
        return new TopicExchange(EXCHANGE);
    }

    @Bean
    // Binding conecta a fila_queue até a fila_exchange usando a routingKey
    public Binding binding(Queue fila, TopicExchange exchange) {
        return BindingBuilder.bind(fila).to(exchange).with(ROUTING_KEY);
    }

    @Bean
    public MessageConverter conversorMensagem() {
        SimpleMessageConverter converter = new SimpleMessageConverter();
        converter.addAllowedListPatterns(
                "br.com.alloha.rabbitmq.dto.*",
                "br.com.alloha.rabbitmq.model.*",
                "br.com.alloha.rabbitmq.enums.*",
                "java.lang.*",
                "java.time.*",
                "java.util.*"
        );
        return converter;
    }

    @Bean
    public AmqpTemplate template(ConnectionFactory connectionFactory) {
        // RabbitTemplate publica as mensagens no RabbitMQ
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(conversorMensagem());
        return rabbitTemplate;
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
            ConnectionFactory connectionFactory, MessageConverter messageConverter) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(messageConverter);
        return factory;
    }
}

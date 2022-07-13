package com.thinkmicroservices.currency.exchange.client.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class CurrencyExchangeConfig {

    @Value("${spring.rabbitmq.template.reply-timeout}")
    private long replyTimeout;
    
    public static final String REQUEST_MESSAGE_QUEUE = "currency_msg_queue";
    public static final String REPLY_MESSAGE_QUEUE = "currency_reply_msg_queue";
    public static final String EXCHANGE = "currency_exchange";
    
    /* outbound request message queue*/
    @Bean
    Queue msgQueue() {

        return new Queue(REQUEST_MESSAGE_QUEUE);
    }
    
    /* reply queue  */
    @Bean
    Queue replyQueue() {

        return new Queue(REPLY_MESSAGE_QUEUE);
    }
     /* exchange */
    @Bean
    TopicExchange exchange() {

        return new TopicExchange(EXCHANGE);
    }
 
     
    /* bind request queue */
    @Bean
    Binding msgBinding() {

        return BindingBuilder.bind(msgQueue()).to(exchange()).with(REQUEST_MESSAGE_QUEUE);
    }
    
    /* bind reply queue */
    @Bean
    Binding replyBinding() {

        return BindingBuilder.bind(replyQueue()).to(exchange()).with(REPLY_MESSAGE_QUEUE);
    }
    
    /* configure rabbit template     */
    @Bean
    RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {

        
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setReplyAddress(REPLY_MESSAGE_QUEUE);
        log.info("rabbitMQ reply timeout set to:{} msec.",replyTimeout);
        template.setReplyTimeout(replyTimeout);
        
        return template;
    }
    
    /* configure reply queue listener */
    @Bean
    SimpleMessageListenerContainer replyContainer(ConnectionFactory connectionFactory) {

        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(REPLY_MESSAGE_QUEUE);
        container.setMessageListener(rabbitTemplate(connectionFactory));
        return container;
    }
}

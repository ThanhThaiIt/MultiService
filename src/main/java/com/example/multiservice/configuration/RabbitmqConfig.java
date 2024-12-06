package com.example.multiservice.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.amqp.core.*;


@Configuration
public class RabbitmqConfig {

    // email
    public static final String ACTIVATE_ACCOUNT_EXCHANGE = "active_account_exchange";
    public static final String ACTIVATE_ACCOUNT_QUEUE = "active_account_queue";
    public static final String ACTIVATE_ACCOUNT_ROUTING_KEY = "/active_account_routing_key";


    // notification

    public static final String NOTIFICATION_EXCHANGE = "notification_exchange";
    public static final String NOTIFICATION_QUEUE = "notification_queue";
    public static final String NOTIFICATION_ROUTING_KEY = "/notification_routing_key";

    @Bean
    public Queue activeAccountQueue() {
        return new Queue(ACTIVATE_ACCOUNT_QUEUE, true);
    }
    @Bean
    public Queue notificationQueue() {
        return new Queue(NOTIFICATION_QUEUE, true);
    }

    @Bean
    public Exchange activeAccountExchange() {
        return new DirectExchange(ACTIVATE_ACCOUNT_EXCHANGE);
    }
    @Bean
    public Exchange notificationExchange() {
        return new TopicExchange(NOTIFICATION_EXCHANGE);
    }

    @Bean Binding activeAccountBinding() {
        return BindingBuilder.bind(activeAccountQueue()).to(activeAccountExchange()).with(ACTIVATE_ACCOUNT_ROUTING_KEY).noargs();
    }
    @Bean Binding notificationBinding() {
        return BindingBuilder.bind(notificationQueue()).to(notificationExchange()).with(NOTIFICATION_ROUTING_KEY).noargs();
    }


}

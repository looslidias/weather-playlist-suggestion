package com.looslidias.playlistsuggestion.core.config;

import com.looslidias.playlistsuggestion.core.properties.queue.ActiveMQProperties;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.util.StringUtils;

import javax.jms.ConnectionFactory;

/**
 * @author Created by Rafael Loosli Dias (rafaldias@gmail.com) on 23/09/18
 */
@Configuration
@EnableJms
public class ActiveMQConfig {

    @Autowired
    private ActiveMQProperties activeMQProperties;

    @Bean
    public ActiveMQConnectionFactory connectionFactory() {
        if (StringUtils.isEmpty(activeMQProperties.getUser())) {
            return new ActiveMQConnectionFactory(activeMQProperties.getBrokerUrl());
        }
        return new ActiveMQConnectionFactory(activeMQProperties.getUser(), activeMQProperties.getPassword(), activeMQProperties.getBrokerUrl());
    }

    @Bean
    public JmsTemplate jmsTemplate() {
        return new JmsTemplate(connectionFactory());
    }
}

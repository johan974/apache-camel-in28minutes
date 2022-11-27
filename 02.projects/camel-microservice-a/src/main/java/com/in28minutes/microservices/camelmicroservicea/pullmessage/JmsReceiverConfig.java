package com.in28minutes.microservices.camelmicroservicea.pullmessage;

import org.apache.activemq.spring.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.connection.JmsTransactionManager;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableJms
public class JmsReceiverConfig {

  @Value("${spring.activemq.broker-url}")
  private String brokerUrl;

  @Bean
  public JmsTemplate jmsTemplate() {
    JmsTemplate jmsTemplate = new JmsTemplate(cachingConnectionFactory());
    jmsTemplate.setSessionTransacted(true);
    return jmsTemplate;
  }

  @Bean
  public CachingConnectionFactory cachingConnectionFactory() {
    ActiveMQConnectionFactory activeMQConnectionFactory = activeMQConnectionFactory();
    CachingConnectionFactory connectionFactory = new CachingConnectionFactory( activeMQConnectionFactory);
    return connectionFactory;
  }

  @Bean
  public PlatformTransactionManager transactionManager() {
    JmsTransactionManager transactionManager = new JmsTransactionManager();
    transactionManager.setConnectionFactory(activeMQConnectionFactory());
    return transactionManager;
  }

  @Bean
  public ActiveMQConnectionFactory activeMQConnectionFactory() {
    ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory();
    activeMQConnectionFactory.setBrokerURL(brokerUrl);
    activeMQConnectionFactory.setTrustAllPackages(true);
    return activeMQConnectionFactory;
  }

  @Bean
  public MessageConverter jacksonJmsMessageConverter() {
    MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
    converter.setTargetType(MessageType.TEXT);
    converter.setTypeIdPropertyName("_type");
    return converter;
  }

}

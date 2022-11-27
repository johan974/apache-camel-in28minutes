package com.in28minutes.microservices.camelmicroservicea.pullmessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.jms.Message;
import java.time.LocalDateTime;

@Component
public class ProcessOneMessage {
  Logger logger = LoggerFactory.getLogger(ProcessOneMessage.class);

  public static final String ENTRY_QUEUE = "entry-queue";
  public static final String SUCCESS_QUEUE = "success-queue";
  public static final String ERROR_QUEUE = "error-queue";

  private JmsTemplate jmsTemplate;
  private int invocationCounter = 0;

  @Autowired
  public ProcessOneMessage(JmsTemplate jmsTemplate) {
    this.jmsTemplate = jmsTemplate;
  }

  @Transactional
  public void processingMessage() throws FailingMessageProcessingException {
    invocationCounter++;
    Message message = jmsTemplate.receive( ENTRY_QUEUE);
    logger.info( "Incoming message {} with invocation {}", message, invocationCounter);
    switch( invocationCounter % 5) {
      case 0:
        throw new FailingMessageProcessingException( "Error in message with invocation " + invocationCounter);
      case 1,2:
        jmsTemplate.convertAndSend(SUCCESS_QUEUE, "test success queue");
        break;
      case 3,4:
        jmsTemplate.convertAndSend(ERROR_QUEUE, "test error body");
        break;
    }
  }
}

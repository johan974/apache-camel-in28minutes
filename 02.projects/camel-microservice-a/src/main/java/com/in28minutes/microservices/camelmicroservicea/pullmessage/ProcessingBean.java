package com.in28minutes.microservices.camelmicroservicea.pullmessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProcessingBean {
  Logger logger = LoggerFactory.getLogger(ProcessingBean.class);
  private int maxRunningCounter = 30;
  private ProcessOneMessage processOneMessage;

  @Autowired
  public ProcessingBean(ProcessOneMessage processOneMessage) {
    this.processOneMessage = processOneMessage;
  }

  public void startProcessingMessages() {
    while (maxRunningCounter-- > 0) {
      logger.info("Going to process a message");
      try {
        Thread.sleep(1000);
        processOneMessage.processingMessage();
      } catch (FailingMessageProcessingException e) {
        logger.info("Failed message processing exception. Message should stay on the queue.. ");
      } catch (InterruptedException e) {
        logger.error("Interrupted!");
        Thread.currentThread().interrupt();
      }
    }

  }
}

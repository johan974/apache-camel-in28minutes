package com.in28minutes.microservices.camelmicroservicea.pullmessage;

import org.apache.camel.Exchange;
import org.apache.camel.Expression;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@Component
public class PullMessageInfra extends RouteBuilder {

  @Override
  public void configure() throws Exception {
    AtomicInteger messageNumber = new AtomicInteger(0);

    errorHandler( deadLetterChannel( "activemq:dead-letter-queue"));

    // Generator of messages
    from("timer:active-mq-timer?period=3000")
            .transform(new Expression() {
              public <T> T evaluate(Exchange exchange, Class<T> type) {
                return (T) ("Message_" + messageNumber.incrementAndGet());
              }
            })
            .to("activemq:entry-queue");

    from( "activemq:success-queue")
            .to( "log:success-queue");
    from( "activemq:error-queue")
            .to( "log:error-queue");
    from( "activemq:dead-letter-queue")
            .to( "log:dead-letter-queue");
  }

}




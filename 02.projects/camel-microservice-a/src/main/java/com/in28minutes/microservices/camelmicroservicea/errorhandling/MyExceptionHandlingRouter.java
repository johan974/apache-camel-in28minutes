package com.in28minutes.microservices.camelmicroservicea.errorhandling;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class MyExceptionHandlingRouter extends RouteBuilder {
  Logger logger = LoggerFactory.getLogger(MyExceptionHandlingRouter.class);

  @Override
  public void configure() {
    // SECOND WAY
//    from("timer:first-timer?period=3000&repeatCount=1")
//            .transform().constant("{\"name\":\"My Constant Message\"")
//            .log("ERROR handling: before: ${body}")
//            .doTry()
//            .unmarshal().json(JsonLibrary.Jackson, SimpleInteger.class)
//            .log( "Never coming here: ${body}")
//            .doCatch( Exception.class)
//            .process( new Processor() {
//              @Override
//              public void process(Exchange exchange) throws Exception {
//                logger.info( "Yes! Should come here!");
//              }
//            })
//            .log("ERROR handling: after: ${body}")
//            .to( "log:my-logger");

    // SECOND WAY
//    onException(Exception.class)
//            .process(new Processor() {
//              @Override
//              public void process(Exchange exchange) throws Exception {
//                logger.info("Yes! Should come here!");
//              }
//            }).handled(true);
//
//    from("timer:first-timer?period=3000&repeatCount=2")
//            .transform().constant("{\"name\":\"My Constant Message\"")
//            .log("ERROR handling: before: ${body}")
//            .unmarshal().json(JsonLibrary.Jackson, SimpleInteger.class)
//            .log("ERROR handling: after: ${body}")
//            .to("log:my-logger");

    // RETRING on exception
//    onException(Exception.class)
//            .process(new Processor() {
//              @Override
//              public void process(Exchange exchange) throws Exception {
//                logger.info("Exception catched!! Yes! Should come here!");
//              }
//            }).handled(true)
//            .maximumRedeliveries(3).redeliveryDelay(0);
//
//    from("timer:first-timer?period=3000&repeatCount=1")
//            .log("Starting: ...")
//            .to("direct:sub")
//            .log("Ending");
//
//    from("direct:sub")
//            .errorHandler(noErrorHandler())
//            .transform().constant("{\"name\":\"My Constant Message\"")
//            .log("Before: ${body}")
//            .unmarshal().json(JsonLibrary.Jackson, SimpleInteger.class)
//            .log("After: ${body}")
//            .to("log:my-logger");

    onException(Exception.class)
            .useOriginalMessage()
            .redeliveryDelay(1000)
            .backOffMultiplier(1.5)
            .maximumRedeliveries(3)
            .retryAttemptedLogLevel(LoggingLevel.WARN)
            .useExponentialBackOff()
            .process(new Processor() {
              @Override
              public void process(Exchange exchange) throws Exception {
                logger.info("Exception catched!! Yes! Should come here!");
              }
            }).handled(true);

    from("timer:first-timer?period=3000&repeatCount=1")
            .log("Starting: ...")
            .transform().constant("{\"name\":\"My Constant Message\"")
            .log("Before: ${body}")
            .unmarshal().json(JsonLibrary.Jackson, SimpleInteger.class)
            .log("After: ${body}")
            .to("log:destination");

  }

}

class SimpleInteger {
  int name = 0;

  public SimpleInteger() {
  }

  public SimpleInteger(int name) {
    this.name = name;
  }

  public void setName(int name) {
    this.name = name;
  }
}

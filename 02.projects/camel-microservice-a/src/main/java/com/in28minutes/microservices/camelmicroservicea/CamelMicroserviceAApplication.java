package com.in28minutes.microservices.camelmicroservicea;

import com.in28minutes.microservices.camelmicroservicea.pullmessage.ProcessingBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class CamelMicroserviceAApplication implements CommandLineRunner {
  Logger logger = LoggerFactory.getLogger(CamelMicroserviceAApplication.class);

//  @Autowired
//  private ProcessingBean processingBean;

  public static void main(String[] args) {
    SpringApplication.run(CamelMicroserviceAApplication.class, args);
  }

  @Override
  public void run(String... args) {
    logger.info("Starting the JMS receiver testing ...TODO ... skipping ");
//	TODO: 	processingBean.startProcessingMessages();
  }

}

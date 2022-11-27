package com.in28minutes.microservices.camelmicroservicea.pullmessage;

public class FailingMessageProcessingException extends Exception {
  public FailingMessageProcessingException( String message) {
    super( message);
  }
}

package com.in28minutes.microservices.camelmicroservicea.pullmessage;

public class RecoverableMessageProcessingException extends Exception {
  public RecoverableMessageProcessingException(String message) {
    super( message);
  }
}

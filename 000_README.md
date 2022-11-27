# Purpose
Purpose of my Camel / Pullmessage (package) is to see whether a jmsTemplate.receive() will
put the messages back on the queue when an Exception is thronw in a Transactional 
method. 

# Setup
- See the 'docker' folder. See the 'data' folder for how to start Docker fast
with an ActiveMQ environment. 
- Go to 02 projects / camel-microservice-a
- Go to the 'pullmessage' folder for the test files. 
- Start the app and you see the messages logged. 
- Browse to the http://localhost:8161/index.html and to see the ActiveMQ dashboard / queues. 

# Monomart

Monomart is a sample application written with Spring Boot that is intended to serve as a medium in which to teach and exercise the decomposition of a monolith into separate services.   

## Components

This version of monomart is no longer a monolith.  It has the following components:

### API Gateway (agw)
This is a Spring Cloud Gateway application that serves as a reverse proxy to the applications and services that exist within the monomart ecosystem.  It is also configured to use a config server

### API Gateway Config Server (config-server)
This provides the gateway with its configurations.  This is just the server, not the actual configurations.

### Configuration
The configuration yaml files that the Config Server uses are hosted in https://github.com/gballer77/monomart-config

### Frontend (frontend)
This hosts the frontend React application

### Inventory Service (inventory)
Inventory service is one of the microservices that implements the "Inventory" domain.  This consists of the products, their information and quantity, as well as their category.

### Inventory Library (inventory-lib)
Inventory library contains the objects shared by the Inventory domain with the commerce domain.

### Commerce Service (commerce)
The Commerce service is another microservice that implements the "Commerce" domain.  this consists of the shopping cart, and purchases.

## How the app works
When a purchase is made, the commerce service sends a rabbitMQ message to the inventory service to let it know that a product was purchased.  Once the inventory service receives this message, it decrements the number of available quantity for that product that was purchased.

### Message Producer (Commerce Service)
The producing service, commerce, has a couple event related configurations that are important to look at:
* Added **amqp** and **json** libraries to [build.gradle](https://github.com/gballer77/monomart/blob/f1ec26ff283b10be5026dc80e701e0bd1e43f1bd/commerce/build.gradle#L31)
* Added [EventBusConfig](https://github.com/gballer77/monomart/blob/f1ec26ff283b10be5026dc80e701e0bd1e43f1bd/commerce/src/main/java/mart/mono/commerce/confiig/EventBusConfig.java) to instantiate and configure beans for publishing json messages to RabbitMQ
* Added the RabbitTemplate publish code to [ProductService](https://github.com/gballer77/monomart/blob/f1ec26ff283b10be5026dc80e701e0bd1e43f1bd/commerce/src/main/java/mart/mono/commerce/product/ProductService.java#L45)

### Message Consumer (Inventory Service)
The consuming service, inventory, had the following relevant changes:
* Added **amqp** and **json** libraries to [build.gradle](https://github.com/gballer77/monomart/blob/f1ec26ff283b10be5026dc80e701e0bd1e43f1bd/inventory/build.gradle#L31)
* Added [EventBusConfig](https://github.com/gballer77/monomart/blob/b1c215c5956527f0c0a07363766e56152314abb3/inventory/src/main/java/mart/mono/inventory/config/EventBusConfig.java) to instantiate and configure beans for consuming json messages from RabbitMQ.  It is also responsible to tell RabbitMQ what exchanges, queues, and bindings to create, which it does with `Declarables`.
* Created a [ProductEventController](https://github.com/gballer77/monomart/blob/f1ec26ff283b10be5026dc80e701e0bd1e43f1bd/inventory/src/main/java/mart/mono/inventory/product/ProductEventController.java) and added `@RabbitListener` annotation to a function that acts as a listener callback.  This function handles any incoming messages.

## To run the application

First build your applications by running 

```shell
./buildScript.sh
```

After that is complete run the following

```shell
docker-compose up
```

This will start up:
* RabbitMQ
* API Gateway
* Config Server
* Frontend
* Log Streams sink for debugging

It will not start up
* Inventory Service
* Commerce Service

So to start these, run the following in separate terminals

```shell
./inventory/gradlew bootrun
```

and

```shell
./commerce/gradlew bootrun
```

Once everything is online you can navigate to

[http://localhost:8888](http://localhost:8888) to get to the Application

You can test the event send by 
1) add an item to cart
2) click the cart button in the top right hand corner of the screen
3) click checkout

This will send a message from commerce to inventory.  you will also see the logger-sink docker container write a log of the message payload
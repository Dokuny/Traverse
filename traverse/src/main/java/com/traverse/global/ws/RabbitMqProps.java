package com.traverse.global.ws;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring.rabbitmq")
public record RabbitMqProps (
	String host,
	Integer port,
	String username,
	String password
){

}

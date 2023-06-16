package com.example.demo;

import net.spy.memcached.ConnectionFactory;
import net.spy.memcached.ConnectionFactoryBuilder;
import net.spy.memcached.MemcachedClient;
import net.spy.memcached.auth.AuthDescriptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Collections;

/**
 * Class
 *
 * @author Tung Dang
 * @function_Id:
 * @screen_ID:
 */
@Configuration
public class MemcachedConfiguration {

  @Value("${spring.memcached.host}")
  private String memcachedHost;

  @Value("${spring.memcached.port}")
  private String memcachedPort;

  @Bean
  public MemcachedClient memcachedClient() throws IOException {
    ConnectionFactory connectionFactory = new ConnectionFactoryBuilder()
        .setProtocol(ConnectionFactoryBuilder.Protocol.TEXT)
//        .setAuthDescriptor(AuthDescriptor.typical("username", "password"))
        .build();
    return new MemcachedClient(connectionFactory,
        Collections.singletonList(new InetSocketAddress(memcachedHost, Integer.parseInt(memcachedPort))));
  }
}

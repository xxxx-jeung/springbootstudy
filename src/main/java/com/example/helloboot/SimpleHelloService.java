package com.example.helloboot;

import org.springframework.stereotype.Component;

public class SimpleHelloService implements HelloService {
  @Override
  public String sayHello(String name) {
    return "Hello " + name;
  }

}

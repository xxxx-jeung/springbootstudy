package com.example.helloboot;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;



@RestController
public class HelloController {
  private final HelloService helloService;

  public HelloController(HelloService helloService) {
    this.helloService = helloService;
  }

  /**
   * 서블릿 컨테이너인 웹 어플리케이션 컨텍스트를 생성자로 받았고, 디스팩처 서블릿이 Bean 을 다 뒤진다.
   * 요청 -> 디스팩처 서블릿 -> 여기서 웹 요청을 처리할 수 있는 Bean 이 있는지 찾는다.
   * @param name
   * @return
   */
  @GetMapping
  public String hello(String name) {
    return helloService.sayHello(Objects.requireNonNull(name));
  }
}

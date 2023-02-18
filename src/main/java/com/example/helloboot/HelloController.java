package com.example.helloboot;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
public class HelloController implements ApplicationContextAware {
  private final HelloService helloService;
  private ApplicationContext applicationContext;

  public HelloController(HelloService helloService) {
    this.helloService = helloService;
  }

  /**
   * 서블릿 컨테이너인 웹 어플리케이션 컨텍스트를 생성자로 받았고, 디스팩처 서블릿이 Bean 을 다 뒤진다.
   * 요청 -> 디스팩처 서블릿 -> 여기서 웹 요청을 처리할 수 있는 Bean 이 있는지 찾는다.
   * @param name
   * @return
   */
  @GetMapping("/hello")
  public String hello(String name) {
    if(name == null || name.trim().length() == 0) throw new IllegalArgumentException();

    return helloService.sayHello(Objects.requireNonNull(name));
  }

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    System.out.println("applicationContext = " + applicationContext);
    this.applicationContext = applicationContext;
  }
}

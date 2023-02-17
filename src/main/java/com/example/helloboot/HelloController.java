package com.example.helloboot;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;


/**
 * 디스팩처 서블릿이 매핑 메소드를 일일이 찾을 수 있지만 그 만큼 시간이 오래걸린다.
 * 따라서 매핑 클래스용 이라고 클래스에다 애노테이션으로 명시해 놓으면 디스팩처 서블릿이 매핑 클래스를 찾고 매핑 메소드를 찾아낸다.
 */
@RequestMapping("/hello")
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
  @ResponseBody
  public String hello(String name) {
    return helloService.sayHello(Objects.requireNonNull(name));
  }
}

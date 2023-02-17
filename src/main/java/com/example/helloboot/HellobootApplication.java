package com.example.helloboot;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServer;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

@Configuration
@ComponentScan
public class HellobootApplication {

  /** Bean으로 빼서 유연하게 서버구성을 변경할 수 있도록 할 수 있다. */
  @Bean
  public ServletWebServerFactory servletWebServerFactory() {
    return new TomcatServletWebServerFactory();
  }

  @Bean
  public DispatcherServlet dispatcherServlet() {
    return new DispatcherServlet(); // 웹어플리케이션 컨텍스트를 주입해줘야 하는데, 밖에다 Bean 등록 해 놓으면 사용 할 수 없다.
    // 가능했던 이유는 스프링 컨테이너가 필요성을 알고 주입해주기 때문
    // 이해하기 위해선 Bean Life Cycling 을 이해할 필요가 있다.
    // ApplicationContextAware
    // Bean 컨테이너가 등록하고 관리하는 도중에 Bean 을 주입해주고 관리해주는 역할을 한다.
    // 컨테이너에 등록되어 있으면 구현된 Setter 메서드를 통해 주입을 해준다.
  }

  public static void main(String[] args) {
    MySpringApplication.run(HellobootApplication.class, args);
  }


}

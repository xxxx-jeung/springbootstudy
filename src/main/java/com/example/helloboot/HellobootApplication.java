package com.example.helloboot;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServer;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.context.support.GenericWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HellobootApplication {

  public static void main(String[] args) {
    GenericWebApplicationContext applicationContext = new GenericWebApplicationContext(); // 어플리케이션 컨택스트의 종류는 매우 많다. 상황에 따라 바꿔가며 사용하면 되는데 디스팩쳐 서블릿을 사용하기 위해선 Web 어플리케이션 컨택스트를 사용해야 한다.
    applicationContext.registerBean(HelloController.class);
    applicationContext.registerBean(SimpleHelloService.class);
    applicationContext.refresh();

    ServletWebServerFactory tomcatServletWebServerFactory = new TomcatServletWebServerFactory();
    WebServer webServer =
        tomcatServletWebServerFactory.getWebServer(
            servletContext -> {
              // 프론트 컨트롤러 방식을 Spring 에서 제공하는 Servlet 을 사용하면 편리하다
              servletContext
                  .addServlet(
                      "dispatcherServlet ", new DispatcherServlet(applicationContext)) // 에러가 발생하는데, 디스팩처 서블릿에게 요청을 받을 때 어떤 컨트롤러에게 전달해줘야 하는지 힌트를 하나도 주지 않은 상태, 이러한 작업을 xml 로 했지만 지금은 @RequestMapping 을 통해 명시해준다.
                  .addMapping("/*");
            });
    webServer.start();
  }
}

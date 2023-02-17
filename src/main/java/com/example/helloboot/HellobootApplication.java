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
    // 수정 목적은 기존에는 스프링 컨테이너가 올라가고 나서 작업을 진행했지만,
    // 스프링 컨테이너가 올라가는 도중에 개발자가 정의한 작업을 실행하도록 하려고 한다. (스프링 부트가 이렇게 구성되어있음)
    // 스프링 컨테이너가 올라갈 때 부가적으로 실행해야 항목이 있다면 onRefresh() 안에 정의해 놓으면 된다.
    GenericWebApplicationContext applicationContext = new GenericWebApplicationContext() {
      @Override
      protected void onRefresh() {
        super.onRefresh();

        ServletWebServerFactory tomcatServletWebServerFactory = new TomcatServletWebServerFactory();
        WebServer webServer =
                tomcatServletWebServerFactory.getWebServer(
                        servletContext -> {
                          servletContext
                                  .addServlet(
                                          "dispatcherServlet ", new DispatcherServlet(this))
                                  .addMapping("/*");
                        });
        webServer.start();
      }
    };
    applicationContext.registerBean(HelloController.class);
    applicationContext.registerBean(SimpleHelloService.class);
    // 여러개의 훅 메소드가 만들어져있다.
    applicationContext.refresh(); // 스프링 컨테이너 초기화, 템플릿 메소드로 만들어져 있음. (템플릿 메소드는 상속을 통해서 기능을 확장한 의미)


  }
}

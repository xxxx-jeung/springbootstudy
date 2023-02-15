package com.example.helloboot;


import org.apache.catalina.startup.Tomcat;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServer;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;

public class HellobootApplication {

  public static void main(String[] args) {
    /**
     * 내장형 톰캣을 가져다 사용할 것
     * 서블릿 컨테이너 만들기
     * 내장형 톰켓을 사용하기 쉽게 도와주는 클래스가 존재함
     * Factory() - 이미 복잡한 과정과 설정을 마친 다음에 톰켓을 생성할 수 있는 클래스
     * ServletWebServerFactory 해당 팩토리는 여러 스프링 부트에서 여러 종류를 받아들일 수 있게 추상화를 해놓았기 때문에 나중에 변경해서 사용할 수 있다.
     */
    ServletWebServerFactory tomcatServletWebServerFactory = new TomcatServletWebServerFactory();
    WebServer webServer = tomcatServletWebServerFactory.getWebServer();
    webServer.start();
  }
}

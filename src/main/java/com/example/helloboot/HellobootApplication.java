package com.example.helloboot;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServer;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.context.support.GenericWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;


/**
 * ComponentScan 을 사용하면 애노테이션을 설정한 클래스를 찾아 Bean 으로 등록해준다.
 */
@Configuration
@ComponentScan
public class HellobootApplication {
  /**
   * 간결하게 Bean 을 등록할 수 있는 기능이 존재
   * @Component 애노테이션을 클래스 위에 작성해놓는다.
   */
  public static void main(String[] args) {
    AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext() {
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
    applicationContext.register(HellobootApplication.class);
    applicationContext.refresh();


  }
}

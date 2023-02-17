package com.example.helloboot;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServer;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.context.support.GenericWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

/**
 * 팩토리 메소드를 만들었으면 Bean 으로 등록한다.
 * 스프링 컨테이너가 Bean 이 등록되었는지 알 수 없다.
 * 따라서 개발자가 여기에 빈이 있다는 걸 알려주기 위해
 * 클래스 위에 구성정보 애노테이션을 작성해놓으면
 * 스프링 컨테이너에게 Bean 으로 등록할 메소드가 존재한다고 알려주는 것이다.
 * 스프링 컨테이너는 그걸 알아듣고 Bean 을 등록해 놓는다.
 *
 * 여기서 중요한건 Configuration 이다
 * 스프링 컨테이너에서 첫번째로 해당 애노테이션을 읽어들이기 때문이다.
 * 스프링을 구성하는데 있어 제일 중요한 애노테이션 중 하나다.
 */
@Configuration
public class HellobootApplication {

  @Bean
  public HelloController helloController(HelloService helloService) {
    // 생성자에 주입을 해줘야 한다.
    // 이걸 파라미터로 받아서 주입해준다.
    // 자바의 팩토리 메소드
    return new HelloController(helloService);
  }

  @Bean
  public HelloService helloService() {
    return new SimpleHelloService();
  }

  public static void main(String[] args) {
    /**
     * GenericWebApplicationContext 는 자바에서 만든 애노테이션을 읽어들일 수 없다.
     * 따라서 AnnotationConfigWebApplicationContext 로 바꿔야 한다.
     */
    AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext() {
      // 스프링 컨테이너 안에 객체를 어떻게 넣을것인가, Bean 이라는 객체가 다른 객체를 의존 또는 주입을 언제 어떻게 할 것인지에 대해 알아보자
      // 팩토리 메소드, 어떤 객체를 가지고 있는 것을 말한다.
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
    // applicationContext.registerBean(HelloController.class); AnnotationConfigWebApplicationContext 에서는 지원하지 않음
    // applicationContext.registerBean(SimpleHelloService.class);
    applicationContext.register(HellobootApplication.class); // 자바 코드로 구성된 애노테이션을 직접 등록해야 한다.
    applicationContext.refresh();


  }
}

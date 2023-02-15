package com.example.helloboot;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServer;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import javax.print.attribute.standard.Media;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HellobootApplication {

  public static void main(String[] args) {
    /**
     * 01 내장형 톰캣을 가져다 사용할 것 서블릿 컨테이너 만들기 내장형 톰켓을 사용하기 쉽게 도와주는 클래스가 존재함 Factory() - 이미 복잡한 과정과 설정을 마친
     * 다음에 톰켓을 생성할 수 있는 클래스 ServletWebServerFactory 해당 팩토리는 여러 스프링 부트에서 여러 종류를 받아들일 수 있게 추상화를 해놓았기
     * 때문에 나중에 변경해서 사용할 수 있다.
     */
    ServletWebServerFactory tomcatServletWebServerFactory = new TomcatServletWebServerFactory();
    /** ServletContextInitializer 서블릿을 생성하는데 사용하는 오브젝트 */
    WebServer webServer =
        tomcatServletWebServerFactory.getWebServer(
            /** 익명 클래스 */
            servletContext -> {
              /**
               * Servlet 또한 인터페이스다. 수십가지 서블릿을 생성하기 어려우니 공통적으로 미리 만들어놓고 상속해서 사용할 수 있는 어댑터 클래스 기반인
               * HttpServlet
               */
              servletContext
                  .addServlet(
                      "hello",
                      new HttpServlet() {
                        // 원하는 메소드를 오버라이드 해서 사용하면 된다.

                        @Override
                        protected void service(HttpServletRequest req, HttpServletResponse resp)
                            throws ServletException, IOException {
                          String name = req.getParameter("name");

                          /**
                           * 서블릿을 만들었다고 해서 끝난게 아니다. 서블릿을 만들었으면 서블릿 컨테이너가 요청을 받았을 때 어떤 서블릿으로 매핑시켜줘야
                           * 하는지 또한 작업해줘야 한다. -> addMapping() 메소드 활용
                           */
                          /** Http 응답을 해주기 위해 필요한 조건들 1. Http 상태 라인 2. Header 3. Body */

                          /**
                           * 스프링에서 제공해주는 enum 을 활용하자.
                           * 오탈자가 생길 위험이 있기 때문
                           */
                          resp.setStatus(HttpStatus.OK.value());
                          resp.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_PLAIN_VALUE);
                          resp.getWriter().println("Hello " + name);
                        }
                      })
                  .addMapping("/hello");
            });
    webServer.start();

    /** 02 웹 클라이언트 <요청> 서블릿 컨테이너에서 어떤 서블릿 한테 처리를 넘길지 선택한다. 서블릿은 요청 받은 정보를 처리한 후 웹 클라이언트에게 응답해준다. */
  }
}

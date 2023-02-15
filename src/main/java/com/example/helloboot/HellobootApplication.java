package com.example.helloboot;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServer;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HellobootApplication {

  public static void main(String[] args) {

    ServletWebServerFactory tomcatServletWebServerFactory = new TomcatServletWebServerFactory();
    WebServer webServer =
        tomcatServletWebServerFactory.getWebServer(
            servletContext -> {
              HelloController helloController = new HelloController();

              servletContext
                  .addServlet(
                      "frontcontroller",
                      new HttpServlet() {
                        @Override
                        protected void service(HttpServletRequest req, HttpServletResponse resp)
                            throws ServletException, IOException {
                          // 인증, 보안, 다국어, 공통 기능
                          // 서블릿 컨테이너가 담당하던 일을 프론트 컨트롤러가 맡아서 처리해야 한다.
                          if (req.getRequestURI().equals("/hello")
                              && req.getMethod().equals(HttpMethod.GET.name())) {
                            String name = req.getParameter("name");

                            String ret = helloController.hello(name); // A->B 로 변수 값을 넘겨주는 행위를 바인딩이라고 부른다, 헬로우 컨트롤러에서 비즈니스 로직이 실행되는 것으로 구현

                            resp.setStatus(HttpStatus.OK.value());
                            resp.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_PLAIN_VALUE);
                            resp.getWriter().println(ret);
                          } else if (req.getRequestURI().equals("/user")) {
                            //
                          } else {
                            resp.setStatus(HttpStatus.NOT_FOUND.value());
                          }
                        }
                      })
                  .addMapping("/*"); // 매핑 부분을 변경, 모든 요청을 얘가 다 받아야 하므로 /* 설정해줘야 함.
            });
    webServer.start();
  }
}

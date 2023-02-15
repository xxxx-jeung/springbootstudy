package com.example.helloboot;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServer;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.support.GenericApplicationContext;
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
    // 어플리케이션 컨텍스트 = 스프링을 대표하는 객체, 리소스 접근 방법, 내부 이벤트 수행, 이게 스프링 컨테이너가 된다.
    // 스프링 컨테이너는 객체를 직접 만들어서 넣어주는것도 가능하지만 그것보다는 어떤 클래스를 이용해서 메타정보를 넣어주는게 일반적이다.
    GenericApplicationContext applicationContext = new GenericApplicationContext();
    applicationContext.registerBean(HelloController.class); // Bean 등록
    applicationContext.refresh(); // Bean 오브젝트 생성

    ServletWebServerFactory tomcatServletWebServerFactory = new TomcatServletWebServerFactory();
    WebServer webServer =
        tomcatServletWebServerFactory.getWebServer(
            servletContext -> {

              servletContext
                  .addServlet(
                      "frontcontroller",
                      new HttpServlet() {
                        @Override
                        protected void service(HttpServletRequest req, HttpServletResponse resp)
                            throws ServletException, IOException {
                          if (req.getRequestURI().equals("/hello")
                              && req.getMethod().equals(HttpMethod.GET.name())) {
                            String name = req.getParameter("name");

                            HelloController helloController = applicationContext.getBean(HelloController.class); // 등록된 Bean 을 가져와 사용할 수 있다.
                            String ret = helloController.hello(name);

                            // 200번 코드가 아닌 다른 코드를 보여주고 싶을 때 사용하지만, 굳이 200번 말고 사용할 필요 없다면 지우는게 낫다., 성공하면 서블릿 컨테이너에서 200번대 코드를 내려준다.
                            //resp.setStatus(HttpStatus.OK.value());
                            //resp.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_PLAIN_VALUE);

                            resp.setContentType(MediaType.TEXT_PLAIN_VALUE);
                            resp.getWriter().println(ret);
                          } else if (req.getRequestURI().equals("/user")) {
                            //
                          } else {
                            resp.setStatus(HttpStatus.NOT_FOUND.value());
                          }
                        }
                      })
                  .addMapping("/*");
            });
    webServer.start();
  }
}

package com.example.helloboot;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.*;

public class HelloApiTest {
  @Test
  void helloApi() {
    // http localhost:8080/hello?name=Spring
    // RestTemplate 외부의 API 를 호출해 사용할 수 있도록 스프링에서 제공
    // Test 용 RestTemplate 이 존재, TestRestTemplate 은 순수 응답, 컨텐트 타입 등 검증하고 싶을 때 쓴다.
    TestRestTemplate rest = new TestRestTemplate();
    ResponseEntity<String> res =
        rest.getForEntity("http://localhost:8080/hello?name={name}", String.class, "Spring");

    assertThat(res.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(res.getHeaders().getFirst(HttpHeaders.CONTENT_TYPE)).startsWith(MediaType.TEXT_PLAIN_VALUE);
    assertThat(res.getBody()).isEqualTo("Hello Spring");
  }
}

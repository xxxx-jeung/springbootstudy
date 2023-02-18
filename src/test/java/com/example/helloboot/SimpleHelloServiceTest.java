package com.example.helloboot;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;


class SimpleHelloServiceTest {
    @Test
    void simnpleHelloService() {
        SimpleHelloService helloService = new SimpleHelloService();
        String ret = helloService.sayHello("Test");

        Assertions.assertThat(ret).isEqualTo("Hello Test");
    }

}
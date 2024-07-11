package com.argmax.demo;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoApplication {

  @PostMapping("/pfp_recognition")
  Employee newEmployee(@RequestBody String rq) {
    return "Hello world!";
  }
}

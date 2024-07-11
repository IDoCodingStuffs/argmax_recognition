package com.argmax.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.argmax.client.StackOverflowClient;


@RestController
public class RequestServingController {

  @PostMapping("/pfp_recognition")
  String pfpRecognition() {
    StackOverflowClient client = new StackOverflowClient();
  
    return client.retrieveUserPfps();
  }
}

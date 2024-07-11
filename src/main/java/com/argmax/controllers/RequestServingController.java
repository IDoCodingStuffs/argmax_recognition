package com.argmax.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.argmax.client.ImageRetrievalClient;
import com.argmax.client.InferenceClient;
import com.argmax.client.StackOverflowClient;
import com.argmax.model.UserImageBlob;
import com.argmax.model.UserProfile;
import com.argmax.model.UserRecognizedObjects;


@RestController
public class RequestServingController {

  @PostMapping("/pfp_recognition")
  List<UserRecognizedObjects> pfpRecognition() {
    StackOverflowClient so_client = new StackOverflowClient();
    ImageRetrievalClient img_client = new ImageRetrievalClient();
    InferenceClient inf_client = new InferenceClient();

    List<UserProfile> userProfiles = so_client.retrieveUserProfiles();
    List<UserImageBlob> userImageBlobs = img_client.retrieveUserImages(userProfiles);
    List<UserRecognizedObjects> userRecognizedObjects = inf_client.retrieveInferenceResults(userImageBlobs);

    return userRecognizedObjects;
  }
}

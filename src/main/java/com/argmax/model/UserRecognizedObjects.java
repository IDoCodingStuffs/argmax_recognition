package com.argmax.model;

import java.util.List;
import lombok.*;

@Getter
@Setter
public class UserRecognizedObjects extends User{
    public List<String> recognizedObjectLabels;
}

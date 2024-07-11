package com.argmax.model;

import java.net.URL;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class UserProfile extends User {
    private URL pfpURL;
}

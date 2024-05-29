package com.panel.user.Response;

import com.panel.user.Entity.User;
import lombok.*;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationResponse {
    private  String message;
    private User data;
    private String accessToken;
}

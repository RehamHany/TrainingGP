package com.panel.user.Response;

import com.panel.user.Entity.User;
import lombok.*;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class RegisterResponse {
    private  String message;
    private User data;
}

package com.panel.user.Exception.Response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class UserErrorResponse {
    private int code;
    private String message;
    private long timestamp;
}

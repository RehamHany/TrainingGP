package com.panel.todo.Exception.Response;

import com.panel.todo.Entity.Item;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class ItemErrorResponse {

    private int code;
    private String message;
    private long timestamp;
}

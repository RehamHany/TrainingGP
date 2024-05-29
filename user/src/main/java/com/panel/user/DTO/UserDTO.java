package com.panel.user.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private int id;

    private String email;

    private String password;

    private String confirmPass;

    private boolean enabled;

    public UserDTO(String email, String password, String confirmPass, boolean enabled) {
        this.email = email;
        this.password = password;
        this.confirmPass = confirmPass;
        this.enabled = enabled;
    }

}

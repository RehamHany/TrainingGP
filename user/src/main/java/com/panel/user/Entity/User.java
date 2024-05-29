package com.panel.user.Entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;


@Entity
@Table(name = "user")
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "entity of User")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Schema(description = "Unique identifier of the user name id", example = "1")
    private int id;


    @Column(name = "email")
    @NotEmpty
    @Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")
    @Schema(description = "email of user", example = "reham@gmail.com")
    private String email;

    @Column(name = "password")
    @NotEmpty
    @Schema(description = "password of user", example = "123")
    private String password;

    @Column(name = "activate")
    @Schema(description = "to activate user", example = "true")
    private boolean activate;




    @OneToOne(mappedBy = "user" , cascade = CascadeType.ALL , fetch = FetchType.LAZY)
    private OTP otp;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public User(String email, String password, boolean activate) {
        this.email = email;
        this.password = password;
        this.activate = activate;
    }

    public User(int id, String email, String password, boolean activate) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.activate = activate;
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }
}

package com.panel.user.Service;

import com.panel.user.DTO.UserDTO;
import com.panel.user.DTO.loginDTO;
import com.panel.user.Entity.User;
import com.panel.user.Exception.UserNotFoundException;
import com.panel.user.Response.AuthenticationResponse;
import com.panel.user.Response.RegisterResponse;

import java.util.List;
import java.util.Optional;

public interface UserService {
    RegisterResponse addUser(UserDTO user) throws UserNotFoundException;

    AuthenticationResponse loginUser(loginDTO login) throws UserNotFoundException;

    void deleteUser(String token) throws UserNotFoundException;

    User findUser(String token) throws UserNotFoundException;

    List<User> findAll();

    User update(User user,String token) throws UserNotFoundException;

    User checkEmailExist(String email);

    User save(User user);


    User findByMail(String email);

    Optional<User> findById(int id);
}

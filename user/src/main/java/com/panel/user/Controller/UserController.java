package com.panel.user.Controller;

import com.panel.user.DTO.*;
import com.panel.user.Entity.Token;
import com.panel.user.Entity.User;
import com.panel.user.Exception.UserNotFoundException;
import com.panel.user.Repo.TokenRepository;
import com.panel.user.Response.AuthenticationResponse;
import com.panel.user.Response.RegisterResponse;
import com.panel.user.Service.JwtService;
import com.panel.user.Service.UserDetailsServiceImpl;
import com.panel.user.Service.UserService;
import com.panel.user.Service.emailService;
import com.panel.user.Util.OTPUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("api/v1/user")
@Tag(name = "user Requests", description = "Controller for managing user ")
public class UserController {
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;
    @Autowired
    private emailService emailservice;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private TokenRepository tokenRepository;

    @PostMapping("/register")
    @Operation(summary = "add new user", description = "add user and return message and data of user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "user added Successfully :)"),
            @ApiResponse(responseCode = "404", description = "check your request :("),
            @ApiResponse(responseCode = "500", description = "problem in server :(")
    })
    public RegisterResponse addUser(@RequestBody UserDTO userDTO) throws UserNotFoundException {
        return userService.addUser(userDTO);
    }

    @PostMapping("/login")
    @Operation(summary = "login user", description = "check your email and password and return message and data user and token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "user login Successfully :)"),
            @ApiResponse(responseCode = "404", description = "check your request :("),
            @ApiResponse(responseCode = "500", description = "problem in server :(")
    })
    public AuthenticationResponse loginUser(@RequestBody loginDTO login, @RequestParam Map<String , Object> claims) throws UserNotFoundException {
        return userService.loginUser(login);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "delete user", description = "delete user and return message")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "user deleted Successfully :)"),
            @ApiResponse(responseCode = "404", description = "check your request :("),
            @ApiResponse(responseCode = "500", description = "problem in server :(")
    })
    public String deleteUser(@Parameter(description = "Token of the user to be fetched", required = true)@RequestHeader("Authorization")String t) throws UserNotFoundException {
        userService.deleteUser(t);
        return "UserDeleted";
    }

    @GetMapping("/findUser")
    @Operation(summary = "get user by token", description = "get user and return data of user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "user found Successfully :)"),
            @ApiResponse(responseCode = "404", description = "check your request :("),
            @ApiResponse(responseCode = "500", description = "problem in server :(")
    })
    public User findByToken(@Parameter(description = "token of the user to be fetched", required = true)@RequestHeader("Authorization")String t) throws UserNotFoundException {
        return userService.findUser(t);
    }

    @GetMapping("/findAllUser")
    @Operation(summary = "get all users", description = "return list of users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "return users Successfully :)"),
            @ApiResponse(responseCode = "404", description = "check your request :("),
            @ApiResponse(responseCode = "500", description = "problem in server :(")
    })
    public List<User> findAllUser(){
        return userService.findAll();
    }

    @PutMapping("/update")
    @Operation(summary = "update user", description = "update user and return data of user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "user updated Successfully :)"),
            @ApiResponse(responseCode = "404", description = "check your request :("),
            @ApiResponse(responseCode = "500", description = "problem in server :(")
    })
    public User updateUser(@RequestBody User userDTO,@RequestHeader("Authorization") String t) throws UserNotFoundException {
        return userService.update(userDTO,t);
    }

    @PostMapping("/checkToken")
    @Operation(summary = "check Token", description = "check Token Validate")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "success :)"),
            @ApiResponse(responseCode = "404", description = "check your request :("),
            @ApiResponse(responseCode = "500", description = "problem in server :(")
    })
    public boolean checkToken(@RequestHeader("Authorization") String t) {
        Token token= tokenRepository.findByToken(t.substring(7));
        UserDetails userDetails = userDetailsService.loadUserByUsername(token.getUser().getEmail());
        return jwtService.isTokenValid(token.getToken(),userDetails);
    }

    @PostMapping("/checkEmail")
    @Operation(summary = "send code to user email", description = "check user found aby email and send a code and return message")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "send code Successfully :)"),
            @ApiResponse(responseCode = "404", description = "check your request :("),
            @ApiResponse(responseCode = "500", description = "problem in server :(")
    })
    public String resetPassword(@RequestBody ResetPassword resetPassword)throws UserNotFoundException{
        if(! (resetPassword.getEmail().matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")))
            throw new UserNotFoundException("Email not Valid :(");

        User user=userService.checkEmailExist(resetPassword.getEmail());
        if(user != null){
            int cod= OTPUtil.generateOTP();
            Mail mail=new Mail(resetPassword.getEmail(), cod);
            emailservice.setCodeByMail(mail);

            user.getOtp().setOtp(cod);
            userService.save(user);
            System.out.println(1);
        }else{
            System.out.println(0);
            throw new UserNotFoundException("user not found");
        }
        return "user found and send code successfully";
    }
    @PostMapping("/resetPassword")
    @Operation(summary = "change password", description = "check code valid and change password and return message")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "password changed Successfully :)"),
            @ApiResponse(responseCode = "404", description = "check your request :("),
            @ApiResponse(responseCode = "500", description = "problem in server :(")
    })
    public String resetPassword(@RequestBody newPassword newPass)throws UserNotFoundException{
        if(! (newPass.getEmail().matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")))
            throw new UserNotFoundException("Email not Valid :(");

        User user=userService.checkEmailExist(newPass.getEmail());
        if(user!=null){
            if(user.getOtp().getOtp()==newPass.getCode()){
                user.setPassword(passwordEncoder.encode(newPass.getPassword()));
                userService.save(user);
                System.out.println(1);
            }else{
                System.out.println(0);
                throw new UserNotFoundException("code is wrong");
            }
        }else{
            System.out.println(0);
            throw new UserNotFoundException("user not found");
        }
        return "reset password Successfully";
    }


    @PostMapping("/activate")
    @Operation(summary = "activate user", description = "activate user by otp and return message")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "activate user Successfully :)"),
            @ApiResponse(responseCode = "404", description = "check your request :("),
            @ApiResponse(responseCode = "500", description = "problem in server :(")
    })
    public String activateUser(@Parameter(description = "email of the user to be fetched", required = true)@RequestParam("email")String email,
                               @Parameter(description = "otp of the user to be fetched", required = true)@RequestHeader("OTP")int otp)throws UserNotFoundException
    {
        User user=userService.findByMail(email);
        if(user != null) {
            if (user.getOtp().getOtp() == otp) {
                user.setActivate(true);
                userService.save(user);
            }
            else{
                throw new UserNotFoundException("code not valid :(");
            }
        }else{
            throw new UserNotFoundException("user not found :(");
        }
        return "activate user successfully :)";
    }

    @PostMapping("/regenerateOTP")
    @Operation(summary = "regenerate otp to user", description = "find user and send otp by email and return message")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "regenerate otp to user Successfully :)"),
            @ApiResponse(responseCode = "404", description = "check your request :("),
            @ApiResponse(responseCode = "500", description = "problem in server :(")
    })
    public String regenerateOTP (@Parameter(description = "email of the user to be fetched", required = true)@RequestParam("email")String email)throws UserNotFoundException
    {
        User user=userService.findByMail(email);
        if(user != null) {
            int cod= OTPUtil.generateOTP();
            Mail mail=new Mail(email, cod);
            emailservice.setCodeByMail(mail);

            user.getOtp().setOtp(cod);
            userService.save(user);
        }else{
            throw new UserNotFoundException("user not found :(");
        }
        return "regenerate otp to the user successfully :)";
    }

    @GetMapping("/token")
    @Operation(summary = "find token by user id", description = "find token by user id and return token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "return token Successfully :)"),
            @ApiResponse(responseCode = "404", description = "check your request :("),
            @ApiResponse(responseCode = "500", description = "problem in server :(")
    })
    public String getToken(@Parameter(description = "token of the user to be fetched", required = true)@RequestHeader("Authorization") String t){
        Token token= tokenRepository.findByToken(t.substring(7));
        return token.getUser().getEmail();
    }


}

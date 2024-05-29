package com.panel.user.Service;

import com.panel.user.DTO.Mail;
import com.panel.user.DTO.UserDTO;
import com.panel.user.DTO.loginDTO;
import com.panel.user.Entity.OTP;
import com.panel.user.Entity.Token;
import com.panel.user.Entity.TokenType;
import com.panel.user.Entity.User;
import com.panel.user.Exception.UserNotFoundException;
import com.panel.user.Repo.TokenRepository;
import com.panel.user.Repo.UserRepo;
import com.panel.user.Response.AuthenticationResponse;
import com.panel.user.Response.RegisterResponse;
import com.panel.user.Util.OTPUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepo userRepo;


    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;
    @Autowired
    private emailService emailservice;

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private AuthenticationManager authenticationManager;


    @Override
    public RegisterResponse addUser(UserDTO userDTO) throws UserNotFoundException {

        User user =new User(
                userDTO.getEmail(),
                passwordEncoder.encode(userDTO.getPassword()),
                false
        );


        OTP otp=new OTP(OTPUtil.generateOTP());
        user.setOtp(otp);
        otp.setUser(user);


        if(! (userDTO.getPassword().equals(userDTO.getConfirmPass())))
            throw new UserNotFoundException("Password and Confirm Password must be Same :(");
        if(! (userDTO.getEmail().matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")))
            throw new UserNotFoundException("Email not Valid :(");
        if(! (userRepo.findByEmail(userDTO.getEmail())==null))
            throw new UserNotFoundException("Email Existed , please Sign in :)");

        Mail mail=new Mail(userDTO.getEmail(), otp.getOtp());
        emailservice.setCodeByMail(mail);


        User savedUser=userRepo.save(user);


        return new RegisterResponse("Registered Successfully :)", savedUser);

    }

    private void saveUserToken(User user, String jwtToken) {
        Token token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    @Override
    public AuthenticationResponse loginUser(loginDTO login) throws UserNotFoundException{

        if(! (login.getEmail().matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")))
            throw new UserNotFoundException("Email not Valid :(");



        User user = userRepo.findByEmail(login.getEmail());
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login.getEmail(),login.getPassword()));

        Map<String , Object> extraClaims = new HashMap<>();
        String jwtToken = jwtService.createToken(user , extraClaims);
        revokeAllTokenByUser(user);
        saveUserToken(user, jwtToken);


        return new AuthenticationResponse("Login Success", user, jwtToken);
    }

    private void revokeAllTokenByUser(User user) {
        List<Token> validTokens = tokenRepository.findAllTokensByUser(user.getId());
        if(validTokens.isEmpty()) {
            return ;
        }

        validTokens.forEach(t-> {
            t.setExpired(true);
            t.setRevoked(true);
            tokenRepository.deleteById(t.getId());
        });

        tokenRepository.saveAll(validTokens);
    }

    @Override
    public void deleteUser(String token) throws UserNotFoundException{
        Token t= tokenRepository.findByToken(token.substring(7));
        User user=userRepo.findByEmail(t.getUser().getEmail());
        System.out.println(user.getId());
        int id=user.getId();
        if(user == null){
            throw new UserNotFoundException("user not Found :(");
        }
        userRepo.deleteById(id);

    }

    @Override
    public User findUser(String token) throws UserNotFoundException{
        Token t= tokenRepository.findByToken(token.substring(7));
        User user=userRepo.findByEmail(t.getUser().getEmail());
        System.out.println(user.getId());
        int id= user.getId();
            if(user == null){
                throw new UserNotFoundException("user not Found :(");
            }else{
                return userRepo.findById(id).get();
            }

    }


    @Override
    public List<User> findAll() {
        return userRepo.findAll();
    }

    @Override
    public User update(User userNew,String token) throws UserNotFoundException{

        if(! (userNew.getEmail().matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")))
            throw new UserNotFoundException("Email not Valid :(");

        Token t= tokenRepository.findByToken(token.substring(7));
        User user=userRepo.findByEmail(t.getUser().getEmail());
        System.out.println(user.getId());
        int id=user.getId();


        if (user!=null) {
            // Modify the fields of the entity object
            user.setEmail(userNew.getEmail());
            user.setPassword(passwordEncoder.encode(userNew.getPassword()));

            // Save the entity
            userRepo.save(user);
            return  userRepo.save(user);
        }else {
            throw new UserNotFoundException("user not found :(");
        }

    }

    @Override
    public User checkEmailExist(String email) {
        return userRepo.findByEmail(email);
    }

    @Override
    public User save(User user) {
         return userRepo.save(user);
    }

    @Override
    public User findByMail(String email) {
        return userRepo.findByEmail(email);
    }
    @Override
    public Optional<User> findById(int id) {
        return userRepo.findById(id);
    }

}

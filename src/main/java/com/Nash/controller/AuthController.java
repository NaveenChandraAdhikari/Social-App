package com.Nash.controller;


import com.Nash.config.JwtProvider;
import com.Nash.models.User;
import com.Nash.repository.UserRepository;
import com.Nash.request.LoginRequest;
import com.Nash.response.AuthResponse;
import com.Nash.service.CustomUserDetailsService;
import com.Nash.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth") // this means iss main class ke sare enpoints starts with /auth endpoint
public class AuthController
{

@Autowired
 private    UserService userService;

@Autowired
private UserRepository userRepository;

@Autowired
private PasswordEncoder passwordEncoder;

@Autowired
private CustomUserDetailsService customUserDetails;
// /auth/signup
    @PostMapping("/signup")
    public AuthResponse createUser(@RequestBody User user) throws Exception {
//        User savedUser =userService.registerUser(user);
//        return savedUser;

        User isExist=userRepository.findByEmail(user.getEmail());

        if(isExist!=null){
            throw new Exception("email already used with another account");
        }

        //otherwise creat the new user

        User newUser=new User();
        newUser.setEmail(user.getEmail());
        newUser.setFirstName(user.getFirstName());
        newUser.setLastName(user.getLastName());
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));


//        to add usr in database we use save method ...this is must ste
        User savedUser =userRepository.save(newUser);


        //now we need to generate the token
        Authentication authentication =new UsernamePasswordAuthenticationToken(savedUser,savedUser.getPassword());

        String token =JwtProvider.generateToken(authentication);


        AuthResponse res=new AuthResponse(token,"Register Success");
        //        now we need to return token instead of user

//        return savedUser;
        return res;









    }


    // auth/signin
@PostMapping("/signin")
    public AuthResponse signin(@RequestBody LoginRequest loginRequest){

//        to genreate token we do authentication as we know that
        Authentication authentication =authenticate(loginRequest.getEmail(),loginRequest.getPassword());


        // same copy paste like login generate token and generate response:::))
        String token =JwtProvider.generateToken(authentication);


        AuthResponse res=new AuthResponse(token,"Login Success");
        //        now we need to return token instead of user

//        return savedUser;
        return res;
    }

    private Authentication authenticate(String email, String password) {
        // check password in the database

        UserDetails userDetails=customUserDetails.loadUserByUsername(email);

        if(userDetails==null){
            //user load nahi hua
            throw new BadCredentialsException("Invlaid username");

        }

        if(!passwordEncoder.matches(password,userDetails.getPassword())){
            throw new BadCredentialsException(" WRONG PASSWORD ,NOT MATCHED");
        }
        return new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());

    }

}

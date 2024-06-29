package com.Nash.service;

import com.Nash.models.User;
import com.Nash.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

//this will basically load the user by email now so bydefault password of spring securtoy will not come

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

//    now automatic generated password nahi ayega we have give our own username and paswword
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user=userRepository.findByEmail(username);
        if(user==null){
            throw new  UsernameNotFoundException("User not found with emaill+"+username);

        }

//        isme roles as such kuch nahi hai jese admin super admin etc etc
        List<GrantedAuthority> authorities =new ArrayList<>();

//this user is not what we made ,,it is of the spring security wala user
        return new org.springframework.security.core.userdetails.User(user.getEmail(),user.getPassword(),authorities);

    }
}

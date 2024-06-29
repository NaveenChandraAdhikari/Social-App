package com.Nash.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.Nash.config.JwtProvider;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.Nash.config.JwtConstant.JWT_HEADER;

public class jwtValidator extends OncePerRequestFilter {


//    public static  String JWT_HEADER="Authorization";

//    when we access secure endpoint we access token in header
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {


        String jwt =request.getHeader(JWT_HEADER);

if(jwt!=null){

    try{
        String email =JwtProvider.getEmailFromJwtToken(jwt);

        List<GrantedAuthority> authorities =new ArrayList<>();

        Authentication authentication=new UsernamePasswordAuthenticationToken(email,null,authorities);


        SecurityContextHolder.getContext().setAuthentication(authentication);

    }
    catch (Exception e){
        throw new BadCredentialsException("INVALID TOKEEEN..");


    }


}
//else{
//    throw new BadCredentialsException("please provide a valid token");
//}
filterChain.doFilter(request,response);
    }
}

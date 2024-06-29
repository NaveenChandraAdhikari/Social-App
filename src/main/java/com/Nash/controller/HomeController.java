package com.Nash.controller;


import org.springframework.web.bind.annotation.*;

@RestController
public class HomeController {


    @GetMapping
    public String homeControllerHandler(){
        return "this is home controlldeeer";
    }

//@PutMapping
//@PostMapping
//@DeleteMapping

}

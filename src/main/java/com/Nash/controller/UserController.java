package com.Nash.controller;


import com.Nash.models.User;
import com.Nash.repository.UserRepository;
import com.Nash.service.UserService;
import org.apache.catalina.realm.UserDatabaseRealm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class UserController
{

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

//
//    @PostMapping("/users")
//    public User createUser(@RequestBody User user){
//// id ont need that as ihavealready define it in userservice business logic
////        User newUser=new User();
////        newUser.setEmail(user.getEmail());
////        newUser.setFirstName(user.getFirstName());
////        newUser.setLastName(user.getLastName());
////        newUser.setPassword(user.getPassword());
////        newUser.setId(user.getId());
//
//////        to add usr in database we use save method
////        User savedUser =userRepository.save(newUser);
////
////
////
//////        return newUser;
////        return savedUser;
//
//
//        User savedUser =userService.registerUser(user);
//        return savedUser;
//
//    }

//    api url cuz of srping security
    @GetMapping("/api/users")

    public List<User> getUsers(){
//
//            List<User> users =new ArrayList<>();
//
//
//            User user1 =new User("cpde","mas","codes","1234");
//            User user2 =new User(3,"cpde","mas","codes","1234");
//
//
//            users.add(user1);
//            users.add(user2);
//
//            return users;

        //to retrieve the data from database
        List<User> users=userRepository.findAll();

        return users;


        }


        @GetMapping("/api/users/{userId}")

    public User getUsersById(@PathVariable("userId") Integer Id) throws Exception {

////            List<User> users =new ArrayList<>();
//
//
//            User user1 =new User("cpde","mas","codes","1234");
////            User user2 =new User(3,"cpde","mas","codes","1234");
//
//            user1.setId(Id);
//
////            users.add(user2);
//
//            return user1;

//            OPtional means yaa to user hoga ya to nahi hoga
//          User user =userRepository.findById(Id).orElse(null);
            //////I DONT NEED AS I HAVE ALREADY DEFINED IT IN SERVICE CLASS
//          Optional<User> user =userRepository.findById(Id);
//
//            if (user.isPresent()){
//                return user.get();
//            }
//
//            throw new Exception("user not exist with userid "+Id);

            User user =userService.findUserById(Id);
            return user;



        }




//        jwt se userid nikalunga apart from giving direct{userid} and using @pathvariable which i gave earleier}
        @PutMapping("/api/users")
        public User updateUser(@RequestBody User user,@RequestHeader("Authorization") String jwt) throws Exception {

//
//        // check if user exist or not
//        Optional<User> user1  =userRepository.findById(userId);
//
//        if(user1.isEmpty()){
//            throw new Exception("user not exist with user id"+userId);
//
//        }
//
////        if it exists
//    User oldUser=user1.get();
//
//        if(user.getFirstName()!=null){
//            oldUser.setFirstName((user.getFirstName()));
//        }
//
//        if(user.getLastName()!=null){
//            oldUser.setLastName(user.getLastName());
//        }
//        if (user.getEmail()!=null){
//            oldUser.setEmail(user.getEmail());
//        }
//
////        save is used to update the user
//        User updatedUser=userRepository.save(oldUser);
//   return updatedUser;

//
//            User user1 =new User("cpde","mas","codes","1234");
//
//            if(user.getFirstName()!=null){
//                user1.setFirstName((user.getFirstName()));
//            }
//
//            if(user.getLastName()!=null){
//                user1.setLastName(user.getLastName());
//            }
//
//            if(user.getEmail()!=null){
//                user1.setEmail(user.getEmail());
//            }



//            return user1;


//            reguser is lggedin user which created jwt i have to uupdate that user only koi or user update naa ho
    User regUser    =userService.findUserByJwt(jwt);

            User updatedUser=userService.updateUser(user,regUser.getId());

            return updatedUser;
        }



/*
        @DeleteMapping("users/{userId}")
        public String deleteUser(@PathVariable Integer userId) throws Exception {

            // check if user exist or not with optional
            Optional<User> user  =userRepository.findById(userId);

            if(user.isEmpty()){
                throw new Exception("user not exist with user id"+userId);

            }
        userRepository.delete(user.get());
            return "user dleeted suceesfully"+userId;
        }


 */

//    1 wala userid wants to follow userdid2
    @PutMapping("/api/users/follow/{userId1}/{userId2}")
    public User followUserHandler(@PathVariable Integer userId1,@PathVariable Integer userId2) throws Exception {

        User user =userService.followUser(userId1,userId2);
        return user;
    }



    @GetMapping("/api/users/search")
    public List<User> searchUser(@RequestParam("query") String query){

        List<User> users =userService.searchUser(query);
        return users;
    }


    @GetMapping("/api/users/profile")
public User getUserFromToken(@RequestHeader("Authorization") String jwt){
//String email
//    System.out.println("jwt -----------"+jwt);

    User user =userService.findUserByJwt(jwt);

//        if (user == null) {
//            throw new RuntimeException("User not found");
//        }


//    i dont wawnt to give password to the frontend
    user.setPassword(null);

    return user;
}
}



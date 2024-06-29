package com.Nash.service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.Nash.config.JwtProvider;
import com.Nash.models.User;
import com.Nash.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


//this all is Business logic ::: so we use Service annotation

@Service
public class UserServiceImplementation implements UserService {


    @Autowired
    UserRepository userRepository;

    @Override
    public User registerUser(User user) {
        User newUser = new User();
        newUser.setEmail(user.getEmail());
        newUser.setFirstName(user.getFirstName());
        newUser.setLastName(user.getLastName());
        newUser.setPassword(user.getPassword());
        newUser.setId(user.getId());

//        to add usr in database we use save method ...this is must ste
        User savedUser = userRepository.save(newUser);


        return savedUser;
    }

    @Override
    public User findUserById(Integer userId) throws Exception {
        Optional<User> user = userRepository.findById(userId);

        if (user.isPresent()) {
            return user.get();
        }

        throw new Exception("user not exist with userid " + userId);

    }

    @Override
    public User findUserByEmail(String email) {

        User user = userRepository.findByEmail(email);
        return user;

    }

    @Override
    public User followUser(Integer userId1, Integer userId2) throws Exception {
        User user1 = findUserById(userId1);
        User user2 = findUserById(userId2);


        user2.getFollowers().add(user1.getId());
        user1.getFollowings().add(user2.getId());

        userRepository.save(user1);
        userRepository.save(user2);

//as user1 follow karna chahtha tha to return user1
        return user1;
    }

    @Override
    public User updateUser(User user, Integer userId) throws Exception {

        // check if user exist or not
        Optional<User> user1 = userRepository.findById(userId);

        if (user1.isEmpty()) {
            throw new Exception("user not exist with user id" + userId);

        }

//        if it exists
        User oldUser = user1.get();

        if (user.getFirstName() != null) {
            oldUser.setFirstName((user.getFirstName()));
        }

        if (user.getLastName() != null) {
            oldUser.setLastName(user.getLastName());
        }
        if (user.getEmail() != null) {
            oldUser.setEmail(user.getEmail());
        }

//        save is used to update the user
        User updatedUser = userRepository.save(oldUser);
        return updatedUser;

    }

    @Override
    public List<User> searchUser(String query) {


        return userRepository.searchUser(query);


    }

    @Override
    public User findUserByJwt(String jwt) {



//        token se email nikalna
        String email= JwtProvider.getEmailFromJwtToken(jwt);

        User user=userRepository.findByEmail(email);




        return user;
    }
}

//    @Override
//    public User findUserByJwt(String jwt) {
//        System.out.println("Processing JWT in findUserByJwt: " + jwt);
//
//        try {
//            String email = JwtProvider.getEmailFromJwtToken(jwt);
//            System.out.println("Extracted email from JWT: " + email);
//
//            User user = userRepository.findByEmail(email);
//            if (user == null) {
//                System.out.println("No user found for email: " + email);
//            } else {
//                System.out.println("User found: ID = " + user.getId() + ", Email = " + user.getEmail());
//            }
//            return user;
//        } catch (Exception e) {
//            System.out.println("Error in findUserByJwt: " + e.getMessage());
//            e.printStackTrace();
//            return null;
//        }
//    }


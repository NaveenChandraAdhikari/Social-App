package com.Nash.repository;


import com.Nash.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

//in jparepository <User,uniqueidetifier>
//kis class se repo lee hiai and and unique indentifier
public interface UserRepository extends JpaRepository<User,Integer> {

    // CREATED FINDBYEMAIL
    public User findByEmail(String email);


    @Query("select u from User u where u.firstName LIKE %:query%  OR u.lastName LIKE %:query% OR u.email LIKE %:query%")

    public List<User> searchUser(@Param("query") String query);




}

package com.Nash.repository;

import com.Nash.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

//this repository make crud operation in database
public interface PostRepository extends JpaRepository<Post,Integer > {


//    method to find postbyuserID


    @Query("select p from Post p where p.user.id=:userID")
    List<Post> findPostByUserId(Integer userID);
}

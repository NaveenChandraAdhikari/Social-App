package com.Nash.models;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDateTime;


// entity map database table to this table ...agr iss model ka tabel nahi hai to create thata table and put the columns ,,if already created than map it

@Entity

public class Post {


    @Id
//    this means it generate automatic id when we create post
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String caption;
    private String image;

    private String video;

//    baar baar user defined karne ki jarurat nahi hai just pass the userid and apply relation here of post to user
//1 post ka ek hi 1 user create kar sakta hai koi dussra user nahi ayega

    // one user has multiple post ..post ke saath many to one relation


    //    post>user=>savedPost>user=>savedPost=>user=>SavedPost there  is a recursive problem
@ManyToOne
    private User user;



    // jo user like karega isme store hoga
    // 1 user do only 1 and multiple user like the post or bhaut sare user can like only 1 post
    @OneToMany
    private List<User> liked=new ArrayList<>();
    private LocalDateTime createdAt;

    public Post(){

    }

    public Post(Integer id, String caption, String image, String video, User user, List<User> liked, LocalDateTime createdAt) {
        this.id = id;
        this.caption = caption;
        this.image = image;
        this.video = video;
        this.user = user;
        this.liked = liked;
        this.createdAt = createdAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public List<User> getLiked() {
        return liked;
    }

    public void setLiked(List<User> liked) {
        this.liked = liked;
    }
}

package com.example.instagramTest.controller;

import com.example.instagramTest.model.Post;
import com.example.instagramTest.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/post")
public class PostController {
    @Autowired
    private PostService postService;

    @GetMapping
    public List<Post> getAllPost(){
        return postService.getAllPost();
    }
    @PostMapping
    public Post savePost(@RequestBody Post post){
        return postService.savePost(post);
    }
}

package com.codingshuttle.MyJwtDemo.controllers;


import com.codingshuttle.MyJwtDemo.dto.PostDTO;
import com.codingshuttle.MyJwtDemo.services.PostServiceImpl;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/posts")
@RequiredArgsConstructor
public class PostController {

    final PostServiceImpl postServiceImpl;

    @PostMapping
    public ResponseEntity<PostDTO> createPost(@RequestBody PostDTO postDTO){
        System.out.println("CONTROLLER HIT");

        return ResponseEntity.ok(postServiceImpl.createPost(postDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDTO> getPostById(@PathVariable Long id){
        return ResponseEntity.ok(postServiceImpl.getPostById(id));
    }
}

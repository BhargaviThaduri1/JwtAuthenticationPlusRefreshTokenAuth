package com.codingshuttle.MyJwtDemo.controllers;


import com.codingshuttle.MyJwtDemo.dto.PostDTO;
import com.codingshuttle.MyJwtDemo.services.PostServiceImpl;
import com.codingshuttle.MyJwtDemo.utils.PostSecurity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/posts")
@RequiredArgsConstructor
public class PostController {

    final PostServiceImpl postServiceImpl;
    final PostSecurity postSecurity;

    @PostMapping
    @PreAuthorize("hasAnyRole('CREATOR','ADMIN') OR hasAuthority('POST_CREATE')")
    public ResponseEntity<PostDTO> createPost(@RequestBody PostDTO postDTO){
        System.out.println("CONTROLLER HIT");

        return ResponseEntity.ok(postServiceImpl.createPost(postDTO));
    }

    @GetMapping("/{id}")
    @PreAuthorize("@postSecurity.isOwner(#id)")
    public ResponseEntity<PostDTO> getPostById(@PathVariable Long id){
        return ResponseEntity.ok(postServiceImpl.getPostById(id));
    }
}

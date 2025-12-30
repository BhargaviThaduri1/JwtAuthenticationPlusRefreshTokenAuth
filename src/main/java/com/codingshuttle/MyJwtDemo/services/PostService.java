package com.codingshuttle.MyJwtDemo.services;


import com.codingshuttle.MyJwtDemo.dto.PostDTO;

public interface PostService {

    PostDTO createPost(PostDTO postDTO);

    PostDTO getPostById(Long id);
}

package com.codingshuttle.MyJwtDemo.utils;

import com.codingshuttle.MyJwtDemo.dto.PostDTO;
import com.codingshuttle.MyJwtDemo.entities.UserEntity;
import com.codingshuttle.MyJwtDemo.entities.enums.Role;
import com.codingshuttle.MyJwtDemo.services.PostService;
import com.codingshuttle.MyJwtDemo.services.UserServiceImpl;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PostSecurity {

     private final PostService postService;

    public boolean isOwner(Long Id){
        UserEntity user = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        PostDTO post = postService.getPostById(Id);
        return post.getAuthor().getId().equals(user.getId());
    }
}

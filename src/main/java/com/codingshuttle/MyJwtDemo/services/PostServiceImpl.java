package com.codingshuttle.MyJwtDemo.services;


import com.codingshuttle.MyJwtDemo.dto.PostDTO;
import com.codingshuttle.MyJwtDemo.entities.Post;
import com.codingshuttle.MyJwtDemo.entities.UserEntity;
import com.codingshuttle.MyJwtDemo.repository.PostRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true,level = AccessLevel.PRIVATE)
public class PostServiceImpl implements PostService {

    PostRepository postRepository;
    ModelMapper modelMapper;

    public PostDTO createPost(PostDTO inputPost) {
        UserEntity user = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Post post = modelMapper.map(inputPost,Post.class);
        post.setAuthor(user);
        return modelMapper.map(postRepository.save(post),PostDTO.class);
    }

    @Override
    public PostDTO getPostById(Long id) {
        return modelMapper.map(postRepository.findById(id),PostDTO.class);
    }


}

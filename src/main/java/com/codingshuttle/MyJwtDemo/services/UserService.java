package com.codingshuttle.MyJwtDemo.services;

import com.codingshuttle.MyJwtDemo.dto.SignUpRequest;
import com.codingshuttle.MyJwtDemo.dto.UserDTO;
import com.codingshuttle.MyJwtDemo.dto.loginRequest;
import com.codingshuttle.MyJwtDemo.entities.UserEntity;

public interface UserService {

    UserDTO signUp(SignUpRequest signUpRequest);
    UserEntity getUserById(Long userId);

}

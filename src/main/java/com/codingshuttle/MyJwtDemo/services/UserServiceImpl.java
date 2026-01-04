package com.codingshuttle.MyJwtDemo.services;

import com.codingshuttle.MyJwtDemo.dto.SignUpRequest;
import com.codingshuttle.MyJwtDemo.dto.UserDTO;
import com.codingshuttle.MyJwtDemo.dto.loginRequest;
import com.codingshuttle.MyJwtDemo.entities.UserEntity;
import com.codingshuttle.MyJwtDemo.repository.UserRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.jspecify.annotations.Nullable;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@FieldDefaults(makeFinal = true,level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {

    UserRepository userRepository;
    ModelMapper modelMapper;
    PasswordEncoder passwordEncoder;

    public UserEntity getUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User with id "+ userId +
                " not found"));
    }

    public UserEntity getUserByEmail(String email){
        return userRepository.findByEmail(email).orElse(null);
    }

    @Override
    public UserEntity save(UserEntity user) {
        return userRepository.save(user);
    }

    public UserDTO signUp(SignUpRequest signUpRequest) {

        Optional<UserEntity> userEntity = userRepository.findByEmail(signUpRequest.getEmail());
        if(userEntity.isPresent()){
            throw new RuntimeException("User with Email "+ signUpRequest.getEmail() + " already exists");
        }

        UserEntity user = modelMapper.map(signUpRequest,UserEntity.class);
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        return modelMapper.map(userRepository.save(user),UserDTO.class);
    }



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return modelMapper.map(userRepository.findByEmail(username).orElseThrow(()-> new BadCredentialsException("User with email not found"+username)),UserDetails.class);
    }
}

package com.codingshuttle.MyJwtDemo.dto;

import com.codingshuttle.MyJwtDemo.entities.enums.Permission;
import com.codingshuttle.MyJwtDemo.entities.enums.Role;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
public class SignUpRequest {

    String username;

    String email;

    String password;

    Set<Role> roles;

    Set<Permission> permissions;

}

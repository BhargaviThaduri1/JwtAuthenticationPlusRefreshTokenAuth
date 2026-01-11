package com.codingshuttle.MyJwtDemo.entities;


import com.codingshuttle.MyJwtDemo.entities.enums.Permission;
import com.codingshuttle.MyJwtDemo.entities.enums.Role;
import com.codingshuttle.MyJwtDemo.utils.PermissionMapping;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
public class UserEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String username;

    String email;

    String password;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(value = EnumType.STRING)
    Set<Role> roles;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
      Set<SimpleGrantedAuthority> authorities = new HashSet<>();
      roles.forEach(role -> {
                  Set<SimpleGrantedAuthority> permissions = PermissionMapping.getAuthorities(role);
                  authorities.addAll(permissions);
                  authorities.add(new SimpleGrantedAuthority("ROLE_"+role.name()));
      });
      return  authorities;
    }

    @Override
    public @Nullable String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }
}

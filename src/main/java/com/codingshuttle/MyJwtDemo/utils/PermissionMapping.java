package com.codingshuttle.MyJwtDemo.utils;

import com.codingshuttle.MyJwtDemo.entities.enums.Permission;
import com.codingshuttle.MyJwtDemo.entities.enums.Role;
import com.codingshuttle.MyJwtDemo.entities.enums.Role.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.codingshuttle.MyJwtDemo.entities.enums.Permission.USER_VIEW;

public class PermissionMapping {

    private static final Map<Role, Set<Permission>> map = Map.of(
            Role.USER, Set.of(USER_VIEW,Permission.POST_VIEW),
            Role.CREATOR,Set.of(USER_VIEW,Permission.POST_VIEW,Permission.POST_CREATE,Permission.POST_UPDATE),
            Role.ADMIN,Set.of(USER_VIEW,Permission.POST_VIEW,Permission.USER_DELETE,Permission.POST_DELETE,Permission.USER_CREATE,Permission.POST_CREATE,Permission.POST_UPDATE,Permission.USER_UPDATE)
    );

    public static Set<SimpleGrantedAuthority> getAuthorities(Role role){
        return map.get(role).stream().map(permission -> new SimpleGrantedAuthority(permission.name())).collect(Collectors.toSet());

    }
}

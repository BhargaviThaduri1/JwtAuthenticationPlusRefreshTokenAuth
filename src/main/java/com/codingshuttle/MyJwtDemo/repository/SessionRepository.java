package com.codingshuttle.MyJwtDemo.repository;

import com.codingshuttle.MyJwtDemo.entities.Session;
import com.codingshuttle.MyJwtDemo.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SessionRepository extends JpaRepository<Session,Long> {
    Optional<Session> findByRefreshToken(String refreshToken);

    List<Session> findByUser(UserEntity user);
}

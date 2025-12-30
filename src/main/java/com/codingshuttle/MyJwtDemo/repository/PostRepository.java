package com.codingshuttle.MyJwtDemo.repository;


import com.codingshuttle.MyJwtDemo.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post,Long> {
}

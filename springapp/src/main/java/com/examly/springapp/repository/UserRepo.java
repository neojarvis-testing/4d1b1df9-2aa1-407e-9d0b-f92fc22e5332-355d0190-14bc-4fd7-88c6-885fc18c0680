package com.examly.springapp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.examly.springapp.model.User;

@Repository
public interface UserRepo extends JpaRepository<User, Long>{

    @Query("select user from User user where user.email=?1")
    User findByEmail(String email);

    @Query("select user from User user where user.username=?1")
    User findByUsername(String email);

    @Query("select user from User user where user.email=?1 or user.username=?2")
    Optional<User> findByEmailOrUser(String email,String username);
}

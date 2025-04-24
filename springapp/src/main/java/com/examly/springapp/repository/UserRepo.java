package com.examly.springapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.examly.springapp.dto.UserDTO;
import com.examly.springapp.model.User;

@Repository
public interface UserRepo extends JpaRepository<User, Long>{

    @Query("select user from User where user.email=?1")
    UserDTO findByEmail(String email);    
}

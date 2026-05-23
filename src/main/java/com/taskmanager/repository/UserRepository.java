package com.taskmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.taskmanager.entity.User;
import java.util.*;

public interface UserRepository extends JpaRepository<User,Long>{
    Optional<User> findByEmail(String email);
}

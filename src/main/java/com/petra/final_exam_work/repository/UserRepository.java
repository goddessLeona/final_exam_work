package com.petra.final_exam_work.repository;

import com.petra.final_exam_work.Entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    //find user through email
    Optional<User> findByUsername(String email);
}

package com.petra.final_exam_work.repository;

import com.petra.final_exam_work.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    //find user through email
    Optional<User> findByUsername(String email);

    Optional<User> findByPublicUuid(UUID publicUuid);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    Optional<User> findByUserId(Long userId);

}

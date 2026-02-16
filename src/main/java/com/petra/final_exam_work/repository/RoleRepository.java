package com.petra.final_exam_work.repository;

import com.petra.final_exam_work.entity.user.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(String roleContributor);

}

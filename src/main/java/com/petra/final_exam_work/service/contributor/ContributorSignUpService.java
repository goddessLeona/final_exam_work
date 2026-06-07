package com.petra.final_exam_work.service.contributor;

import com.petra.final_exam_work.dto.mapperDto.contributor.ContributorSignUpMapper;
import com.petra.final_exam_work.dto.requestDto.contributor.ContributorSignUpRequest;
import com.petra.final_exam_work.dto.responseDto.contributor.ContributorSignUpResponse;
import com.petra.final_exam_work.entity.user.Role;
import com.petra.final_exam_work.entity.user.User;
import com.petra.final_exam_work.exception.ApiException;
import com.petra.final_exam_work.repository.RoleRepository;
import com.petra.final_exam_work.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Set;

@Service
public class ContributorSignUpService {

    private final UserRepository userRepository;
    private final ContributorSignUpMapper contributorSignUpMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public ContributorSignUpService(UserRepository userRepository, ContributorSignUpMapper contributorSignUpMapper, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.contributorSignUpMapper = contributorSignUpMapper;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    // ######### POST Contributor sign up #################
    @Transactional
    public ContributorSignUpResponse signUpContributor (ContributorSignUpRequest request) {

        //check if username already exist
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new ApiException(
                    "Validation failed",
                    Map.of("username", "Username already exist"),
                    HttpStatus.BAD_REQUEST
            );
        }

        //check if email already exist
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ApiException(
                    "Validation failed",
                    Map.of("email", "Email already exist"),
                    HttpStatus.BAD_REQUEST
            );
        }

        // Map DTO to Entity
        User user = contributorSignUpMapper.toUser((request));

        //Encode password
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        //Get role Contributor
        Role contributorRole = roleRepository.findByRole("CONTRIBUTOR")
                .orElseThrow(() -> new ApiException("Role not found", HttpStatus.NOT_FOUND));

        user.setRoles(Set.of(contributorRole));
        System.out.println(user);

        //Save
        userRepository.save(user);

        return new ContributorSignUpResponse(
                user.getUsername()
        );

    }

}


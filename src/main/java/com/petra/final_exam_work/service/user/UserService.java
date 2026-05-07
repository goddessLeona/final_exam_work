package com.petra.final_exam_work.service.user;

import com.petra.final_exam_work.dto.mapperDto.ContributorSignUpMapper;
import com.petra.final_exam_work.dto.mapperDto.MeMapper;
import com.petra.final_exam_work.dto.requestDto.ContributorSignUpRequest;
import com.petra.final_exam_work.dto.responseDto.ContributorSignUpResponse;
import com.petra.final_exam_work.dto.responseDto.MeResponse;
import com.petra.final_exam_work.entity.enums.ContributorStatus;
import com.petra.final_exam_work.entity.user.Role;
import com.petra.final_exam_work.entity.user.User;
import com.petra.final_exam_work.exception.ApiException;
import com.petra.final_exam_work.repository.RoleRepository;
import com.petra.final_exam_work.repository.UserRepository;
import com.petra.final_exam_work.security.SecurityUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Period;
import java.time.Year;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final MeMapper meMapper;
    private final ContributorSignUpMapper contributorSignUpMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public UserService(UserRepository userRepository, MeMapper meMapper, ContributorSignUpMapper contributorSignUpMapper,
                       PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.meMapper = meMapper;
        this.contributorSignUpMapper = contributorSignUpMapper;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

//#################################### GET USERNAME ############################

    public MeResponse getUsername() {

        UUID publicUuid = SecurityUtils.getCurrentUserPublicUuid();
        User user = userRepository.findByPublicUuid(publicUuid)
                .orElseThrow(() -> new ApiException("User was not found", HttpStatus.NOT_FOUND));

        return meMapper.toDto(user);
    }

// ################################# POST Contributor sign up #########################
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

        //simple check if user is over 18 years old
        LocalDate today = LocalDate.now();
        LocalDate birthDay  = LocalDate.of(
                request.getBirthYear(),
                request.getBirthMonth(),
                request.getBirthDay()
        );

        int age = Period.between(birthDay, today).getYears();

        if (age < 18) {
            throw new ApiException("You must be at least 18 years old to upload content", HttpStatus.BAD_REQUEST);
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

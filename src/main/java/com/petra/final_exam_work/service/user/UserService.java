package com.petra.final_exam_work.service.user;

import com.petra.final_exam_work.dto.mapperDto.contributor.ContributorSignUpMapper;
import com.petra.final_exam_work.dto.mapperDto.MeMapper;
import com.petra.final_exam_work.dto.requestDto.contributor.ContributorSignUpRequest;
import com.petra.final_exam_work.dto.responseDto.ContributorSignUpResponse;
import com.petra.final_exam_work.dto.responseDto.MeResponse;
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
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final MeMapper meMapper;

    public UserService(UserRepository userRepository, MeMapper meMapper) {
        this.userRepository = userRepository;
        this.meMapper = meMapper;
    }

    //###########GET USERNAME ############
    public MeResponse getUsername() {

        UUID publicUuid = SecurityUtils.getCurrentUserPublicUuid();
        User user = userRepository.findByPublicUuid(publicUuid)
                .orElseThrow(() -> new ApiException("User was not found", HttpStatus.NOT_FOUND));

        return meMapper.toDto(user);
    }

}

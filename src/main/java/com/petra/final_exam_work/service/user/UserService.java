package com.petra.final_exam_work.service.user;

import com.petra.final_exam_work.dto.mapperDto.MeMapper;
import com.petra.final_exam_work.dto.responseDto.UserNameResponse;
import com.petra.final_exam_work.entity.user.User;
import com.petra.final_exam_work.exception.ApiException;
import com.petra.final_exam_work.repository.UserRepository;
import com.petra.final_exam_work.security.CustomUserDetails;
import com.petra.final_exam_work.security.SecurityUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

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
    public UserNameResponse getUsername(
            CustomUserDetails userDetails
    ) {

        UUID publicUuid = SecurityUtils.getCurrentUserPublicUuid();

        User user = userRepository.findByPublicUuid(publicUuid)
                .orElseThrow(() -> new ApiException("User was not found", HttpStatus.NOT_FOUND));

        return meMapper.toDto(user);
    }

}

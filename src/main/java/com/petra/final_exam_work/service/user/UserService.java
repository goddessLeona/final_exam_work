package com.petra.final_exam_work.service.user;

import com.petra.final_exam_work.dto.mapperDto.MeMapper;
import com.petra.final_exam_work.dto.responseDto.UserNameResponse;
import com.petra.final_exam_work.entity.user.User;
import com.petra.final_exam_work.exception.ApiException;
import com.petra.final_exam_work.repository.UserRepository;
import com.petra.final_exam_work.security.CustomUserDetails;
import com.petra.final_exam_work.security.SecurityUtils;
import com.petra.final_exam_work.service.memberAccess.MemberAccessService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final MeMapper meMapper;
    private final MemberAccessService memberAccessService;

    public UserService(UserRepository userRepository, MeMapper meMapper, MemberAccessService memberAccessService) {
        this.userRepository = userRepository;
        this.meMapper = meMapper;
        this.memberAccessService = memberAccessService;
    }

    //###########GET USERNAME ############
    public UserNameResponse getUsername(
            CustomUserDetails userDetails
    ) {

        UUID publicUuid = SecurityUtils.getCurrentUserPublicUuid();

        User user = userRepository.findByPublicUuid(publicUuid)
                .orElseThrow(() -> new ApiException("User was not found", HttpStatus.NOT_FOUND));

        memberAccessService.validateMemberAccess(user);

        return meMapper.toDto(user);
    }

}

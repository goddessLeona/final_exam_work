package com.petra.final_exam_work.service;

import com.petra.final_exam_work.dto.mapperDto.MeMapper;
import com.petra.final_exam_work.dto.responseDto.MeResponse;
import com.petra.final_exam_work.entity.user.User;
import com.petra.final_exam_work.exception.ApiException;
import com.petra.final_exam_work.repository.UserRepository;
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

//#################################### GET USERNAME ##################################################

    public MeResponse getusername(UUID publicid){

        User user = userRepository.findByPublicUuid(publicid)
                .orElseThrow(() -> new ApiException("User was not found", HttpStatus.NOT_FOUND));

        return meMapper.toDto(user);
    }


}

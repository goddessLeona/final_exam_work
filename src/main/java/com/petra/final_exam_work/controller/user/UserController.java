package com.petra.final_exam_work.controller.user;

import com.petra.final_exam_work.dto.responseDto.MeResponse;
import com.petra.final_exam_work.entity.user.User;
import com.petra.final_exam_work.exception.ApiException;
import com.petra.final_exam_work.security.CustomUserDetails;
import com.petra.final_exam_work.security.CustomUserDetailsService;
import com.petra.final_exam_work.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

// ################################## GET USER NAME #################################################
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/username")
    public ResponseEntity<MeResponse> getUsername(
            @AuthenticationPrincipal CustomUserDetails userDetails
    ){

        if(userDetails == null){
            throw new ApiException("User not authenticated", HttpStatus.UNAUTHORIZED);
        }

        return ResponseEntity.ok(userService.getusername(userDetails.getPublicUuid()));
    }
}

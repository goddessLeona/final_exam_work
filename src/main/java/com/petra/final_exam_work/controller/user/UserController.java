package com.petra.final_exam_work.controller.user;

import com.petra.final_exam_work.dto.responseDto.UserNameResponse;
import com.petra.final_exam_work.security.CustomUserDetails;
import com.petra.final_exam_work.service.user.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


// ################################## GET username/Me logged in #########################

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/username")
    public ResponseEntity<UserNameResponse> getUsername(
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {

        UserNameResponse response = userService.getUsername(userDetails);

        return ResponseEntity.ok(response);
    }

}

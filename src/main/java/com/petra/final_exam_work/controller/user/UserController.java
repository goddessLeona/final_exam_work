package com.petra.final_exam_work.controller.user;

import com.petra.final_exam_work.dto.requestDto.ContributorSignUpRequest;
import com.petra.final_exam_work.dto.responseDto.ContributorSignUpResponse;
import com.petra.final_exam_work.dto.responseDto.MeResponse;
import com.petra.final_exam_work.exception.ApiException;
import com.petra.final_exam_work.security.CustomUserDetails;
import com.petra.final_exam_work.service.user.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<MeResponse> getUsername(
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {

        return ResponseEntity.ok(userService.getUsername());
    }

// ################################## Sign up contributor ##############################

    @PostMapping("/signup-contributor")
    public ResponseEntity<ContributorSignUpResponse> signUpContributor (
            @Valid @RequestBody ContributorSignUpRequest request
    ) {

        ContributorSignUpResponse response = userService.signUpContributor(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }


    //##########test####### REMOVE LATER



    @GetMapping("/test")
    public String contributorTest() {
        return "Hello MEMBER, you are now authenticated !";
        }

}

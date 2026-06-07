package com.petra.final_exam_work.controller.contributor;

import com.petra.final_exam_work.dto.requestDto.contributor.ContributorSignUpRequest;
import com.petra.final_exam_work.dto.responseDto.contributor.ContributorSignUpResponse;
import com.petra.final_exam_work.service.contributor.ContributorSignUpService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class ContributorSignUpController {

    private final ContributorSignUpService contributorSignUpService;

    public ContributorSignUpController(ContributorSignUpService contributorSignUpService) {
        this.contributorSignUpService = contributorSignUpService;
    }

    // ########### Sign up contributor ###########
    @PostMapping("/signup-contributor")
    public ResponseEntity<ContributorSignUpResponse> signUpContributor (
            @Valid
            @RequestBody ContributorSignUpRequest request
    ) {

        ContributorSignUpResponse response = contributorSignUpService.signUpContributor(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }
}

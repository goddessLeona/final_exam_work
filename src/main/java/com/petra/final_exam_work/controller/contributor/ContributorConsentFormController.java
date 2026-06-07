package com.petra.final_exam_work.controller.contributor;

import com.petra.final_exam_work.dto.requestDto.ContributorConsentFormRequest;
import com.petra.final_exam_work.dto.responseDto.contributor.ContributorConsentFormResponse;
import com.petra.final_exam_work.dto.responseDto.contributor.ContributorWelcomeResponse;
import com.petra.final_exam_work.security.CustomUserDetails;
import com.petra.final_exam_work.service.contributor.ContributorConsentFormService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/contributor")
public class ContributorConsentFormController {

    private final ContributorConsentFormService contributorConsentFormService;

    public ContributorConsentFormController(ContributorConsentFormService contributorConsentFormService) {
        this.contributorConsentFormService = contributorConsentFormService;
    }

    // ################ GET welcome message ##################

    @PreAuthorize("hasRole('CONTRIBUTOR')")
    @GetMapping("/welcome")
    public ResponseEntity<ContributorWelcomeResponse> getWelcome (
            @AuthenticationPrincipal CustomUserDetails userDetails
    ){
        return ResponseEntity.ok(contributorConsentFormService.getWelcomeMessage());
    }

    // ############## GET consent form ###############################

    @PreAuthorize("hasRole('CONTRIBUTOR')")
    @GetMapping("/consent")
    public ResponseEntity<ContributorConsentFormResponse> getConsentForm(
            @AuthenticationPrincipal CustomUserDetails userDetails
    ){
        return ResponseEntity.ok(contributorConsentFormService.getConsentFormStatus());
    }

    // ############# POST consent form ##########################

    @PreAuthorize("hasRole('CONTRIBUTOR')")
    @PostMapping(
            value = "/consent",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<ContributorConsentFormResponse> postConsentForm(
            @ModelAttribute ContributorConsentFormRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails

    ){
        return ResponseEntity.ok(contributorConsentFormService.postConsentForm(request));
    }
}

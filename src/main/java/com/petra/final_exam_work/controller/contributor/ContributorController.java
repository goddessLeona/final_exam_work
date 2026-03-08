package com.petra.final_exam_work.controller.contributor;

import com.petra.final_exam_work.dto.requestDto.ContributorConsentFormRequest;
import com.petra.final_exam_work.dto.responseDto.ContributorConsentFormResponse;
import com.petra.final_exam_work.dto.responseDto.ContributorMeResponse;
import com.petra.final_exam_work.dto.responseDto.ContributorWelcomeResponse;
import com.petra.final_exam_work.security.CustomUserDetails;
import com.petra.final_exam_work.service.contributor.ContributorService;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/contributor")
public class ContributorController {

    private final ContributorService contributorService;

    public ContributorController(ContributorService contributorService) {
        this.contributorService = contributorService;
    }


    // #################### GET info about Contributor #########################
    @PreAuthorize("hasRole('CONTRIBUTOR')")
    @GetMapping("/info")
    public ResponseEntity<ContributorMeResponse> getInfo(
            @AuthenticationPrincipal CustomUserDetails userDetails
    ){
        return ResponseEntity.ok(contributorService.getInfoContributor());
    }

    // ################ GET welcome message ##################

    @PreAuthorize("hasRole('CONTRIBUTOR')")
    @GetMapping("/welcome")
    public ResponseEntity<ContributorWelcomeResponse> getWelcome (
            @AuthenticationPrincipal CustomUserDetails userDetails
    ){
        return ResponseEntity.ok(contributorService.getWelcomeMessage());
    }

    // ############## GET consent form ###############################3

    @PreAuthorize("hasRole('CONTRIBUTOR')")
    @GetMapping("/consent")
    public ResponseEntity<ContributorConsentFormResponse> getConsentForm(
            @AuthenticationPrincipal CustomUserDetails userDetails
    ){
        return ResponseEntity.ok(contributorService.getConsentFormStatus());
    }

    // ############# POST consent form ##########################

    @PreAuthorize("hasRole('CONTRIBUTOR')")
    @PostMapping(
            value = "/consent",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<ContributorConsentFormResponse> postConsentForm(
            @ModelAttribute ContributorConsentFormRequest request
    ){
        return ResponseEntity.ok(contributorService.postConsentForm(request));
    }
}

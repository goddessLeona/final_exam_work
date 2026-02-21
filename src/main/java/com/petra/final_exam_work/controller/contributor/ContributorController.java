package com.petra.final_exam_work.controller.contributor;

import com.petra.final_exam_work.dto.responseDto.ContributorMeResponse;
import com.petra.final_exam_work.dto.responseDto.ContributorWelcomeResponse;
import com.petra.final_exam_work.security.CustomUserDetails;
import com.petra.final_exam_work.service.contributor.ContributorService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}

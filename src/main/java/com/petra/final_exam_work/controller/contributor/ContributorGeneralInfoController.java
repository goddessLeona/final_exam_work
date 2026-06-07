package com.petra.final_exam_work.controller.contributor;

import com.petra.final_exam_work.dto.responseDto.contributor.ContributorInfo.ContributorAlbumStatsResponse;
import com.petra.final_exam_work.security.CustomUserDetails;
import com.petra.final_exam_work.service.contributor.ContributorGeneralInfoService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/contributor/general")
public class ContributorGeneralInfoController {

    private final ContributorGeneralInfoService contributorGeneralInfoService;

    public ContributorGeneralInfoController(ContributorGeneralInfoService contributorGeneralInfoService) {
        this.contributorGeneralInfoService = contributorGeneralInfoService;
    }

    // #################### GET info about Contributor #########################
    @PreAuthorize("hasRole('CONTRIBUTOR')")
    @GetMapping("/info")
    public ResponseEntity<ContributorAlbumStatsResponse> getInfo(
            @AuthenticationPrincipal CustomUserDetails userDetails
    ){
        ContributorAlbumStatsResponse response = contributorGeneralInfoService.getInfoContributor(
                userDetails
        );

        return ResponseEntity.ok(response);
    }
}

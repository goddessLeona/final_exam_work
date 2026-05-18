package com.petra.final_exam_work.controller.contributor;

import com.petra.final_exam_work.dto.responseDto.contributor.PhotoContent.ContributorPhotoAlbumResponse;
import com.petra.final_exam_work.entity.enums.ContentStatus;
import com.petra.final_exam_work.security.CustomUserDetails;
import com.petra.final_exam_work.service.contributor.ContributorPhotoAlbumService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/contributor/albums")
public class ContributorPhotoAlbumController {

    private final ContributorPhotoAlbumService contributorPhotoAlbumService;

    public ContributorPhotoAlbumController(ContributorPhotoAlbumService contributorPhotoAlbumService) {
        this.contributorPhotoAlbumService = contributorPhotoAlbumService;
    }

    @PreAuthorize("hasRole('CONTRIBUTOR')")
    @GetMapping("/list")
    public ResponseEntity<Page<ContributorPhotoAlbumResponse>> getPhotoAlbums(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestParam ContentStatus status,
            Pageable pageable
            ) {

        Page<ContributorPhotoAlbumResponse> response =
                contributorPhotoAlbumService.getPhotoAlbumInfo(
                        userDetails,
                        status,
                        pageable
                );

        return ResponseEntity.ok(response);


    }
}

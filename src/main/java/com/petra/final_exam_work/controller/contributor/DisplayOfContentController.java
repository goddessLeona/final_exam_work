package com.petra.final_exam_work.controller.contributor;

import com.petra.final_exam_work.dto.responseDto.contributor.PhotoContent.ContributorPhotoAlbumResponse;
import com.petra.final_exam_work.entity.enums.ContentStatus;
import com.petra.final_exam_work.security.CustomUserDetails;
import com.petra.final_exam_work.service.contributor.ContributorPhotoAlbumService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/contributor/albums")
public class DisplayOfContentController {

    private final ContributorPhotoAlbumService contributorPhotoAlbumService;

    public DisplayOfContentController(ContributorPhotoAlbumService contributorPhotoAlbumService) {
        this.contributorPhotoAlbumService = contributorPhotoAlbumService;
    }

    @PreAuthorize("hasRole('CONTRIBUTOR')")
    @GetMapping
    public ResponseEntity<List<ContributorPhotoAlbumResponse>> getPhotoAlbums(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestParam ContentStatus status
            ) {

        List<ContributorPhotoAlbumResponse> response =
                contributorPhotoAlbumService.getPhotoAlbumInfo(
                        userDetails,
                        status
                );

        return ResponseEntity.ok(response);


    }
}

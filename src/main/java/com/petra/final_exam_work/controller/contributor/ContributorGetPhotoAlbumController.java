package com.petra.final_exam_work.controller.contributor;

import com.petra.final_exam_work.dto.responseDto.contributor.PhotoContent.ContributorPhotoAlbumResponse;
import com.petra.final_exam_work.dto.responseDto.members.GetPhotoAlbumsResponse;
import com.petra.final_exam_work.entity.enums.ContentStatus;
import com.petra.final_exam_work.security.CustomUserDetails;
import com.petra.final_exam_work.service.contributor.ContributorPhotoAlbumService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/contributor/albums")
public class ContributorGetPhotoAlbumController {

    private final ContributorPhotoAlbumService contributorPhotoAlbumService;

    public ContributorGetPhotoAlbumController(ContributorPhotoAlbumService contributorPhotoAlbumService) {
        this.contributorPhotoAlbumService = contributorPhotoAlbumService;
    }

    //######## GET CoverPhotos from all uploaded content ############
    @PreAuthorize("hasRole('CONTRIBUTOR')")
    @GetMapping("/cover-photo")
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

    //######## GET to album from cover photo ############
    @PreAuthorize("hasRole('CONTRIBUTOR')")
    @GetMapping("/{albumPublicUuid}")
    public ResponseEntity<GetPhotoAlbumsResponse> getPhotoAlbum (
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable UUID albumPublicUuid
    ) {
        return ResponseEntity.ok(
                contributorPhotoAlbumService.getPhotoAlbum(
                        albumPublicUuid,
                        userDetails
                )
        );
    }


}

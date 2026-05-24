package com.petra.final_exam_work.controller.contributor;

import com.petra.final_exam_work.dto.requestDto.contributor.editUploadedPhotos.EditCoverPhotoRequest;
import com.petra.final_exam_work.dto.requestDto.contributor.editUploadedPhotos.EditTitleAndDescriptionRequest;
import com.petra.final_exam_work.dto.responseDto.contributor.EditAlbum.EditCoverPhotoResponse;
import com.petra.final_exam_work.dto.responseDto.contributor.EditAlbum.EditTitleAndDescriptionResponse;
import com.petra.final_exam_work.security.CustomUserDetails;
import com.petra.final_exam_work.service.contributor.ContributorEditAlbumService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/contributor/albums")
public class EditAlbumController {

    private final ContributorEditAlbumService contributorEditAlbumService;

    public EditAlbumController(ContributorEditAlbumService contributorEditAlbumService) {
        this.contributorEditAlbumService = contributorEditAlbumService;
    }

    @PreAuthorize("hasRole('CONTRIBUTOR')")
    @PatchMapping("/{albumPublicUuid}/title-description")
    public ResponseEntity<EditTitleAndDescriptionResponse> editTitleAndDescription(
            @PathVariable UUID albumPublicUuid,
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @Valid
            @RequestBody
            EditTitleAndDescriptionRequest request
    ) {
        EditTitleAndDescriptionResponse response =
                contributorEditAlbumService.editTitleAndDescription(
                        albumPublicUuid,
                        userDetails,
                        request
                );

        return ResponseEntity.ok(response);
    }

    //######### Edit cover photo on uploaded content #######
    @PreAuthorize("hasRole('CONTRIBUTOR')")
    @PatchMapping("/{albumPublicUuid}/cover-photo")
    public ResponseEntity<EditCoverPhotoResponse> editCoverPhoto(
            @PathVariable UUID albumPublicUuid,
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @Valid
            @RequestBody
            EditCoverPhotoRequest request
    ){
        EditCoverPhotoResponse response =
                contributorEditAlbumService.editCoverPhoto(
                        albumPublicUuid,
                        userDetails,
                        request
                );

        return ResponseEntity.ok(response);
    }
}

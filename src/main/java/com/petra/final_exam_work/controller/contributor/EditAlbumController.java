package com.petra.final_exam_work.controller.contributor;

import com.petra.final_exam_work.dto.requestDto.contributor.editUploadedPhotos.*;
import com.petra.final_exam_work.dto.responseDto.contributor.EditAlbum.EditCoverPhotoResponse;
import com.petra.final_exam_work.dto.responseDto.contributor.EditAlbum.EditTitleAndDescriptionResponse;
import com.petra.final_exam_work.dto.responseDto.members.GetPhotoAlbumsResponse;
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

    //######### Edit title and description on album #######
    @PreAuthorize("hasRole('CONTRIBUTOR')")
    @PatchMapping("/{albumPublicUuid}/title-description")
    public ResponseEntity<EditTitleAndDescriptionResponse> editTitleAndDescription(
            @PathVariable UUID albumPublicUuid,
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @Valid
            @RequestBody EditTitleAndDescriptionRequest request
    ) {
        EditTitleAndDescriptionResponse response =
                contributorEditAlbumService.editTitleAndDescription(
                        albumPublicUuid,
                        userDetails,
                        request
                );

        return ResponseEntity.ok(response);
    }

    //######### Edit cover photo #######
    @PreAuthorize("hasRole('CONTRIBUTOR')")
    @PatchMapping("/{albumPublicUuid}/cover-photo")
    public ResponseEntity<EditCoverPhotoResponse> editCoverPhoto(
            @PathVariable UUID albumPublicUuid,
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @Valid
            @RequestBody EditCoverPhotoRequest request
    ){
        EditCoverPhotoResponse response =
                contributorEditAlbumService.editCoverPhoto(
                        albumPublicUuid,
                        userDetails,
                        request
                );

        return ResponseEntity.ok(response);
    }

    //######### Delete photo from uploaded album #######
    @PreAuthorize("hasRole('CONTRIBUTOR')")
    @DeleteMapping("/{albumPublicUuid}/photos")
    public ResponseEntity<Void> deletePhoto(
            @PathVariable UUID albumPublicUuid,
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody DeletePhotoRequest request
    ) {

        contributorEditAlbumService.deletePhoto(
                albumPublicUuid,
                userDetails,
                request
        );

        return ResponseEntity.noContent().build();
    }

    //######### Add photo to uploaded album #######
    @PreAuthorize("hasRole('CONTRIBUTOR')")
    @PostMapping("/{albumPublicUuid}/photos")
    public ResponseEntity<GetPhotoAlbumsResponse> addPhoto(
            @PathVariable UUID albumPublicUuid,
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @ModelAttribute AddPhotoRequest request
    ) {

        GetPhotoAlbumsResponse response =
                contributorEditAlbumService.addPhoto(
                        albumPublicUuid,
                        userDetails,
                        request
                );

        return ResponseEntity.ok(response);
    }

    //######### Reorder photo in album #######
    @PreAuthorize("hasRole('CONTRIBUTOR')")
    @PatchMapping("/{albumPublicUuid}/reorder")
    public ResponseEntity<GetPhotoAlbumsResponse> reorderPhotos(
            @PathVariable UUID albumPublicUuid,
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody ReorderPhotosRequest request
    ) {
        GetPhotoAlbumsResponse response =
                contributorEditAlbumService.reorderPhotos(
                        albumPublicUuid,
                        userDetails,
                        request
                );

        return ResponseEntity.ok(response);
    }

    //######### Change status Album #######
    @PreAuthorize("hasRole('CONTRIBUTOR')")
    @PatchMapping("/{albumPublicUuid}/status")
    public ResponseEntity<Void> editStatus(
            @PathVariable UUID albumPublicUuid,
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody EditStatusAlbumRequest request
            ) {

        contributorEditAlbumService.editStatus(
                albumPublicUuid,
                userDetails,
                request
        );

        return ResponseEntity.noContent().build();
    }

    //######### Change publishing date #######
    @PreAuthorize("hasRole('CONTRIBUTOR')")
    @PatchMapping("/{albumPublicUuid}/scheduled")
    public ResponseEntity<GetPhotoAlbumsResponse> editScheduled(
            @PathVariable UUID albumPublicUuid,
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody EditPublishedDateRequest request
    ) {
        GetPhotoAlbumsResponse response =
        contributorEditAlbumService.editPublishingDate(
                albumPublicUuid,
                userDetails,
                request
        );
        return ResponseEntity.ok(response);
    }

}

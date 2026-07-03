package com.petra.final_exam_work.controller.contributor;

import com.petra.final_exam_work.dto.requestDto.contributor.UploadPhotoContentRequest;
import com.petra.final_exam_work.dto.responseDto.contributor.ContributorUploadPhotos.UploadContentResponse;
import com.petra.final_exam_work.security.CustomUserDetails;
import com.petra.final_exam_work.service.contributor.ContributorUploadPhotosService;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/contributor/upload")
public class ContributorUploadPhotosController {

    private final ContributorUploadPhotosService contributorUploadPhotosService;

    public ContributorUploadPhotosController(ContributorUploadPhotosService contributorUploadPhotosService) {
        this.contributorUploadPhotosService = contributorUploadPhotosService;
    }

    // ##### POST -UPLOAD PHOTOS TO ALBUM ######
    @PreAuthorize("hasRole('CONTRIBUTOR')")
    @PostMapping(
            value = "/photo",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<UploadContentResponse> uploadPhotos (
            @Valid @ModelAttribute UploadPhotoContentRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) throws IOException {

        UploadContentResponse response = contributorUploadPhotosService.uploadContent(userDetails, request);

        return ResponseEntity.ok(response);
    }
}

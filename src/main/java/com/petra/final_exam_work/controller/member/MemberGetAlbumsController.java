package com.petra.final_exam_work.controller.member;

import com.petra.final_exam_work.dto.responseDto.members.GetCoverPhotoAlbumsResponse;
import com.petra.final_exam_work.dto.responseDto.members.GetPhotoAlbumsResponse;
import com.petra.final_exam_work.entity.enums.ContentType;
import com.petra.final_exam_work.security.CustomUserDetails;
import com.petra.final_exam_work.service.member.MemberPhotoAlbumsService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/member")
public class MemberGetAlbumsController {

    private final MemberPhotoAlbumsService memberPhotoAlbumsService;

    public MemberGetAlbumsController(MemberPhotoAlbumsService memberPhotoAlbumsService) {
        this.memberPhotoAlbumsService = memberPhotoAlbumsService;
    }

    //####### Get all cover photos from all albums that are published #######
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/albums")
    public ResponseEntity<Page<GetCoverPhotoAlbumsResponse>> getAlbumCovers(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestParam ContentType contentType,
            Pageable pageable
            ) {

        return ResponseEntity.ok(
                memberPhotoAlbumsService.getCoverPhotoAlbums(
                        userDetails,
                        contentType,
                        pageable
                )
        );
    }

    //######## GET to album from cover photo ############
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/albums/{albumPublicUuid}")
    public ResponseEntity<GetPhotoAlbumsResponse> getAlbum(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestParam UUID albumPublicUuid
    ) {

        return ResponseEntity.ok(
                memberPhotoAlbumsService.getPhotoAlbum(
                        albumPublicUuid,
                        userDetails
                )
        );

    }

}

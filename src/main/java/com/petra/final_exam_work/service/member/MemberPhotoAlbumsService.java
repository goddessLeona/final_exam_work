package com.petra.final_exam_work.service.member;

import com.petra.final_exam_work.dto.mapperDto.member.GetCoverPhotoAlbumsMapper;
import com.petra.final_exam_work.dto.mapperDto.member.GetPhotoAlbumsMapper;
import com.petra.final_exam_work.dto.responseDto.members.GetCoverPhotoAlbumsResponse;
import com.petra.final_exam_work.entity.enums.ContentStatus;
import com.petra.final_exam_work.entity.enums.ContentType;
import com.petra.final_exam_work.entity.photo.PhotoAlbum;
import com.petra.final_exam_work.entity.user.User;
import com.petra.final_exam_work.exception.ApiException;
import com.petra.final_exam_work.repository.PhotoAlbumRepository;
import com.petra.final_exam_work.repository.UserRepository;
import com.petra.final_exam_work.security.CustomUserDetails;
import com.petra.final_exam_work.service.memberAccess.MemberAccessService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class MemberPhotoAlbumsService {

    private final GetCoverPhotoAlbumsMapper getCoverPhotoAlbumsMapper;
    private final GetPhotoAlbumsMapper getPhotoAlbumsMapper;
    private final PhotoAlbumRepository photoAlbumRepository;
    private final UserRepository userRepository;
    private final MemberAccessService memberAccessService;

    public MemberPhotoAlbumsService(GetCoverPhotoAlbumsMapper getCoverPhotoAlbumsMapper, GetPhotoAlbumsMapper getPhotoAlbumsMapper, PhotoAlbumRepository photoAlbumRepository, UserRepository userRepository, MemberAccessService memberAccessService) {
        this.getCoverPhotoAlbumsMapper = getCoverPhotoAlbumsMapper;
        this.getPhotoAlbumsMapper = getPhotoAlbumsMapper;
        this.photoAlbumRepository = photoAlbumRepository;
        this.userRepository = userRepository;
        this.memberAccessService = memberAccessService;
    }

    //###### GET all cover photos from all albums that are published #######

    public Page<GetCoverPhotoAlbumsResponse> getCoverPhotoAlbums(
            CustomUserDetails userDetails,
            ContentType contentType,
            Pageable pageable) {

        UUID publicUuid = userDetails.getPublicUuid();
        User user = userRepository.findByPublicUuid(publicUuid)
                .orElseThrow(() -> new ApiException("User was not found", HttpStatus.NOT_FOUND));


        memberAccessService.validateMemberAccess(user);

        Page<PhotoAlbum> albums =
                photoAlbumRepository.findAllByContentStatusAndContentTypeOrderByPublishedAtDesc(
                        ContentStatus.PUBLISHED,
                        contentType,
                        pageable
                );

        return albums.map(getCoverPhotoAlbumsMapper::toDto);
    }


    //############ GET photo album #################
}

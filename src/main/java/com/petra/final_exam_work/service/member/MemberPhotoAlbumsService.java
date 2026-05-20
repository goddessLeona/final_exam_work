package com.petra.final_exam_work.service.member;

import com.petra.final_exam_work.dto.mapperDto.member.GetCoverPhotoAlbumsMapper;
import com.petra.final_exam_work.dto.mapperDto.member.GetPhotoAlbumsMapper;
import com.petra.final_exam_work.dto.responseDto.members.GetCoverPhotoAlbumsResponse;
import com.petra.final_exam_work.dto.responseDto.members.GetPhotoAlbumsResponse;
import com.petra.final_exam_work.entity.enums.ContentStatus;
import com.petra.final_exam_work.entity.enums.ContentType;
import com.petra.final_exam_work.entity.junktionTables.photoAlbumPhoto.PhotoAlbumPhoto;
import com.petra.final_exam_work.entity.photo.Photo;
import com.petra.final_exam_work.entity.photo.PhotoAlbum;
import com.petra.final_exam_work.entity.user.User;
import com.petra.final_exam_work.exception.ApiException;
import com.petra.final_exam_work.repository.PhotoAlbumPhotoRepository;
import com.petra.final_exam_work.repository.PhotoAlbumRepository;
import com.petra.final_exam_work.repository.UserRepository;
import com.petra.final_exam_work.security.CustomUserDetails;
import com.petra.final_exam_work.service.memberAccess.MemberAccessService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class MemberPhotoAlbumsService {

    private final GetCoverPhotoAlbumsMapper getCoverPhotoAlbumsMapper;
    private final PhotoAlbumRepository photoAlbumRepository;
    private final PhotoAlbumPhotoRepository photoAlbumPhotoRepository;
    private final UserRepository userRepository;
    private final MemberAccessService memberAccessService;
    private final GetPhotoAlbumsMapper getPhotoAlbumsMapper;

    public MemberPhotoAlbumsService(GetCoverPhotoAlbumsMapper getCoverPhotoAlbumsMapper, PhotoAlbumRepository photoAlbumRepository, PhotoAlbumPhotoRepository photoAlbumPhotoRepository, UserRepository userRepository, MemberAccessService memberAccessService, GetPhotoAlbumsMapper getPhotoAlbumsMapper) {
        this.getCoverPhotoAlbumsMapper = getCoverPhotoAlbumsMapper;
        this.photoAlbumRepository = photoAlbumRepository;
        this.photoAlbumPhotoRepository = photoAlbumPhotoRepository;
        this.userRepository = userRepository;
        this.memberAccessService = memberAccessService;
        this.getPhotoAlbumsMapper = getPhotoAlbumsMapper;
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


    //######## GET to album from cover photo ############
    public GetPhotoAlbumsResponse getPhotoAlbum(
            UUID albumPublicUuid,
            CustomUserDetails userDetails
    ) {

        UUID publicUuid = userDetails.getPublicUuid();
        User user = userRepository.findByPublicUuid(publicUuid)
                .orElseThrow(() -> new ApiException("User was not found", HttpStatus.NOT_FOUND));

        memberAccessService.validateMemberAccess(user);

        PhotoAlbum album = photoAlbumRepository
                .findByPublicUuidAndContentStatus(
                        albumPublicUuid,
                        ContentStatus.PUBLISHED
                )
                .orElseThrow(() -> new ApiException(
                        "Album not found",
                        HttpStatus.NOT_FOUND
                ));

        List<Photo> photos = photoAlbumPhotoRepository
                .findByPhotoAlbumOrderByPositionAsc(album)
                .stream()
                .map(PhotoAlbumPhoto::getPhoto)
                .toList();

        return getPhotoAlbumsMapper.toDto(album, photos);
    }
}

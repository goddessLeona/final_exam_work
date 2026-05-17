package com.petra.final_exam_work.service.contributor;

import com.petra.final_exam_work.dto.mapperDto.contributor.ContributorPhotoAlbumMapper;
import com.petra.final_exam_work.dto.responseDto.contributor.PhotoContent.ContributorPhotoAlbumResponse;
import com.petra.final_exam_work.entity.enums.ContentStatus;
import com.petra.final_exam_work.entity.enums.ContributorStatus;
import com.petra.final_exam_work.entity.photo.PhotoAlbum;
import com.petra.final_exam_work.entity.user.User;
import com.petra.final_exam_work.exception.ApiException;
import com.petra.final_exam_work.repository.PhotoAlbumRepository;
import com.petra.final_exam_work.repository.UserRepository;
import com.petra.final_exam_work.security.CustomUserDetails;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ContributorPhotoAlbumService {

    private final ContributorPhotoAlbumMapper contributorPhotoAlbumMapper;
    private final UserRepository userRepository;
    private final PhotoAlbumRepository photoAlbumRepository;

    public ContributorPhotoAlbumService(ContributorPhotoAlbumMapper contributorPhotoAlbumMapper, UserRepository userRepository, PhotoAlbumRepository photoAlbumRepository) {
        this.contributorPhotoAlbumMapper = contributorPhotoAlbumMapper;
        this.userRepository = userRepository;
        this.photoAlbumRepository = photoAlbumRepository;
    }

    //################## Get list PhotoAlbums ############################
    private Page<ContributorPhotoAlbumResponse> getPhotoAlbumInfo(CustomUserDetails userDetails, ContentStatus status, Pageable pageable) {

        UUID publicUuid = userDetails.getPublicUuid();
        User user = userRepository.findByPublicUuid(publicUuid)
                .orElseThrow(() -> new ApiException("User was not found", HttpStatus.NOT_FOUND));


        if (user.getContributorStatus() != ContributorStatus.APPROVED) {
            throw new ApiException(
                    "You are not approved to have access to this data",
                    HttpStatus.FORBIDDEN
            );
        }

        Page<PhotoAlbum> albums = photoAlbumRepository.findByOwnerUserAndContentStatus(
                user,
                status,
                pageable
        );

        return albums.map(contributorPhotoAlbumMapper::toResponse);
    }
}

package com.petra.final_exam_work.service.contributor;

import com.petra.final_exam_work.dto.mapperDto.contributor.editAlbum.EditTitleAndContributorMapper;
import com.petra.final_exam_work.dto.requestDto.contributor.editUploadedPhotos.EditTitleAndDescriptionRequest;
import com.petra.final_exam_work.dto.responseDto.contributor.EditAlbum.EditTitleAndDescriptionResponse;
import com.petra.final_exam_work.entity.enums.ContributorStatus;
import com.petra.final_exam_work.entity.photo.PhotoAlbum;
import com.petra.final_exam_work.entity.user.User;
import com.petra.final_exam_work.exception.ApiException;
import com.petra.final_exam_work.repository.PhotoAlbumRepository;
import com.petra.final_exam_work.repository.UserRepository;
import com.petra.final_exam_work.security.CustomUserDetails;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class ContributorEditAlbumService {

    private final EditTitleAndContributorMapper editTitleAndContributorMapper;
    private final PhotoAlbumRepository photoAlbumRepository;
    private final UserRepository userRepository;

    public ContributorEditAlbumService(EditTitleAndContributorMapper editTitleAndContributorMapper, PhotoAlbumRepository photoAlbumRepository, UserRepository userRepository) {
        this.editTitleAndContributorMapper = editTitleAndContributorMapper;
        this.photoAlbumRepository = photoAlbumRepository;
        this.userRepository = userRepository;
    }

    //######### Edit title and description on uploaded content #######
    @Transactional
    public EditTitleAndDescriptionResponse editTitleAndDescription(
            UUID albumPublicUuid,
            CustomUserDetails userDetails,
            EditTitleAndDescriptionRequest request
    ) {

        UUID publicUuid = userDetails.getPublicUuid();
        User user = userRepository.findByPublicUuid(publicUuid)
                .orElseThrow(() -> new ApiException("User was not found", HttpStatus.NOT_FOUND));


        if (user.getContributorStatus() != ContributorStatus.APPROVED) {
            throw new ApiException(
                    "You are not approved to have access to this data",
                    HttpStatus.FORBIDDEN
            );
        }

        PhotoAlbum album = photoAlbumRepository
                .findByPublicUuid(albumPublicUuid)
                .orElseThrow(() -> new ApiException(
                        "Album not found",
                        HttpStatus.NOT_FOUND
                ));

        if (!album.getOwnedByUser().getId().equals(user.getId())) {
            throw new ApiException(
                    "You do not have access to this album",
                    HttpStatus.FORBIDDEN
            );
        }

        editTitleAndContributorMapper.updatePhotoAlbum(album, request);

        photoAlbumRepository.save(album);

        return editTitleAndContributorMapper.toDto(album);
    }
}

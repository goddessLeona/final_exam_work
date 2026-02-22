package com.petra.final_exam_work.service.contributor;

import com.petra.final_exam_work.dto.mapperDto.ContributorMeMapper;
import com.petra.final_exam_work.dto.mapperDto.ContributorWelcomeMapper;
import com.petra.final_exam_work.dto.responseDto.ContributorMeResponse;
import com.petra.final_exam_work.dto.responseDto.ContributorWelcomeResponse;
import com.petra.final_exam_work.entity.consentForm.ConsentStatus;
import com.petra.final_exam_work.entity.user.User;
import com.petra.final_exam_work.exception.ApiException;
import com.petra.final_exam_work.repository.PhotoAlbumRepository;
import com.petra.final_exam_work.repository.UserConsentFormRepository;
import com.petra.final_exam_work.repository.UserRepository;
import com.petra.final_exam_work.security.SecurityUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ContributorService {

    private final UserRepository userRepository;
    private final PhotoAlbumRepository photoAlbumRepository;
    private final UserConsentFormRepository userConsentFormRepository;
    private final ContributorMeMapper contributorMeMapper;
    private final ContributorWelcomeMapper contributorWelcomeMapper;

    public ContributorService(UserRepository userRepository, PhotoAlbumRepository photoAlbumRepository, UserConsentFormRepository userConsentFormRepository, ContributorMeMapper contributorMeMapper, ContributorWelcomeMapper contributorWelcomeMapper) {
        this.userRepository = userRepository;
        this.photoAlbumRepository = photoAlbumRepository;
        this.userConsentFormRepository = userConsentFormRepository;
        this.contributorMeMapper = contributorMeMapper;
        this.contributorWelcomeMapper = contributorWelcomeMapper;
    }

    public ContributorMeResponse getInfoContributor() {

        UUID publicUuid = SecurityUtils.getCurrentUserPublicUuid();
        User user = userRepository.findByPublicUuid(publicUuid)
                .orElseThrow(() -> new ApiException("User was not found", HttpStatus.NOT_FOUND));

        ConsentStatus consentStatus = null;
        Integer albumCount = null;
        String message = null;

        if (user.isContributor()) {
            // count albums by numeric user ID
            albumCount = (int) photoAlbumRepository.countByOwnedByUser_id(user.getId());
        } else {
            // get consent status by numeric user ID
            consentStatus = userConsentFormRepository.findStatusByUser(user.getId())
                    .orElse(null);

            if (consentStatus == null) {
                message = "You have to fill in your consent form to be able to upload content";
            }
        }

        return contributorMeMapper.toResponse(user, consentStatus, albumCount, message);
    }

    public ContributorWelcomeResponse getWelcomeMessage(){

        UUID publicUuid = SecurityUtils.getCurrentUserPublicUuid();
        User user = userRepository.findByPublicUuid(publicUuid)
                .orElseThrow(() -> new ApiException("User was not found", HttpStatus.NOT_FOUND));

        String message = null;

        if (user.isContributor()){
            message = "Welcome back contributor!";
        }else{
            message = "Welcome new contributor! Hope you will enjoy our smale community. Before you are able to " +
                    "contribute and post your own photos or enter member pages, you have to fill in the agreement forms.";
        }

        return contributorWelcomeMapper.toResponse(user, message);
    }

}

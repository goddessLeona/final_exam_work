package com.petra.final_exam_work.service.contributor;

import com.petra.final_exam_work.dto.mapperDto.ContributorConsentFormMapper;
import com.petra.final_exam_work.dto.mapperDto.ContributorMeMapper;
import com.petra.final_exam_work.dto.mapperDto.ContributorWelcomeMapper;
import com.petra.final_exam_work.dto.requestDto.ContributorConsentFormRequest;
import com.petra.final_exam_work.dto.responseDto.ContributorConsentFormResponse;
import com.petra.final_exam_work.dto.responseDto.ContributorMeResponse;
import com.petra.final_exam_work.dto.responseDto.ContributorWelcomeResponse;
import com.petra.final_exam_work.entity.consentForm.ConsentForm;
import com.petra.final_exam_work.entity.consentForm.ConsentStatus;
import com.petra.final_exam_work.entity.junktionTables.userConcentform.UserConsentForm;
import com.petra.final_exam_work.entity.user.User;
import com.petra.final_exam_work.exception.ApiException;
import com.petra.final_exam_work.repository.PhotoAlbumRepository;
import com.petra.final_exam_work.repository.UserConsentFormRepository;
import com.petra.final_exam_work.repository.UserRepository;
import com.petra.final_exam_work.security.SecurityUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
public class ContributorService {

    private final UserRepository userRepository;
    private final PhotoAlbumRepository photoAlbumRepository;
    private final UserConsentFormRepository userConsentFormRepository;
    private final ContributorMeMapper contributorMeMapper;
    private final ContributorWelcomeMapper contributorWelcomeMapper;
    private final ContributorConsentFormMapper contributorConsentFormMapper;

    public ContributorService(UserRepository userRepository, PhotoAlbumRepository photoAlbumRepository, UserConsentFormRepository userConsentFormRepository, ContributorMeMapper contributorMeMapper, ContributorWelcomeMapper contributorWelcomeMapper, ContributorConsentFormMapper contributorConsentFormMapper) {
        this.userRepository = userRepository;
        this.photoAlbumRepository = photoAlbumRepository;
        this.userConsentFormRepository = userConsentFormRepository;
        this.contributorMeMapper = contributorMeMapper;
        this.contributorWelcomeMapper = contributorWelcomeMapper;
        this.contributorConsentFormMapper = contributorConsentFormMapper;
    }

    //####################### INFO CONTRIBUTOR ######################

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

    //##################### WELCOME MESSAGE ##########################

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

    // ################### GET Contributor - form ###################333

    @Transactional(readOnly = true)
    public ContributorConsentFormResponse getConsentFormStatus() {

        UUID publicUuid = SecurityUtils.getCurrentUserPublicUuid();

        User user = userRepository.findByPublicUuid(publicUuid)
                .orElseThrow(() -> new ApiException("User not found", HttpStatus.NOT_FOUND));

        Optional<UserConsentForm> optional =
                userConsentFormRepository.findByUser(user);

        if (optional.isEmpty()) {
            // No form yet
            return ContributorConsentFormResponse.builder()
                    .contributor(user.isContributor())
                    .consentStatus(null)
                    .build();
        }

        UserConsentForm userConsentForm = optional.get();
        ConsentForm consentForm = userConsentForm.getConsentForm();

        return contributorConsentFormMapper.toResponse(
                consentForm,
                userConsentForm.getConsentStatus(),
                user
        );
    }

    //################### CONSENT FORM ######################

    @Transactional
    public ContributorConsentFormResponse postConsentForm(
            ContributorConsentFormRequest request
    ) {

        UUID publicUuid = SecurityUtils.getCurrentUserPublicUuid();

        User user = userRepository.findByPublicUuid(publicUuid)
                .orElseThrow(() -> new ApiException("User not found", HttpStatus.NOT_FOUND));

        if (user.isContributor()) {
            throw new ApiException("Already approved contributor", HttpStatus.BAD_REQUEST);
        }

        Optional<UserConsentForm> optional =
                userConsentFormRepository.findByUser(user);

        ConsentForm consentForm;

        if (optional.isEmpty()) {
            // FIRST SUBMISSION
            consentForm = new ConsentForm();
            consentForm.setPublicUuid(UUID.randomUUID());
        } else {
            consentForm = optional.get().getConsentForm();
        }

        // Upload and overwrite files if provided
        if (request.getIdCardFile() != null) {
            String path = fileStorageService.save(request.getIdCardFile());
            consentForm.setIdCardFilePath(path);
            consentForm.setIdCardReviewed(false); // needs review again
        }

        if (request.getIdFaceFile() != null) {
            String path = fileStorageService.save(request.getIdFaceFile());
            consentForm.setIdFaceFilePath(path);
            consentForm.setIdFaceReviewed(false);
        }

        if (request.getFacefffFile() != null) {
            String path = fileStorageService.save(request.getFacefffFile());
            consentForm.setFacefffFilePath(path);
            consentForm.setFacefffReviewed(false);
        }

        consentForm.setApprovedRules(request.getApprovedRules());

        consentFormRepository.save(consentForm);

        UserConsentForm userConsentForm;

        if (optional.isEmpty()) {
            userConsentForm = new UserConsentForm();
            userConsentForm.setUser(user);
            userConsentForm.setConsentForm(consentForm);
        } else {
            userConsentForm = optional.get();
        }

        userConsentForm.setConsentStatus(ConsentStatus.PENDING);

        userConsentFormRepository.save(userConsentForm);

        return contributorConsentFormMapper.toResponse(
                consentForm,
                userConsentForm.getConsentStatus(),
                user
        );
    }

}

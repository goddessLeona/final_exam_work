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
import com.petra.final_exam_work.repository.ConsentFormRepository;
import com.petra.final_exam_work.repository.PhotoAlbumRepository;
import com.petra.final_exam_work.repository.UserConsentFormRepository;
import com.petra.final_exam_work.repository.UserRepository;
import com.petra.final_exam_work.security.SecurityUtils;
import com.petra.final_exam_work.service.FileStorageService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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
    private final ConsentFormRepository consentFormRepository;
    private final FileStorageService fileStorageService;

    public ContributorService(UserRepository userRepository, PhotoAlbumRepository photoAlbumRepository,
                              UserConsentFormRepository userConsentFormRepository, ContributorMeMapper contributorMeMapper,
                              ContributorWelcomeMapper contributorWelcomeMapper, ContributorConsentFormMapper contributorConsentFormMapper,
                              ConsentFormRepository consentFormRepository, FileStorageService fileStorageService) {
        this.userRepository = userRepository;
        this.photoAlbumRepository = photoAlbumRepository;
        this.userConsentFormRepository = userConsentFormRepository;
        this.contributorMeMapper = contributorMeMapper;
        this.contributorWelcomeMapper = contributorWelcomeMapper;
        this.contributorConsentFormMapper = contributorConsentFormMapper;
        this.consentFormRepository = consentFormRepository;
        this.fileStorageService = fileStorageService;
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
            message = "Welcome new contributor! Hope you will enjoy our smale community. This is your private page from " +
                    "where you in the future can post all your content. Create a profile and see your statistic and more." +
                    " Before you are able to contribute and post your own photos or enter member pages, you first have " +
                    "to fill in the agreement forms. This is to prevent people to upload photos from their ex lovers or " +
                    "friends. To make sure it is your photos and that you are over 18 years old";
        }

        return contributorWelcomeMapper.toResponse(user, message);
    }

    // ################### GET - user consent form ###################

    @Transactional(readOnly = true)
    public ContributorConsentFormResponse getConsentFormStatus() {

        UUID publicUuid = SecurityUtils.getCurrentUserPublicUuid();

        User user = userRepository.findByPublicUuid(publicUuid)
                .orElseThrow(() -> new ApiException("User not found", HttpStatus.NOT_FOUND));

        Optional<UserConsentForm> form =
                userConsentFormRepository.findByUser(user);

        System.out.println("FORM EXISTS: " + form.isPresent());

        if (form.isEmpty()) {

           ContributorConsentFormResponse response = new ContributorConsentFormResponse();

           response.setContributor(user.isContributor());
           response.setConsentStatus(ConsentStatus.NOT_SUBMITTED);

           return response;
        }

        UserConsentForm userConsentForm = form.get();
        ConsentForm consentForm = userConsentForm.getConsentForm();

        return contributorConsentFormMapper.toResponse(
                consentForm,
                userConsentForm.getConsentStatus(),
                user
        );
    }

    //################### POST - User consent form ######################

    @Transactional
    public ContributorConsentFormResponse postConsentForm(
            ContributorConsentFormRequest request
    ) {

        UUID publicUuid = SecurityUtils.getCurrentUserPublicUuid();

        User user = userRepository.findByPublicUuid(publicUuid)
                .orElseThrow(() -> new ApiException("User not found", HttpStatus.NOT_FOUND));

        if (user.isContributor()) {
            throw new ApiException("Already approved contributor", HttpStatus.CONFLICT);
        }

        Optional<UserConsentForm> form =
                userConsentFormRepository.findByUser(user);

        if (form.isPresent() &&
                form.get().getConsentStatus() == ConsentStatus.PENDING) {
            throw new ApiException(
                    "Your documents are already under review",
                    HttpStatus.CONFLICT
            );
        }

        ConsentForm consentForm;
        UserConsentForm userConsentForm;
        boolean update = false;

        if (form.isEmpty()) {
            // FIRST SUBMISSION
            if(
                    request.getIdCardFile() == null ||
                    request.getIdFaceFile() == null ||
                    request.getFacefffFile() == null
            ){
                throw new ApiException("You have to upload all required documents", HttpStatus.BAD_REQUEST);
            }

            consentForm = new ConsentForm();
            userConsentForm = new UserConsentForm();
            userConsentForm.setUser(user);
            userConsentForm.setConsentForm(consentForm);
            update = true;

        } else {
            userConsentForm = form.get();
            consentForm = form.get().getConsentForm();
        }

        // ID CARD
        if (request.getIdCardFile() != null) {

            if(Boolean.TRUE.equals(consentForm.getIdCardReviewed())){
                throw new ApiException(
                        "Id- card is already approved",
                        HttpStatus.BAD_REQUEST
                );
            }

            String path = handleImageUpload(
                    request.getIdCardFile(),
                    user.getPublicUuid(),
                    "consent",
            "id-card"
            );

            consentForm.setIdCardFilePath(path);
            consentForm.setIdCardReviewed(null);
            update = true;
        }

        // ID CARD + FACE
        if (request.getIdFaceFile() != null) {

            if(Boolean.TRUE.equals(consentForm.getIdFaceReviewed())){
                throw new ApiException(
                        "Id + Face photo already approved",
                        HttpStatus.BAD_REQUEST
                );
            }

            String path = handleImageUpload(
                    request.getIdFaceFile(),
                    user.getPublicUuid(),
                    "consent",
                    "id-card/face"
            );

            consentForm.setIdFaceFilePath(path);
            consentForm.setIdFaceReviewed(null);
            update = true;
        }

        // FFF + FACE
        if(request.getFacefffFile() != null) {

            if(Boolean.TRUE.equals(consentForm.getFacefffReviewed())){
                throw new ApiException(
                        "This document is already approved",
                        HttpStatus.BAD_REQUEST
                );
            }

            String path = handleImageUpload(
                    request.getFacefffFile(),
                    user.getPublicUuid(),
                    "consent",
                    "FFF/face"
            );

            consentForm.setFacefffFilePath(path);
            consentForm.setFacefffReviewed(null);
            update = true;
        }

        // Approve rules
        if (!request.getApprovedRules()) {
            throw new ApiException("You must approve the rules", HttpStatus.BAD_REQUEST);
        }

        consentForm.setApprovedRules(request.getApprovedRules());

        // save form
        consentFormRepository.save(consentForm);

        // Set form status
        if(update){
            userConsentForm.setConsentStatus(ConsentStatus.PENDING);
        }

        // save users consent form
        userConsentFormRepository.save(userConsentForm);

        return contributorConsentFormMapper.toResponse(
                consentForm,
                userConsentForm.getConsentStatus(),
                user
        );
    }

    // ---------------- Private helper ----------------
    private String handleImageUpload(
            MultipartFile file,
            UUID userUuid,
            String category,
            String filePrefix
    ) {
        if (file.isEmpty()) {
            throw new ApiException("File is empty", HttpStatus.BAD_REQUEST);
        }

        if (file.getContentType() == null || !file.getContentType().startsWith("image/")) {
            throw new ApiException("Only images allowed", HttpStatus.BAD_REQUEST);
        }

        fileStorageService.validateImage(file);

        return fileStorageService.save(file, userUuid, category, filePrefix);
    }

}

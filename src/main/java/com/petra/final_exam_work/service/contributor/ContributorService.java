package com.petra.final_exam_work.service.contributor;

import com.petra.final_exam_work.dto.mapperDto.ContributorConsentFormMapper;
import com.petra.final_exam_work.dto.mapperDto.ContributorMeMapper;
import com.petra.final_exam_work.dto.mapperDto.ContributorWelcomeMapper;
import com.petra.final_exam_work.dto.requestDto.ContributorConsentFormRequest;
import com.petra.final_exam_work.dto.responseDto.ContributorConsentFormResponse;
import com.petra.final_exam_work.dto.responseDto.ContributorMeResponse;
import com.petra.final_exam_work.dto.responseDto.ContributorWelcomeResponse;
import com.petra.final_exam_work.entity.consentForm.ConsentForm;
import com.petra.final_exam_work.entity.consentForm.ConsentFormStatus;
import com.petra.final_exam_work.entity.consentForm.ReviewStatus;
import com.petra.final_exam_work.entity.enums.ContributorStatus;
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

import java.util.HashMap;
import java.util.Map;
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

        Integer albumCount = null;
        albumCount = (int) photoAlbumRepository.countByOwnedByUser_id(user.getId());

        return contributorMeMapper.toResponse(user, albumCount);
    }

    //##################### WELCOME MESSAGE ##########################

    public ContributorWelcomeResponse getWelcomeMessage(){

        UUID publicUuid = SecurityUtils.getCurrentUserPublicUuid();
        User user = userRepository.findByPublicUuid(publicUuid)
                .orElseThrow(() -> new ApiException("User was not found", HttpStatus.NOT_FOUND));

        String message = null;

        switch (user.getContributorStatus()) {

            case APPROVED:
                message = "Welcome back contributor!";
                break;

            case PENDING:
                message = "Your contributor application is under review";
                break;

            case REJECTED:
                message = "Your consent form application was rejected. Please review the feedback and try again.";
                break;

            case TEMP_BANNED:
                message = "Your acount is temporaily restricted. Please contact support if needed";
                break;

            case BANNED:
                message = "Your account is taken down and banned";
                break;

            case NOT_APPLIED:
            default:
                message = "Welcome new contributor! Hope you will enjoy our smale community. This is your private page from " +
                        "where you in the future can post all your content. Create a profile and see your statistic and more." +
                        " Before you are able to contribute and post your own photos or enter member pages, you first have " +
                        "to fill in the agreement forms. This is to prevent people to upload photos from their ex lovers or " +
                        "friends. To make sure it is your photos and that you are over 18 years old";
                break;
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
        System.out.println("user status:" + user.getContributorStatus() );

        if (form.isEmpty()) {

           ContributorConsentFormResponse response = new ContributorConsentFormResponse();

           response.setStatus(user.getContributorStatus());
           response.setConsentFormStatus(ConsentFormStatus.NOT_SUBMITTED);

           return response;
        }

        UserConsentForm userConsentForm = form.get();
        ConsentForm consentForm = userConsentForm.getConsentForm();
        ContributorStatus contributorStatus = user.getContributorStatus();

        return contributorConsentFormMapper.toResponse(
                consentForm,
                userConsentForm.getConsentFormStatus(),
                contributorStatus,
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

        ContributorStatus contributorStatus = user.getContributorStatus();

        if (contributorStatus == ContributorStatus.APPROVED) {
            throw new ApiException(
                    "Already approved contributor",
                    HttpStatus.CONFLICT);
        }

        Optional<UserConsentForm> form = userConsentFormRepository.findByUser(user);

        ConsentForm consentForm;
        UserConsentForm userConsentForm;
        boolean update = false;

        if (form.isEmpty()) {
            //FIRST SUBMISSION

            if (!request.getApprovedRules()) {
                throw new ApiException(
                        "Validation failed",
                        Map.of("approvedRules", "You must approve the rules"),
                        HttpStatus.BAD_REQUEST
                );
            }

            Map<String, String> errors = new HashMap<>();

            if (request.getIdCardFile() == null) {
                errors.put("idCardFile", "Id card is required");
            }
            if (request.getIdFaceFile() == null) {
                errors.put("idFaceFile", "Id + Face photo is required");
            }
            if (request.getFacefffFile() == null) {
                errors.put("facefffFile", "FFF photo is required");
            }

            if (!errors.isEmpty()) {
                throw new ApiException("Validation failed", errors, HttpStatus.BAD_REQUEST);
            }
        }

         if (form.isEmpty()) {
            consentForm = new ConsentForm();
            userConsentForm = new UserConsentForm();
            userConsentForm.setUser(user);
            userConsentForm.setConsentForm(consentForm);
        } else {
            userConsentForm = form.get();
            consentForm = userConsentForm.getConsentForm();

            if (userConsentForm.getConsentFormStatus() == ConsentFormStatus.PENDING) {
                throw new ApiException("Your document are already under review", HttpStatus.CONFLICT);
            }
        }

        // upload ID card
        if (request.getIdCardFile() != null) {

            if (consentForm.getIdCardReviewed() == ReviewStatus.APPROVED) {
                throw new ApiException(
                        "Validation failed",
                        Map.of( "idCardFile", "Id- card is already approved"),
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
            consentForm.setIdCardReviewed(ReviewStatus.PENDING);
            update = true;
        }

        // upload ID CARD + FACE
        if (request.getIdFaceFile() != null) {

            if (consentForm.getIdFaceReviewed() == ReviewStatus.APPROVED) {
                throw new ApiException(
                        "Validation failed",
                        Map.of( "idFaceFile","Id + Face photo already approved"),
                        HttpStatus.BAD_REQUEST
                );
            }

            String path = handleImageUpload(
                    request.getIdFaceFile(),
                    user.getPublicUuid(),
                    "consent",
                    "id-card-face"
            );

            consentForm.setIdFaceFilePath(path);
            consentForm.setIdFaceReviewed(ReviewStatus.PENDING);
            update = true;
        }

        // upload image FFF + FACE
        if (request.getFacefffFile() != null) {

            if (consentForm.getFacefffReviewed() == ReviewStatus.APPROVED) {
                throw new ApiException(
                        "Validation failed",
                        Map.of( "facefffFile", "This document is already approved"),
                        HttpStatus.BAD_REQUEST
                );
            }

            String path = handleImageUpload(
                    request.getFacefffFile(),
                    user.getPublicUuid(),
                    "consent",
                    "FFF-face"
            );

            consentForm.setFacefffFilePath(path);
            consentForm.setFacefffReviewed(ReviewStatus.PENDING);
            update = true;
        }

        if (consentForm.getApprovedRules() == Boolean.TRUE) {
        }

        // Save consent form
        consentFormRepository.save(consentForm);

        if (update) {
            userConsentForm.setConsentFormStatus(ConsentFormStatus.PENDING);
            user.setContributorStatus(ContributorStatus.PENDING);

            userConsentFormRepository.save(userConsentForm);
            userRepository.save(user);
        }

        ContributorConsentFormResponse response = contributorConsentFormMapper.toResponse(
                consentForm,
                userConsentForm.getConsentFormStatus(),
                contributorStatus,
                user
        );

        return response;

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

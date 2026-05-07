package com.petra.final_exam_work.service.admin;

import com.petra.final_exam_work.dto.mapperDto.admin.AdminConsentFormDataMapper;
import com.petra.final_exam_work.dto.mapperDto.admin.AdminDashboardCFMapper;
import com.petra.final_exam_work.dto.requestDto.admin.AdminConsentReviewRequest;
import com.petra.final_exam_work.dto.responseDto.admin.AdminConsentFormDataResponse.ConsentFormDataResponse;
import com.petra.final_exam_work.dto.responseDto.admin.AdminDashboardConsentFormresponse.AdminDashboardConsentFormItem;
import com.petra.final_exam_work.dto.responseDto.admin.AdminDashboardConsentFormresponse.AdminDashboardConsentFormResponse;
import com.petra.final_exam_work.dto.responseDto.admin.AdminDashboardConsentFormresponse.AdminDashboardConsentFormSection;
import com.petra.final_exam_work.entity.consentForm.ConsentForm;
import com.petra.final_exam_work.entity.consentForm.ConsentFormStatus;
import com.petra.final_exam_work.entity.consentForm.ReviewStatus;
import com.petra.final_exam_work.entity.enums.ContributorStatus;
import com.petra.final_exam_work.entity.junktionTables.userConcentform.UserConsentForm;
import com.petra.final_exam_work.entity.user.User;
import com.petra.final_exam_work.exception.ApiException;
import com.petra.final_exam_work.repository.ConsentFormRepository;
import com.petra.final_exam_work.repository.UserConsentFormRepository;
import com.petra.final_exam_work.repository.UserRepository;
import com.petra.final_exam_work.security.CustomUserDetails;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.core.io.Resource;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class AdminService {

    private final AdminDashboardCFMapper adminDashboardCFMapper;
    private final UserRepository userRepository;
    private final UserConsentFormRepository userConsentFormRepository;
    private final AdminConsentFormDataMapper adminConsentFormDataMapper;
    private final ConsentFormRepository consentFormRepository;

    public AdminService(AdminDashboardCFMapper adminDashboardCFMapper, UserRepository userRepository, UserConsentFormRepository userConsentFormRepository, AdminConsentFormDataMapper adminConsentFormDataMapper, ConsentFormRepository consentFormRepository) {
        this.adminDashboardCFMapper = adminDashboardCFMapper;
        this.userRepository = userRepository;
        this.userConsentFormRepository = userConsentFormRepository;
        this.adminConsentFormDataMapper = adminConsentFormDataMapper;
        this.consentFormRepository = consentFormRepository;
    }


    //##### GET Admin dashboard consent form summary ######

    public AdminDashboardConsentFormResponse getDashboard (CustomUserDetails userDetails){

        UUID publicUuid = userDetails.getPublicUuid();
        User user = userRepository.findByPublicUuid(publicUuid)
                .orElseThrow(() -> new ApiException("User was not found", HttpStatus.NOT_FOUND));

        List<UserConsentForm> allUserConsentForms = userConsentFormRepository.findAll();

        Map<ConsentFormStatus, List <UserConsentForm>> grouped =
            allUserConsentForms.stream().collect(Collectors.groupingBy(UserConsentForm :: getConsentFormStatus));

        AdminDashboardConsentFormResponse response = new AdminDashboardConsentFormResponse();
        response.setTotal(allUserConsentForms.size());

        response.setPending(buildSection(grouped.getOrDefault(ConsentFormStatus.PENDING, List.of())));
        response.setApproved(buildSection(grouped.getOrDefault(ConsentFormStatus.APPROVED, List.of( ))));
        response.setRejected(buildSection(grouped.getOrDefault(ConsentFormStatus.REJECTED, List.of())));
        response.setNotSubmitted(buildSection(grouped.getOrDefault(ConsentFormStatus.NOT_SUBMITTED, List.of())));

        return response;
    }

    private long countByStatus(ConsentForm cf, ReviewStatus status) {
        return Stream.of(
                cf.getIdCardReviewed(),
                cf.getIdFaceReviewed(),
                cf.getFacefffReviewed()
        ).filter(s -> s == status).count();
    }

    private AdminDashboardConsentFormSection buildSection(List<UserConsentForm> list) {

        List<AdminDashboardConsentFormItem> latest = list.stream()
                .sorted(Comparator.comparing(UserConsentForm::getCreatedAt).reversed())
                .limit(5)
                .map(ucf -> {

                    ConsentForm cf = ucf.getConsentForm();

                    long pending = countByStatus(cf, ReviewStatus.PENDING);
                    long approved = countByStatus(cf,ReviewStatus.APPROVED);
                    long rejected = countByStatus(cf, ReviewStatus.REJECTED);

                    return adminDashboardCFMapper.toItem(ucf, pending, approved, rejected);
                })
                .toList();

        AdminDashboardConsentFormSection section = new AdminDashboardConsentFormSection();
        section.setTotal(list.size());
        section.setLatest(latest);

        return section;
    }

    //##### GET Admin-ConsentForm Data #####

    public ResponseEntity<Resource> getDocumentData (UUID publicUuid, String type) {

        UserConsentForm userConsentForm =userConsentFormRepository
                .findByConsentForm_PublicUuid(publicUuid)
                .orElseThrow(() -> new ApiException("Document not found", HttpStatus.NOT_FOUND));

        String path = switch (type) {
            case "id-card" -> userConsentForm.getConsentForm().getIdCardFilePath();
            case "id-face" -> userConsentForm.getConsentForm().getIdFaceFilePath();
            case "face-fff" -> userConsentForm.getConsentForm().getFacefffFilePath();
            default -> throw new IllegalArgumentException("Invalid type");
        };

        Path file = Paths.get(path);

        if(!Files.exists(file)){
            throw new ApiException("File not found", HttpStatus.NOT_FOUND);
        }

        String contentType;
        try {
            contentType = Files.probeContentType(file);
        }catch (IOException e) {
            contentType = "application/octet-stream";
        }

        Resource resource;

        try {
            resource = new UrlResource(file.toUri());
        } catch (MalformedURLException e) {
            throw new ApiException("Invalid file path", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE,
                        contentType !=null ? contentType : "application/octet-stream")
                .body(resource);
    }

    //##### GET consentFormData id #####

    public ConsentFormDataResponse getConsentFormDataId(UUID id) {

        UserConsentForm ucf = userConsentFormRepository
                .findByConsentForm_PublicUuid(id)
                .orElseThrow(() -> new ApiException("Not found", HttpStatus.NOT_FOUND));

        return adminConsentFormDataMapper.toDto(ucf);
    }

    //##### admin Review the consent form status #####

    @Transactional
    public void reviewConsentForm (UUID id, AdminConsentReviewRequest request) {

        UserConsentForm ucf = userConsentFormRepository
                .findByConsentForm_PublicUuid(id)
                .orElseThrow(() -> new ApiException("Consent form not found",
                        HttpStatus.NOT_FOUND));

        ConsentForm cf = ucf.getConsentForm();
        System.out.println(request);

        if (request.getIdCardStatus() != null) {
            cf.setIdCardReviewed(request.getIdCardStatus());
            cf.setIdCardMessage(request.getIdCardMessage());
        }

        if (request.getIdFaceStatus() != null) {
            cf.setIdFaceReviewed(request.getIdFaceStatus());
            cf.setIdFaceMessage(request.getIdFaceMessage());
        }

        if (request.getFacefffStatus() != null) {
            cf.setFacefffReviewed(request.getFacefffStatus());
            cf.setFacefffMessage(request.getFacefffMessage());
        }

        boolean allApproved =
                cf.getIdCardReviewed() == ReviewStatus.APPROVED &&
                cf.getIdFaceReviewed() == ReviewStatus.APPROVED &&
                cf.getFacefffReviewed() == ReviewStatus.APPROVED;

        boolean anyRejected =
                cf.getIdCardReviewed() == ReviewStatus.REJECTED ||
                cf.getIdFaceReviewed() == ReviewStatus.REJECTED ||
                cf.getFacefffReviewed() == ReviewStatus.REJECTED;

        User user = ucf.getUser();

        if (allApproved) {
            ucf.setConsentFormStatus(ConsentFormStatus.APPROVED);
            user.setContributorStatus(ContributorStatus.APPROVED);
            userRepository.save(user);

        } else if (anyRejected){
            ucf.setConsentFormStatus(ConsentFormStatus.REJECTED);
            user.setContributorStatus(ContributorStatus.REJECTED);
            userRepository.save(user);

        } else {
            ucf.setConsentFormStatus(ConsentFormStatus.PENDING);
            user.setContributorStatus(ContributorStatus.PENDING);
            userRepository.save(user);
        }

        consentFormRepository.save(cf);
        userConsentFormRepository.save(ucf);
    }

}

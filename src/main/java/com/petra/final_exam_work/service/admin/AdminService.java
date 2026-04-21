package com.petra.final_exam_work.service.admin;

import com.petra.final_exam_work.dto.mapperDto.admin.AdminDashboardCFMapper;
import com.petra.final_exam_work.dto.responseDto.admin.AdminDashboardConsentFormresponse.AdminDashboardConsentFormItem;
import com.petra.final_exam_work.dto.responseDto.admin.AdminDashboardConsentFormresponse.AdminDashboardConsentFormResponse;
import com.petra.final_exam_work.dto.responseDto.admin.AdminDashboardConsentFormresponse.AdminDashboardConsentFormSection;
import com.petra.final_exam_work.entity.consentForm.ConsentForm;
import com.petra.final_exam_work.entity.consentForm.ConsentFormStatus;
import com.petra.final_exam_work.entity.consentForm.ReviewStatus;
import com.petra.final_exam_work.entity.junktionTables.userConcentform.UserConsentForm;
import com.petra.final_exam_work.entity.user.User;
import com.petra.final_exam_work.exception.ApiException;
import com.petra.final_exam_work.repository.UserConsentFormRepository;
import com.petra.final_exam_work.repository.UserRepository;
import com.petra.final_exam_work.security.CustomUserDetails;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

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

    public AdminService(AdminDashboardCFMapper adminDashboardCFMapper, UserRepository userRepository, UserConsentFormRepository userConsentFormRepository) {
        this.adminDashboardCFMapper = adminDashboardCFMapper;
        this.userRepository = userRepository;
        this.userConsentFormRepository = userConsentFormRepository;
    }


    //############################# GET Admin dashboard consent form summary #######################

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

    //########################## GET Admin dashboard consent form data #######################
}

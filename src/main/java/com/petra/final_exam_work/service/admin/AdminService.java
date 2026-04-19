package com.petra.final_exam_work.service.admin;

import com.petra.final_exam_work.dto.mapperDto.AdminConsentFormItemMapper;
import com.petra.final_exam_work.dto.responseDto.AdminDashboardConsentFormresponse.AdminConsentFormItem;
import com.petra.final_exam_work.dto.responseDto.AdminDashboardConsentFormresponse.AdminDashboardConsentFormResponse;
import com.petra.final_exam_work.dto.responseDto.AdminDashboardConsentFormresponse.DashboardSection;
import com.petra.final_exam_work.entity.consentForm.ConsentFormStatus;
import com.petra.final_exam_work.entity.junktionTables.userConcentform.UserConsentForm;
import com.petra.final_exam_work.entity.user.User;
import com.petra.final_exam_work.exception.ApiException;
import com.petra.final_exam_work.repository.UserConsentFormRepository;
import com.petra.final_exam_work.repository.UserRepository;
import com.petra.final_exam_work.security.CustomUserDetails;
import com.petra.final_exam_work.security.SecurityUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AdminService {

    private final AdminConsentFormItemMapper adminConsentFormItemMapper;
    private final UserRepository userRepository;
    private final UserConsentFormRepository userConsentFormRepository;

    public AdminService(AdminConsentFormItemMapper adminConsentFormItemMapper, UserRepository userRepository, UserConsentFormRepository userConsentFormRepository) {
        this.adminConsentFormItemMapper = adminConsentFormItemMapper;
        this.userRepository = userRepository;
        this.userConsentFormRepository = userConsentFormRepository;
    }


    //############################# GET Admin dashboard consent form #######################

    public AdminDashboardConsentFormResponse getDashboard (CustomUserDetails userDetails){

        UUID publicUuid = userDetails.getPublicUuid();
        User user = userRepository.findByPublicUuid(publicUuid)
                .orElseThrow(() -> new ApiException("User was not found", HttpStatus.NOT_FOUND));

        List<UserConsentForm> allUserConsentForms = userConsentFormRepository.findAll();

        Map<ConsentFormStatus, List <UserConsentForm>> grouped =
            allUserConsentForms.stream().collect(Collectors.groupingBy(UserConsentForm :: getConsentFormStatus));

        AdminDashboardConsentFormResponse response = new AdminDashboardConsentFormResponse();
        response.setTotal(allUserConsentForms.size());

        response.setPending(buildSection(grouped.get(ConsentFormStatus.PENDING)));
        response.setApproved(buildSection(grouped.get(ConsentFormStatus.APPROVED)));
        response.setRejected(buildSection(grouped.get(ConsentFormStatus.REJECTED)));
        response.setNotSubmitted(buildSection(grouped.get(ConsentFormStatus.NOT_SUBMITTED)));

        return response;
    }

    private DashboardSection buildSection(List<UserConsentForm> list) {

        if (list == null) {
            return new DashboardSection(0, List.of());
        }

        List<AdminConsentFormItem> latest = list.stream()
                .sorted(Comparator.comparing(UserConsentForm::getCreatedAt).reversed())
                .limit(5)
                .map(ucf -> adminConsentFormItemMapper.toItem(ucf.getConsentForm(), ucf))
                .toList();

        DashboardSection section = new DashboardSection();
        section.setTotal(list.size());
        section.setLatest(latest);

        return section;
    }
}

package com.petra.final_exam_work.dto.mapperDto.admin;

import com.petra.final_exam_work.dto.responseDto.admin.AdminDashboardConsentFormresponse.AdminDashboardConsentFormItem;
import com.petra.final_exam_work.entity.junktionTables.userConcentform.UserConsentForm;
import org.springframework.stereotype.Component;

@Component
public class AdminDashboardCFMapper {

    public AdminDashboardConsentFormItem toItem(
            UserConsentForm ucf,
            long pending,
            long approved,
            long rejected
    ) {
        AdminDashboardConsentFormItem item = new AdminDashboardConsentFormItem();

        item.setUsername(ucf.getUser().getUsername());
        item.setDocumentsPending(pending);
        item.setDocumentsApproved(approved);
        item.setDocumentsRejected(rejected);
        item.setConsentFormStatus(ucf.getConsentFormStatus());
        item.setConsentFormId(ucf.getConsentForm().getPublicUuid());

        return item;
    }
}

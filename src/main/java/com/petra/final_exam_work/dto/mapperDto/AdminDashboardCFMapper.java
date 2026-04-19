package com.petra.final_exam_work.dto.mapperDto;

import com.petra.final_exam_work.dto.responseDto.AdminDashboardConsentFormresponse.AdminConsentFormItem;
import com.petra.final_exam_work.entity.junktionTables.userConcentform.UserConsentForm;
import org.springframework.stereotype.Component;

@Component
public class AdminDashboardCFMapper {

    public AdminConsentFormItem toItem(
            UserConsentForm ucf,
            long pending,
            long approved,
            long rejected
    ) {
        AdminConsentFormItem item = new AdminConsentFormItem();

        item.setUsername(ucf.getUser().getUsername());
        item.setDocumentsPending(pending);
        item.setDocumentsApproved(approved);
        item.setDocumentsRejected(rejected);
        item.setConsentFormStatus(ucf.getConsentFormStatus());
        item.setConsentFormId(ucf.getConsentForm().getPublicUuid());

        return item;
    }
}

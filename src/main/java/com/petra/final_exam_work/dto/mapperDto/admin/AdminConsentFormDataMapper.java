package com.petra.final_exam_work.dto.mapperDto.admin;

import com.petra.final_exam_work.dto.responseDto.admin.AdminConsentFormDataResponse.ConsentFormDataResponse;
import com.petra.final_exam_work.dto.responseDto.admin.AdminConsentFormDataResponse.DocumentDto;
import com.petra.final_exam_work.entity.consentForm.ConsentForm;
import com.petra.final_exam_work.entity.consentForm.ReviewStatus;
import com.petra.final_exam_work.entity.junktionTables.userConcentform.UserConsentForm;
import org.springframework.stereotype.Component;

@Component
public class AdminConsentFormDataMapper {

    private DocumentDto toDocumentDto(String filePath, ReviewStatus status) {
        DocumentDto dto = new DocumentDto();
        dto.setFilePath(filePath);
        dto.setStatus(status);

        return dto;
    }

    public ConsentFormDataResponse toDto ( UserConsentForm ucf) {

        ConsentForm cf = ucf.getConsentForm();
        ConsentFormDataResponse response = new ConsentFormDataResponse();

        response.setUsername(ucf.getUser().getUsername());
        response.setDocumentIdCard (toDocumentDto(cf.getIdCardFilePath(), cf.getIdCardReviewed()));
        response.setDocumentIdCardFace(toDocumentDto(cf.getIdFaceFilePath(), cf.getIdFaceReviewed()));
        response.setDocumentFaceFFF(toDocumentDto(cf.getFacefffFilePath(), cf.getFacefffReviewed()));
        response.setApprovedRules(cf.getApprovedRules());
        response.setConsentFormStatus(ucf.getConsentFormStatus());
        response.setConsentFormId(ucf.getConsentForm().getPublicUuid());

        return response;
    }
}

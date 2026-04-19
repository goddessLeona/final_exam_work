package com.petra.final_exam_work.dto.mapperDto;

import com.petra.final_exam_work.dto.responseDto.AdminDashboardConsentFormresponse.AdminConsentFormItem;
import com.petra.final_exam_work.entity.consentForm.ConsentForm;
import com.petra.final_exam_work.entity.junktionTables.userConcentform.UserConsentForm;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AdminConsentFormItemMapper {

    @Mapping(target = "documentId", source = "consentForm.idCardFilePath")
    @Mapping(target = "documentIdFace", source = "consentForm.idFaceFilePath")
    @Mapping(target = "documentfff", source = "consentForm.facefffFilePath")

    @Mapping(target = "idCardReviewed", source = "consentForm.idCardReviewed")
    @Mapping(target = "idFaceReviewed", source = "consentForm.idFaceReviewed")
    @Mapping(target = "facefffReviewed", source = "consentForm.facefffReviewed")
    @Mapping(target = "approvedRules", source = "consentForm.approvedRules")

    @Mapping(target = "consentFormStatus", source = "userConsentForm.consentFormStatus")
    AdminConsentFormItem toItem(ConsentForm consentForm, UserConsentForm userConsentForm);

}

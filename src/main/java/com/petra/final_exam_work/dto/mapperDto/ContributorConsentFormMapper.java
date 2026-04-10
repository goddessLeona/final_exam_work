package com.petra.final_exam_work.dto.mapperDto;

import com.petra.final_exam_work.dto.responseDto.ContributorConsentFormResponse;
import com.petra.final_exam_work.entity.consentForm.ConsentForm;
import com.petra.final_exam_work.entity.consentForm.ConsentFormStatus;

import com.petra.final_exam_work.entity.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ContributorConsentFormMapper {

    @Mapping(target = "idCardFilePath", source = "consentForm.idCardFilePath")
    @Mapping(target = "idCardReviewed", source = "consentForm.idCardReviewed")
    @Mapping(target = "idFaceFilePath", source = "consentForm.idFaceFilePath")
    @Mapping(target = "idFaceReviewed", source = "consentForm.idFaceReviewed")
    @Mapping(target = "facefffFilePath", source = "consentForm.facefffFilePath")
    @Mapping(target = "facefffReviewed", source = "consentForm.facefffReviewed")
    @Mapping(target = "approvedRules", source = "consentForm.approvedRules")
    @Mapping(target = "consentFormStatus", source = "consentFormStatus")
    @Mapping(target = "contributor", source = "user.contributor")
    ContributorConsentFormResponse toResponse(
            ConsentForm consentForm,
            ConsentFormStatus consentFormStatus,
            User user);
}

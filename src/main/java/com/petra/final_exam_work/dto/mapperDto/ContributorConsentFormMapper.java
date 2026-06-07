package com.petra.final_exam_work.dto.mapperDto;

import com.petra.final_exam_work.dto.responseDto.contributor.ContributorConsentFormResponse;
import com.petra.final_exam_work.entity.consentForm.ConsentForm;
import com.petra.final_exam_work.entity.consentForm.ConsentFormStatus;

import com.petra.final_exam_work.entity.enums.ContributorStatus;
import com.petra.final_exam_work.entity.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ContributorConsentFormMapper {

    @Mapping(target = "idCardFilePath", source = "consentForm.idCardFilePath")
    @Mapping(target = "idCardReviewed", source = "consentForm.idCardReviewed")
    @Mapping(target = "idCardMessage", source = "consentForm.idCardMessage")
    @Mapping(target = "idFaceFilePath", source = "consentForm.idFaceFilePath")
    @Mapping(target = "idFaceReviewed", source = "consentForm.idFaceReviewed")
    @Mapping(target = "idFaceMessage", source = "consentForm.idFaceMessage")
    @Mapping(target = "facefffFilePath", source = "consentForm.facefffFilePath")
    @Mapping(target = "facefffReviewed", source = "consentForm.facefffReviewed")
    @Mapping(target = "facefffMessage", source = "consentForm.facefffMessage")
    @Mapping(target = "approvedRules", source = "consentForm.approvedRules")
    @Mapping(target = "consentFormStatus", source = "consentFormStatus")
    @Mapping(target = "status", source = "status")
    ContributorConsentFormResponse toResponse(
            ConsentForm consentForm,
            ConsentFormStatus consentFormStatus,
            ContributorStatus status,
            User user);
}

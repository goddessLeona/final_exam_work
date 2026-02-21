package com.petra.final_exam_work.dto.mapperDto;

import com.petra.final_exam_work.dto.responseDto.ContributorMeResponse;
import com.petra.final_exam_work.entity.consentForm.ConsentStatus;
import com.petra.final_exam_work.entity.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ContributorMeMapper {

    @Mapping(target = "username", source = "user.userName")
    @Mapping(target = "yearSignedUp", expression = "java(user.getCreatedAt().getYear())")
    @Mapping(target = "isContributor", source = "user.isContributor")
    @Mapping(
            target = "consentStatus",
            expression = "java(user.isContributor() ? null : consentStatus)")
    @Mapping(target = "countPhotoAlbums", source = "countPhotoAlbums")
    @Mapping(target = "message", source = "message")
    ContributorMeResponse toResponse(
            User user,
            ConsentStatus consentStatus,
            Integer countPhotoAlbums,
            String message
    );
}

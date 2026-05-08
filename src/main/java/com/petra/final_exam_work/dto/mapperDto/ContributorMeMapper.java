package com.petra.final_exam_work.dto.mapperDto;

import com.petra.final_exam_work.dto.responseDto.ContributorMeResponse;
import com.petra.final_exam_work.entity.consentForm.ConsentFormStatus;
import com.petra.final_exam_work.entity.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ContributorMeMapper {

    @Mapping(target = "username", source = "user.username")
    @Mapping(target = "yearSignedUp", expression = "java(user.getCreatedAt().atZone(java.time.ZoneId.systemDefault()).getYear())")
    @Mapping(target = "countPhotoAlbums", source = "countPhotoAlbums")
    ContributorMeResponse toResponse(
            User user,
            Integer countPhotoAlbums
    );
}

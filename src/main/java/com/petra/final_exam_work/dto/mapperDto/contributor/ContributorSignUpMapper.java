package com.petra.final_exam_work.dto.mapperDto.contributor;

import com.petra.final_exam_work.dto.requestDto.contributor.ContributorSignUpRequest;
import com.petra.final_exam_work.entity.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ContributorSignUpMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "publicUuid", ignore = true)
    @Mapping(target = "username")
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "email", expression = "java(request.getEmail() != null ? request.getEmail().toLowerCase() : null)")
    @Mapping(target = "firstName")
    @Mapping(target = "lastName")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "consentForms", ignore = true)
    User toUser(ContributorSignUpRequest request);
}

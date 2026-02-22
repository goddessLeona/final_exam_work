package com.petra.final_exam_work.dto.mapperDto;

import com.petra.final_exam_work.dto.responseDto.ContributorWelcomeResponse;
import com.petra.final_exam_work.entity.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ContributorWelcomeMapper {

    @Mapping(target = "contributor", source = "user.contributor")
    @Mapping(target = "message", source = "message")
    ContributorWelcomeResponse toResponse(
            User user,
            String message
    );
}

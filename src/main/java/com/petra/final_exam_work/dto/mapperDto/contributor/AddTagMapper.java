package com.petra.final_exam_work.dto.mapperDto.contributor;

import com.petra.final_exam_work.dto.responseDto.contributor.ContributorUploadPhotos.AddTagResponse;
import com.petra.final_exam_work.entity.Tag;
import org.springframework.stereotype.Component;

@Component
public class AddTagMapper {

    public AddTagResponse toDto (Tag tag){

        AddTagResponse response = new AddTagResponse();

        response.setPublicUuid(tag.getPublicUuid());
        response.setNameTag(tag.getNameTag());

        return response;
    }
}

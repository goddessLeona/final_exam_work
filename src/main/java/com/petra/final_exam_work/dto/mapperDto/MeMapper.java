package com.petra.final_exam_work.dto.mapperDto;

import com.petra.final_exam_work.dto.responseDto.UserNameResponse;
import com.petra.final_exam_work.entity.user.User;
import org.springframework.stereotype.Component;

@Component
public class MeMapper {

    public UserNameResponse toDto(User user) {

        if (user == null) {
            return null;
        }

        return new UserNameResponse(
                user.getUsername()
        );

    }
}

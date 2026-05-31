package com.petra.final_exam_work.dto.mapperDto.contributor;

import com.petra.final_exam_work.dto.responseDto.contributor.ContributorInfo.ContentStatsResponse;
import com.petra.final_exam_work.dto.responseDto.contributor.ContributorInfo.ContributorAlbumStatsResponse;
import com.petra.final_exam_work.entity.user.User;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.util.List;

@Component
public class ContributorAlbumStatsMapper {

    public ContributorAlbumStatsResponse toDto(
            User user,
            List<ContentStatsResponse>stats
    ) {

        ContributorAlbumStatsResponse response = new ContributorAlbumStatsResponse();

        response.setUsername(user.getUsername());

        response.setYearSignedUp(
                user.getCreatedAt()
                        .atZone(ZoneId.systemDefault())
                        .getYear()
        );
        response.setContent(stats);

        return response;

    }
}

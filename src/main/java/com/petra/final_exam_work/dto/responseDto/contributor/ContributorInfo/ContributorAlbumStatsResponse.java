package com.petra.final_exam_work.dto.responseDto.contributor.ContributorInfo;

import java.util.List;

public class ContributorAlbumStatsResponse {

    private String username;
    private Integer yearSignedUp;
    private List<ContentStatsResponse> content;

    public ContributorAlbumStatsResponse() {
    }

    public ContributorAlbumStatsResponse(String username, Integer yearSignedUp, List<ContentStatsResponse> content) {
        this.username = username;
        this.yearSignedUp = yearSignedUp;
        this.content = content;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getYearSignedUp() {
        return yearSignedUp;
    }

    public void setYearSignedUp(Integer yearSignedUp) {
        this.yearSignedUp = yearSignedUp;
    }

    public List<ContentStatsResponse> getContent() {
        return content;
    }

    public void setContent(List<ContentStatsResponse> content) {
        this.content = content;
    }
}

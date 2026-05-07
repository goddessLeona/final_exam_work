package com.petra.final_exam_work.dto.responseDto;

import com.petra.final_exam_work.entity.consentForm.ConsentFormStatus;

public class ContributorMeResponse {

    private String username;
    private Integer yearSignedUp;
    private Integer countPhotoAlbums;

    public ContributorMeResponse() {
    }

    public ContributorMeResponse(String username, Integer yearSignedUp, Integer countPhotoAlbums) {
        this.username = username;
        this.yearSignedUp = yearSignedUp;
        this.countPhotoAlbums = countPhotoAlbums;
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

    public Integer getCountPhotoAlbums() {
        return countPhotoAlbums;
    }

    public void setCountPhotoAlbums(Integer countPhotoAlbums) {
        this.countPhotoAlbums = countPhotoAlbums;
    }
}

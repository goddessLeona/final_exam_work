package com.petra.final_exam_work.dto.responseDto;

import com.petra.final_exam_work.entity.consentForm.ConsentFormStatus;

public class ContributorMeResponse {

    private String username;
    private Integer yearSignedUp;

    private boolean contributor;
    private ConsentFormStatus consentFormStatus;

    private Integer countPhotoAlbums;

    private String message;

    public ContributorMeResponse() {
    }

    public ContributorMeResponse(String username, Integer yearSignedUp, boolean contributor,
                                 ConsentFormStatus consentFormStatus, Integer countPhotoAlbums, String message) {
        this.username = username;
        this.yearSignedUp = yearSignedUp;
        this.contributor = contributor;
        this.consentFormStatus = consentFormStatus;
        this.countPhotoAlbums = countPhotoAlbums;
        this.message = message;
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

    public boolean isContributor() {
        return contributor;
    }

    public void setContributor(boolean contributor) {
        this.contributor = contributor;
    }

    public ConsentFormStatus getConsentFormStatus() {
        return consentFormStatus;
    }

    public void setConsentFormStatus(ConsentFormStatus consentFormStatus) {
        this.consentFormStatus = consentFormStatus;
    }

    public Integer getCountPhotoAlbums() {
        return countPhotoAlbums;
    }

    public void setCountPhotoAlbums(Integer countPhotoAlbums) {
        this.countPhotoAlbums = countPhotoAlbums;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

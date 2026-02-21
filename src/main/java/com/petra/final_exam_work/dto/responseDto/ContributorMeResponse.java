package com.petra.final_exam_work.dto.responseDto;

import com.petra.final_exam_work.entity.consentForm.ConsentStatus;

public class ContributorMeResponse {

    private String username;
    private Integer yearSignedUp;

    private boolean isContributor;
    private ConsentStatus consentStatus;

    private Integer countPhotoAlbums;

    private String message;

    public ContributorMeResponse() {
    }

    public ContributorMeResponse(String username, Integer yearSignedUp, boolean isContributor,
                                 ConsentStatus consentStatus, Integer countPhotoAlbums, String message) {
        this.username = username;
        this.yearSignedUp = yearSignedUp;
        this.isContributor = isContributor;
        this.consentStatus = consentStatus;
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
        return isContributor;
    }

    public void setContributor(boolean contributor) {
        isContributor = contributor;
    }

    public ConsentStatus getConsentStatus() {
        return consentStatus;
    }

    public void setConsentStatus(ConsentStatus consentStatus) {
        this.consentStatus = consentStatus;
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

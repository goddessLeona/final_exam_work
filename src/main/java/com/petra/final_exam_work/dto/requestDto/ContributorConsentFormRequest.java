package com.petra.final_exam_work.dto.requestDto;

import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

public class ContributorConsentFormRequest {


    private MultipartFile idCardFile;
    private MultipartFile idFaceFile;
    private MultipartFile facefffFile;


    private Boolean approvedRules;

    public ContributorConsentFormRequest() {
    }

    public ContributorConsentFormRequest(MultipartFile idCardFile, MultipartFile idFaceFile, MultipartFile facefffFile,
                                         Boolean approvedRules) {
        this.idCardFile = idCardFile;
        this.idFaceFile = idFaceFile;
        this.facefffFile = facefffFile;
        this.approvedRules = approvedRules;
    }

    public MultipartFile getIdCardFile() {
        return idCardFile;
    }

    public void setIdCardFile(MultipartFile idCardFile) {
        this.idCardFile = idCardFile;
    }

    public MultipartFile getIdFaceFile() {
        return idFaceFile;
    }

    public void setIdFaceFile(MultipartFile idFaceFile) {
        this.idFaceFile = idFaceFile;
    }

    public MultipartFile getFacefffFile() {
        return facefffFile;
    }

    public void setFacefffFile(MultipartFile facefffFile) {
        this.facefffFile = facefffFile;
    }

    public Boolean getApprovedRules() {
        return approvedRules;
    }

    public void setApprovedRules(Boolean approvedRules) {
        this.approvedRules = approvedRules;
    }
}

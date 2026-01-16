package com.petra.final_exam_work.Entity.junktionTables;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class UserConsentFormId implements Serializable {

    private Long userId;
    private Long consentFormId;

    public UserConsentFormId() {
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getConsentFormId() {
        return consentFormId;
    }

    public void setConsentFormId(Long consentFormId) {
        this.consentFormId = consentFormId;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof UserConsentFormId that)) return false;
        return Objects.equals(userId, that.userId) && Objects.equals(consentFormId, that.consentFormId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, consentFormId);
    }
}

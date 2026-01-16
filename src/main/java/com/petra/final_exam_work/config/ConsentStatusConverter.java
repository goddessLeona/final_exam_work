package com.petra.final_exam_work.config;

import com.petra.final_exam_work.Entity.consentForm.ConsentStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class ConsentStatusConverter implements AttributeConverter<ConsentStatus, String> {

    @Override
    public String convertToDatabaseColumn(ConsentStatus attribute) {
        if (attribute == null) return null;
        return attribute.name().toLowerCase();  // Convert enum to lowercase string for DB
    }

    @Override
    public ConsentStatus convertToEntityAttribute(String dbData) {
        if (dbData == null) return null;
        return ConsentStatus.valueOf(dbData.toUpperCase());  // Convert DB string back to enum
    }
}

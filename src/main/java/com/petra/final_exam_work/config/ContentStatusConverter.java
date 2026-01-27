package com.petra.final_exam_work.config;

import com.petra.final_exam_work.entity.photo.ContentStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class ContentStatusConverter implements AttributeConverter<ContentStatus, String> {

    @Override
    public String convertToDatabaseColumn(ContentStatus attribute) {
        if (attribute == null) return null;
        return attribute.name().toLowerCase();  // Convert enum to lowercase string for DB
    }

    @Override
    public ContentStatus convertToEntityAttribute(String dbData) {
        if (dbData == null) return null;
        return ContentStatus.valueOf(dbData.toUpperCase());  // Convert DB string back to enum
    }
}


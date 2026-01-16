package com.petra.final_exam_work.config;

import com.petra.final_exam_work.Entity.photo.AlbumRoleStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class AlbumRoleStatusConverter implements AttributeConverter<AlbumRoleStatus, String> {

    @Override
    public String convertToDatabaseColumn(AlbumRoleStatus attribute) {
        if (attribute == null) return null;
        return attribute.name().toLowerCase();  // Convert enum to lowercase string for DB
    }

    @Override
    public AlbumRoleStatus convertToEntityAttribute(String dbData) {
        if (dbData == null) return null;
        return AlbumRoleStatus.valueOf(dbData.toUpperCase());  // Convert DB string back to enum
    }

}

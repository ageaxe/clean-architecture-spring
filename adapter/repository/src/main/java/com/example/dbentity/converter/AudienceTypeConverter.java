package com.example.dbentity.converter;

import com.example.dbentity.Inventory;
import org.springframework.stereotype.Component;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Arrays;

@Component
@Converter(autoApply = true)
public class AudienceTypeConverter implements AttributeConverter<Inventory.AudienceType, String> {
    @Override
    public String convertToDatabaseColumn(Inventory.AudienceType audienceType) {
        return audienceType == null
                ? null
                : audienceType.getValue();
    }

    @Override
    public Inventory.AudienceType convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isEmpty()) {
            return null;
        }

        return Arrays.stream(Inventory.AudienceType.values())
                .filter(c -> c.getValue().equals(dbData))
                .findAny()
                .orElseThrow(IllegalArgumentException::new);
    }

}

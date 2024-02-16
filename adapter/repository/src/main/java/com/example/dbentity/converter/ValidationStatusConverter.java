package com.example.dbentity.converter;

import com.example.dbentity.Inventory;
import org.springframework.stereotype.Component;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Arrays;

@Component
@Converter(autoApply = true)
public class ValidationStatusConverter implements AttributeConverter<Inventory.ValidationStatus, String> {
    @Override
    public String convertToDatabaseColumn(Inventory.ValidationStatus validationStatus) {
        return validationStatus == null
                ? null
                : validationStatus.getValue();
    }

    @Override
    public Inventory.ValidationStatus convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isEmpty()) {
            return null;
        }

        return Arrays.stream(Inventory.ValidationStatus.values())
                .filter(c -> c.getValue().equals(dbData))
                .findAny()
                .orElseThrow(IllegalArgumentException::new);
    }

}


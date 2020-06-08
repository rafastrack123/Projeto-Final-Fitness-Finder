package com.project.fitnessfinder.converter;

import java.time.DayOfWeek;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class DayOfWeekIntegerConverter implements AttributeConverter<DayOfWeek, Integer> {
    @Override
    public Integer convertToDatabaseColumn(DayOfWeek attribute) {
        return attribute.getValue();
    }

    @Override
    public DayOfWeek convertToEntityAttribute(Integer dbData) {
        return DayOfWeek.of(dbData);
    }
}